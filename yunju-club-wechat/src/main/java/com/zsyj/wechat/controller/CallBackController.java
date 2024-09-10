package com.zsyj.wechat.controller;

import com.zsyj.wechat.handler.WxChatMsgFactory;
import com.zsyj.wechat.handler.WxChatMsgHandler;
import com.zsyj.wechat.util.MessageUtil;
import com.zsyj.wechat.util.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/9
 */
@Slf4j
//@RefreshScope
@RestController
public class CallBackController {

//    @Value("${wx-token}")
    private final String wxToken = "yunjuc1ubwxtoken";

    @Resource
    private WxChatMsgFactory wxChatMsgFactory;


    /**
     * 回调消息校验
     */
    @GetMapping("/callback")
    public String callback(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        log.info("微信回调GET验签请求参数：signature:{}，timestamp:{}，nonce:{}，echostr:{}",
                signature, timestamp, nonce, echostr);
        String shaStr = SHA1.getSHA1(wxToken, timestamp, nonce, "");
        if (signature.equals(shaStr)) {
            return echostr;
        }
        return "unknown";
    }

    @PostMapping(value = "/callback", produces = "application/xml;charset=UTF-8")
    public String callback(
            @RequestBody String requestBody) {
        log.info(">>>>>>>接收到微信消息：requestBody：{}", requestBody);
        Map<String, String> messageMap = MessageUtil.parseXml(requestBody);
        String msgType = messageMap.get("MsgType");
        String event = messageMap.get("Event") == null ? "" : messageMap.get("Event");
        log.info("msgType:{},event:{}", msgType, event);

        StringBuilder sb = new StringBuilder();
        sb.append(msgType);
        if (!StringUtils.isBlank(event)) {
            sb.append(".");
            sb.append(event);
        }
        String msgTypeKey = sb.toString();
        WxChatMsgHandler wxChatMsgHandler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);
        if (Objects.isNull(wxChatMsgHandler)) {
            return "unknown";
        }
        String replyContent = wxChatMsgHandler.dealMsg(messageMap);
        log.info("回复微信消息replyContent:{}", replyContent+"+\n<<<<<<<");
        return replyContent;
    }

}
