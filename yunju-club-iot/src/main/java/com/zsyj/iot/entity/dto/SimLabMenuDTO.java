package com.zsyj.iot.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟仿真实验左侧菜单DTO.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/9
 */
@Data
public class SimLabMenuDTO {

    private String key;
    private String label;
    private String projectUrl;
    private String projectDesc;
    private List<SimLabMenuDTO> children = new ArrayList<>();
}
