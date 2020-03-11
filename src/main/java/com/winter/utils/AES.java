package com.winter.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    /**
     *  算法/模式/补码方式
     */
    private static final String ALGORITHM = "AES/ecb/PKCS5Padding";

    /**
     * 加密
     * @param src 密文
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(src);
    }

    /**
     * 解密: 使用AES 256 bit
     * @param src 密文
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(src);
    }

}
