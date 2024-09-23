package com.zsyj.practice.server.util;


import com.zsyj.practice.server.config.context.LoginContextHolder;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/11
 */
public class LoginUtil {
    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}
