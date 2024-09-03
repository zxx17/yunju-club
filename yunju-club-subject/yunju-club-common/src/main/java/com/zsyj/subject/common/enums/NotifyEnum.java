package com.zsyj.subject.common.enums;

import lombok.Getter;

@Getter
public enum NotifyEnum {

    INSERT_SUCCESS("新增成功: "),
    INSERT_FAIL("新增失败: "),
    QUERY_FAIL("查询失败: "),
    UPDATE_FAIL("更新失败: "),
    DELETE_FAIL("删除失败: ");

    private final String notify;

    NotifyEnum(String message){
        this.notify = message;
    }

}
