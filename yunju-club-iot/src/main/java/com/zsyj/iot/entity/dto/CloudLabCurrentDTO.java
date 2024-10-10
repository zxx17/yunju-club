package com.zsyj.iot.entity.dto;

import lombok.Data;

/**
 * 云端实验-电流检测实验返回DTO.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/10
 **/
@Data
public class CloudLabCurrentDTO {

    private String receivedData;

    private Boolean connectionStatus;

}
