package com.zsyj.iot.service.impl;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsyj.iot.controller.request.CloudLabCurrentSendMessageRequest;
import com.zsyj.iot.controller.request.CloudLabCurrentStartRequest;
import com.zsyj.iot.entity.IotCloudLab;
import com.zsyj.iot.entity.Result;
import com.zsyj.iot.entity.dto.CloudLabCurrentDTO;
import com.zsyj.iot.mapper.IotCloudLabDao;
import com.zsyj.iot.redis.RedisUtil;
import com.zsyj.iot.service.IOTCloudLabService;
import com.zsyj.iot.util.LoginUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
@Slf4j
@Service
public class IOTCloudLabServiceImpl implements IOTCloudLabService {

    // 连接数量（仅作实验用途，64为max）
    private static final int CONNECTION_COUNT = 64;

    // amqp连接线程池配置（仅作实验用途）
    private final static ExecutorService executorService = new ThreadPoolExecutor(
            1,
            2, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(128));

    @Resource
    private IotCloudLabDao iotCloudLabDao;

    @Resource
    private RedisUtil redisUtil;


    @Override
    @SneakyThrows
    public Result<CloudLabCurrentDTO> connectionAndReceive(CloudLabCurrentStartRequest request) {
        String loginId = LoginUtil.getLoginId();
        String value = redisUtil.get(redisUtil.buildKey("iot:lab:current:", loginId));
        if (value != null){
            CloudLabCurrentDTO dto = new CloudLabCurrentDTO();
            dto.setConnectionStatus(true);
            dto.setReceivedData(value);
            return Result.ok(dto);
        }
        String accessKey = request.getAccessKey();
        String accessSecret = request.getAccessSecret();
        String consumerGroupId = request.getConsumerGroupId();
        //iotInstanceId：实例ID。若是2021年07月30日之前（不含当日）开通的公共实例，请填空字符串。
        String iotInstanceId = request.getIotInstanceId();
        //控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
        //建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。
        String clientId = UuidUtils.generateUuid();
        //${YourHost}为接入域名，请参见AMQP客户端接入说明文档。
        String host = request.getHost();

        List<Connection> connections = new ArrayList<>();

        long timeStamp = System.currentTimeMillis();
        //签名方法：支持hmacmd5、hmacsha1和hmacsha256。
        String signMethod = "hmacsha1";
        //userName组装方法，请参见AMQP客户端接入说明文档。
        String userName = clientId + "-" + CONNECTION_COUNT + "|authMode=aksign"
                + ",signMethod=" + signMethod
                + ",timestamp=" + timeStamp
                + ",authId=" + accessKey
                + ",iotInstanceId=" + iotInstanceId
                + ",consumerGroupId=" + consumerGroupId
                + "|";
        //计算签名，password组装方法，请参见AMQP客户端接入说明文档。
        String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
        String password = doSign(signContent, accessSecret, signMethod);
        String connectionUrl = "failover:(amqps://" + host + ":5671?amqp.idleTimeout=80000)"
                + "?failover.reconnectDelay=30";

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF", connectionUrl);
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        Context context = new InitialContext(hashtable);
        ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
        Destination queue = (Destination) context.lookup("QUEUE");
        // 创建连接。
        Connection connection = cf.createConnection(userName, password);
        connections.add(connection);

        ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
        // 创建会话。
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();
        // 创建Receiver连接。
        MessageConsumer consumer = session.createConsumer(queue);
        // 消费数据
        // TODO 实际的物联网数据对接应该是持续异步消费（参考官方的demo，编写MessageListener），这里作为实验，先这样
        Double current = null;
        int i = 5;
        while (i != 0) {
            Message receive = consumer.receive();
            byte[] body = receive.getBody(byte[].class);
            String content = new String(body);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(content);
            if (json.get("items") != null && !json.get("items").isEmpty()) {
                current = json.get("items").get("LightCurrent").get("value").asDouble() * 100;
            }
            i--;
        }

        // 没有收到数据直接返回失败
        if (current == null){
            return Result.fail();
        }

        // 持久化数据到redis
        redisUtil.setNx(redisUtil.buildKey("iot:lab:current:", loginId), String.valueOf(current),60L, TimeUnit.SECONDS);
        // 构造返回值
        CloudLabCurrentDTO dto = new CloudLabCurrentDTO();
        dto.setReceivedData(current.toString());
        dto.setConnectionStatus(true);
        // 如果数据正确，则更新一份到mysql数据库中
        if (current >= 85){
            IotCloudLab iotCloudLab = new IotCloudLab();
            iotCloudLab.setProjectName(request.getProjectName());
            iotCloudLab.setFinishStatus(1);
            iotCloudLab.setReceivedData(current.toString());
            iotCloudLab.setAccessKey(request.getAccessKey());
            iotCloudLab.setAccessSecret(request.getAccessSecret());
            iotCloudLab.setConsumerGroupId(request.getConsumerGroupId());
            iotCloudLab.setIotInstanceId(request.getIotInstanceId());
            iotCloudLab.setClientId(clientId);
            iotCloudLab.setConnectionCount(CONNECTION_COUNT);
            iotCloudLab.setHost(host);
            iotCloudLab.setCreatedBy(loginId);
            iotCloudLab.setCreatedTime(new Date());
            iotCloudLabDao.insert(iotCloudLab);
        }
        return Result.ok(dto);
    }

    @Override
    public Result<Boolean> sendMessage(CloudLabCurrentSendMessageRequest request) {
        return null;
    }


    /**
     * 连接监听器
     */
    private static final JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立。
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            log.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            log.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            log.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            log.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {
        }

        @Override
        public void onSessionClosed(Session session, Throwable cause) {
        }

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {
        }

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {
        }
    };


    /**
     * 计算签名，password组装方法，请参见AMQP客户端接入说明文档。
     */
    private String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }
}
