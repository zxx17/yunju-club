package com.zsyj.iot.controller.request;

import lombok.Data;

/**
 * 云端实验-电流检测实验---用户就绪，平台接收数据Req.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
@Data
public class CloudLabCurrentStartRequest {
    /* 项目名称 */
    private String projectName;

    /* 完整状态 1为完成 0 为未完成 */
    private Byte finishStatus;

    /* 接收到的数据 */
    private String receivedData;

    /* 阿里云主账号或对应RAM用户的AccessKey */
    private String accessKey;

    /* 阿里云主账号或对应RAM用户的AccessKeySecret */
    private String accessSecret;

    /* 当前物联网平台对应实例中的消费组ID */
    private String consumerGroupId;

    /* IOT实例ID */
    private String iotInstanceId;

    /* 表示客户端ID */
    private String clientId;

    /* 启动AMQP客户端的连接数 */
    private Integer connectionCount;

    /* AMQP接入域名 */
    private String host;
}
