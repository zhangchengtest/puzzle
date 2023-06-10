package com.elephant.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.commons.lang3.StringUtils;

public class PasswordController {

    private static final String CRYPTO_SYMMETRIC_KEY = "0CoJUm6Qyw8W8jud";

    private static final String CRYPTO_SYMMETRIC_IV = "0102030405060708";

    public static void main(String[] args) {
        String salt = "d210fc1ef50595684650d24d979f0f61";
        String password = "ed646614443bacd911321f37035b0e51";

        String saltContent = symmetricDecryptStr(salt);
        String passwordEncryptData = generateDigestData(password, saltContent);
        passwordEncryptData = digestEncryptHex(passwordEncryptData);
        System.out.println(passwordEncryptData); //6c33d4db94f3315ac430a4c501fdfa02
    }

    private static String symmetricDecryptStr(String encryptHex) {
        AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, CRYPTO_SYMMETRIC_KEY.getBytes(), CRYPTO_SYMMETRIC_IV.getBytes());
        return aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
    private static String generateDigestData(String data, String salt) {
        return StringUtils.lowerCase(data) + salt;
    }

    private static String digestEncryptHex(String content) {
        return DigestUtil.md5Hex(content);
    }

}
