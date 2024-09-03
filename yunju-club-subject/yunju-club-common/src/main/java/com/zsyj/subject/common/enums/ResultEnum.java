package com.zsyj.subject.common.enums;


import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    private int code;
    private String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResultEnum getByCode(Integer code){
        for(ResultEnum resultEnum: ResultEnum.values()){
            if(resultEnum.code == code){
                return resultEnum;
            }
        }
        return null;
    }


}
