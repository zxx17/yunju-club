package com.zsyj.wechat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 生成随机验证码.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/10
 */
public class ValidCodeUtil {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER    = "0123456789";
    private static final String COMBINED  = CHAR_LOWER + CHAR_UPPER + NUMBER;

    /**
     * 生成一个包含字母和数字的4位随机字符串。
     *
     * @return 随机字符串
     */
    public static String generateRandomValidCode() {
        Random rnd = new Random();
        StringBuilder stringBuilder = new StringBuilder(4);

        // 确保至少有一个字母和一个数字
        char digit = NUMBER.charAt(rnd.nextInt(NUMBER.length()));
        char letterLower = CHAR_LOWER.charAt(rnd.nextInt(CHAR_LOWER.length()));
        char letterUpper = CHAR_UPPER.charAt(rnd.nextInt(CHAR_UPPER.length()));

        // 添加到字符串构建器
        stringBuilder.append(digit).append(letterLower).append(letterUpper);

        // 剩下的一个位置填充任意字符
        for (int i = 3; i < 4; i++) {
            stringBuilder.append(COMBINED.charAt(rnd.nextInt(COMBINED.length())));
        }

        // 打乱顺序
        List<Character> characters = new ArrayList<>(stringBuilder.length());
        for (char c : stringBuilder.toString().toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters, rnd);
        stringBuilder.setLength(0);
        for (char c : characters) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }
}
