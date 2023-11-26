package com.zsyj.subject.infra.basic.utils;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/24
 * <p>
 * 对yaml中敏感信息进行加密
 * </p>
 */

public class DruidEncryptUtil {


    private static String publicKey;

    private static String privateKey;


    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            System.out.println("privateKey: " + privateKey + "\n");

            publicKey = keyPair[1];
            System.out.println("publicKey: " + publicKey + "\n");

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }


    public static String encrypt(String plainText) throws Exception {
        return ConfigTools.encrypt(privateKey, plainText);
    }


    public static String decrypt(String encryptText) throws Exception {
        return ConfigTools.decrypt(publicKey, encryptText);
    }

}
