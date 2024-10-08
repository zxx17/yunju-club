package com.zsyj.iot.controller.request;

import lombok.Data;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/8
 */
@Data
public class SimLabRecordRequest {
    private String projectUrl;
    /**
     * 是否完成 0未完成 1已完成
     */
    private Integer isFinished;
}
