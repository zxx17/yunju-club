package com.zsyj.iot.controller.request;

import lombok.Data;

/**
 * 云端实验-电流检测实验---用户向设备发送消息.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
@Data
public class CloudLabCurrentSendMessageRequest {
    private String respMessage;
}
