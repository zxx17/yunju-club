package com.zsyj.subject.common.enums;

import lombok.Getter;

/**
 * 分类类型枚举.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/4
 */
@Getter
public enum CategoryTypeEnum {

    PRIMARY(1, "一级分类"),
    SECOND(2, "二级分类");

    public final int code;

    public final String desc;

    CategoryTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CategoryTypeEnum getByCode(int codeVal) {
        for (CategoryTypeEnum resultCodeEnum : CategoryTypeEnum.values()) {
            if (resultCodeEnum.code == codeVal) {
                return resultCodeEnum;
            }
        }
        return null;
    }

}
