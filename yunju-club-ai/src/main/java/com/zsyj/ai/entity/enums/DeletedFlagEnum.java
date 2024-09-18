package com.zsyj.ai.entity.enums;

import lombok.Getter;

@Getter
public enum DeletedFlagEnum {

    IS_DELETED(1),
    UN_DELETE(0);

    private Integer flag;

    DeletedFlagEnum(Integer flag){
        this.flag = flag;
    }

}
