package com.zsyj.iot.service;

import com.zsyj.iot.controller.request.CloudLabCurrentSendMessageRequest;
import com.zsyj.iot.controller.request.CloudLabCurrentStartRequest;
import com.zsyj.iot.entity.Result;
import com.zsyj.iot.entity.dto.CloudLabCurrentDTO;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
public interface IOTCloudLabService {
    Result<CloudLabCurrentDTO> connectionAndReceive(CloudLabCurrentStartRequest request);

    Result<Boolean> sendMessage(CloudLabCurrentSendMessageRequest request);
}
