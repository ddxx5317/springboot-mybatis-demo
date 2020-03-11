package com.winter.utils;

import com.winter.enums.CipherTypeEnum;

/**
 * 加密接口
 */
public interface Cipher {

    /**
     * 加密
     * @param text  明文
     * @return      密文
     */
    String encrypt(String text);

    /**
     * 解密
     * @param text  密文
     * @return      明文
     */
    String decrypt(String text);

    String getKey();

    CipherTypeEnum getCipherType();
}
