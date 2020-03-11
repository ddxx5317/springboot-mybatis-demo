package com.winter.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Map;

/**
 * 密钥实体
 */
public class SecretKey implements Serializable{
	private static final long serialVersionUID = 5624442485429858922L;
	private final String charset;
    private final SecretKeySpec key;
    private String keyName;

    public SecretKey(String key) {
        this(key, "UTF-8");
    }

    public SecretKey(String pswd, String charset) {
        this.charset = charset;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(pswd.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, secureRandom);
            javax.crypto.SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, "AES");
        } catch (Throwable t) {
            throw new RuntimeException("使用[" + pswd + "][" + charset + "]构建秘钥时发生异常:" + t.getMessage(), t);
        }
    }

    public void encodeValues(Map<?,String> map){
        if(map==null || map.isEmpty()) return;
        for (Map.Entry<?, String> entry : map.entrySet()) {
            String value = entry.getValue();
            String encode = encode(value);
            entry.setValue(encode);
        }
    }

    public String encode(String msg) {
        try {
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = msg.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return SecurityUtil.bytes2Hex(result);
        } catch (Exception e) {
            throw new RuntimeException("对[" + msg + "][" + charset + "]进行加密时发生异常:" + e.getMessage(), e);
        }
    }

    public void decodeValues(Map<?,String> map){
        if(map==null || map.isEmpty()) return;
        for (Map.Entry<?, String> entry : map.entrySet()) {
            String value = entry.getValue();
            String decode = decode(value);
            entry.setValue(decode);
        }
    }

    public String decode(String msg) {
        Cipher cipher = null; // 创建密码器
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] content = SecurityUtil.parseHexStr2Byte(msg);
            byte[] result = cipher.doFinal(content);
            return new String(result, charset);
        } catch (Exception e) {
            throw new RuntimeException("对[" + msg + "][" + charset + "]进行解密时发生异常:" + e.getMessage(), e);
        }
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public static void main(String[] args) {
        SecretKey secretKey = new SecretKey("l;jhyGYtfyu5675iykgBJKY*(GjklhiouyiortIUGOhio789-090o-kpo^T*(^Tgioyhio5^&(TIOUHIOhjo");
        String encode = secretKey.encode("340711********0010");
        System.out.println(encode);
        SecretKey secretKey1 = new SecretKey("l;jhyGYtfyu5675iykgBJKY*(GjklhiouyiortIUGOhio789-090o-kpo^T*(^Tgioyhio5^&(TIOUHIOhjo");
        String decode = secretKey1.decode(encode);
        System.out.println(decode);
    }
}
