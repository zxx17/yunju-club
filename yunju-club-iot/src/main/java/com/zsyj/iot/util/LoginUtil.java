package com.zsyj.iot.util;


import com.zsyj.iot.context.LoginContextHolder;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
public class LoginUtil {
    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}