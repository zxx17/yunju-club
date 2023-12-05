package com.zsyj.subject.common.enums;

import lombok.Getter;

@Getter
public enum SubjectInfoTypeEnum {

    RADIO(1, ""),
    MULTIPLE(2, ""),
    JUDGE(3, ""),
    BRIEF(4, "");

    private int code;
    private String desc;


    SubjectInfoTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubjectInfoTypeEnum getByCode(Integer code) {
        for (SubjectInfoTypeEnum subjectInfoTypeEnum : SubjectInfoTypeEnum.values()) {
            if (subjectInfoTypeEnum.code == code) {
                return subjectInfoTypeEnum;
            }
        }
        return null;
    }
}
