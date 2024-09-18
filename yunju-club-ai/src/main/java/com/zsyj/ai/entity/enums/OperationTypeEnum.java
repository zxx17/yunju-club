package com.zsyj.ai.entity.enums;

import lombok.Getter;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Getter
public enum OperationTypeEnum {

    QUIZ("答题", 1),
    SIM_LAB("物联网仿真实验", 2),
    CLOUD_LAB("物联网云端场景实验", 3),
    PUSH("发布帖子到圈子", 4);

    private final Integer type;
    private final String desc;


    OperationTypeEnum(String desc, Integer type) {
        this.desc = desc;
        this.type = type;
    }

    public static String getDescByType(Integer type) {
        for (OperationTypeEnum value : OperationTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDesc();
            }
        }
        return null;
    }

}
