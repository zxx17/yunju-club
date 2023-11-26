package com.zsyj.subject.common.entity;

import com.zsyj.subject.common.enums.ResultEnum;
import lombok.Data;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/26
 * <p>
 *
 * </p>
 */

@Data
public class Result<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> ok() {
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getDesc());
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getDesc());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getDesc());
        return result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getDesc());
        result.setData(data);
        return result;
    }



}
