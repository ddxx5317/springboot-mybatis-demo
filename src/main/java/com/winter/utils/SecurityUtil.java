package com.winter.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtil {

    private static final String SHA_256 = "SHA-256";
    private static final String MD5 = "MD5";
    private static final int KEY_LENGTH = 16;

    public static final String DEFAULT_AES_KEY = "%01#g34h&B7c89i^";

    /**
     * 获取随机盐
     *
     * @param size 字节数
     * @return
     */
    public static String getSalt(int size) {
        byte[] salt = new byte[size];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
            return Base64.encodeBase64String(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String key = "0123456789abcdef";
        String abc = aesEncrypt("abcdefghijklmnopq", DEFAULT_AES_KEY);
        System.out.println(abc);

        String aaaa = aesDecrypt("1D25821C3E311EEA2D4DD8633A25C1B5", key);
        System.out.println(aaaa);

    }

    /**
     * 加密密码
     *
     * @param password 密码
     * @param key      密钥 长度为32bytes
     * @return
     */
    public static String encryptPassword(String password, String key) {
        if (password == null || key == null) {
            throw new IllegalArgumentException("password or key can't be null!");
        }
        // 判断Key是否为16bytes
        if (key.getBytes().length != KEY_LENGTH) {
            throw new IllegalArgumentException("Key must be 16 bytes long!");
        }
        try {
            return Base64.encodeBase64String(AES.encrypt(password.getBytes(), key.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密密码
     *
     * @param password 密码
     * @param key      密钥 长度为16bytes
     * @return
     */
    public static String decryptPassword(String password, String key) {
        if (password == null || key == null) {
            throw new IllegalArgumentException("password or key can't be null!");
        }
        // 判断Key是否为16bytes
        if (key.getBytes().length != KEY_LENGTH) {
            throw new IllegalArgumentException("Key must be 16 bytes long!");
        }
        try {
            return new String(AES.decrypt(Base64.decodeBase64(password), key.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encrypt by the des algorithm
     * <p>change the byte result data to hex </p>
     *
     * @param password
     * @param key
     * @return
     */
    public static String aesEncrypt(String password, String key) {

        if (password == null || key == null) {
            throw new IllegalArgumentException("password or key can't be null!");
        }
        // 判断Key是否为16bytes
        if (key.getBytes().length != KEY_LENGTH) {
            throw new IllegalArgumentException("Key must be 16 bytes long!");
        }
        try {
            return parseByte2HexStr(AES.encrypt(password.getBytes(), key.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * byte data to hex data
     * <p>
     * <p>change the hex result data to byte </p>
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * decrypt by the des algorithm
     *
     * @param password
     * @param key
     * @return
     */
    public static String aesDecrypt(String password, String key) {
        if (password == null || key == null) {
            throw new IllegalArgumentException("password or key can't be null!");
        }
        // 判断Key是否为16bytes
        if (key.getBytes().length != KEY_LENGTH) {
            throw new IllegalArgumentException("Key must be 16 bytes long!");
        }
        try {
            return new String(AES.decrypt(parseHexStr2Byte(password), key.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * change hex data to byte
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 做SHA256hash处理
     *
     * @param str
     * @return
     */
    public static String doSHA256(String str) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = str.getBytes();
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * 做SHA256hash处理
     *
     * @param str
     * @param charset
     * @return
     */
    public static String doSHA256(String str, String charset) {
        MessageDigest md = null;
        String strDes = null;
        try {
            byte[] bt = str.getBytes(charset);
            md = MessageDigest.getInstance(SHA_256);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (Exception e) {
            return null;
        }
        return strDes;
    }

    public static String doMd5(String str, String charset) {
        MessageDigest messageDigest = null;
        String strDes = null;
        try {
            byte[] bt = str.getBytes(charset);
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bt);
            strDes = bytes2Hex(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException("MD5对【"+ str +"】签名出现异常："+ e.getMessage(), e);
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
