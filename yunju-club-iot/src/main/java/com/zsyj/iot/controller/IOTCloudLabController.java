package com.zsyj.iot.controller;

import com.zsyj.iot.controller.request.CloudLabCurrentSendMessageRequest;
import com.zsyj.iot.controller.request.CloudLabCurrentStartRequest;
import com.zsyj.iot.entity.Result;
import com.zsyj.iot.entity.dto.CloudLabCurrentDTO;
import com.zsyj.iot.service.IOTCloudLabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/8
 */
@Slf4j
@RestController
@RequestMapping("/cloudLab")
public class IOTCloudLabController {

    @Resource
    private IOTCloudLabService iotCloudLabService;

    /**
     * 用户就绪，平台接收数据
     */
    @PostMapping("/start")
    public  Result<CloudLabCurrentDTO> connectionAndReceive(@RequestBody CloudLabCurrentStartRequest request) {
        return iotCloudLabService.connectionAndReceive(request);
    }


    /**
     * 用户向物联网设备发送数据，完成此步骤则表示实验完成
     */
    @PostMapping("/send")
    public Result<Boolean> sendMessage(@RequestBody CloudLabCurrentSendMessageRequest request) {
        return iotCloudLabService.sendMessage(request);
    }

}
