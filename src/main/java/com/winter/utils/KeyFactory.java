package com.winter.utils;


import java.util.HashMap;
import java.util.Map;

import com.winter.enums.CipherTypeEnum;
import com.winter.enums.SignTypeEnum;



/**
 * key工厂
 */
public class KeyFactory {
    public static final String KEY_SIGN_TYPE = "signType";
    public static final String KEY_CIPHER_TYPE = "cipherType";
    public static final String KEY_PRIVATE = "privateKey";
    public static final String KEY_PUBLIC = "publicKey";
    public static final String KEY_MD5 = "md5Key";

    public static final String KEY_AES = "aesKey";
    public static final String KEY_DES = "desKey";

    public static final String CHARSET = "utf8";

    private static KeyFactory factory = new KeyFactory();

    public static KeyFactory keyFactory() {
        return factory;
    }

    private KeyFactory() {
    }

    public Cipher map2cipher(Map<String, String> map) {
        CipherTypeEnum cipherType = CipherTypeEnum.getEnumByCode(map.get(KEY_CIPHER_TYPE));

        switch (cipherType) {
            case RSA:
                return makeRSA(map);
            case NONE:
                return new NoCipher();
            default:
                throw new RuntimeException("无法识别的加密类型[" + CollectionUtils.map2string(map) + "]");
        }
    }

    public Signature map2signature(Map<String, String> map) {
        SignTypeEnum signType = SignTypeEnum.getEnumByCode(map.get(KEY_SIGN_TYPE));

        switch (signType) {
            case RSA:
                return makeRSA(map);
            case MD5:
            case NONE:
                return new NoSign();
            default:
                throw new RuntimeException("无法识别的加密类型[" + CollectionUtils.map2string(map) + "]");
        }
    }

    public Map<String, String> cipher2map(Cipher cipher) {
        CipherTypeEnum cipherType = cipher.getCipherType();

        switch (cipherType) {
            case RSA:
                return rsa2map((RSA) cipher);
            case AES:
            case NONE:
                return noCipher2map();
            default:
                throw new RuntimeException("无法识别的加密方式[" + cipher + "]");
        }
    }

    public Map<String, String> signature2map(Signature signature) {
        SignTypeEnum signType = signature.getSignType();

        switch (signType) {
            case RSA:
                return rsa2map((RSA) signature);
            case MD5:
                return md52map((MD5) signature);
            case NONE:
                return noSign2map();
            default:
                throw new RuntimeException("无法识别的加签方式[" + signature + "]");
        }
    }

    private RSA makeRSA(Map<String, String> map) {
        String publicKeyStr = map.get(KEY_PUBLIC);
        String privateKeyStr = map.get(KEY_PRIVATE);
        return new RSA(publicKeyStr, privateKeyStr, CHARSET);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> rsa2map(RSA rsa) {
        Map map = new HashMap<>();
        map.put(KEY_SIGN_TYPE, SignTypeEnum.RSA.getCode());
        map.put(KEY_PUBLIC, rsa.getPublicKeyStr());
        map.put(KEY_PRIVATE, rsa.getPrivateKeyStr());
        return map;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> md52map(MD5 md5) {
        Map map = new HashMap<>();
        map.put(KEY_SIGN_TYPE, SignTypeEnum.MD5.getCode());
        map.put(KEY_MD5, md5.getKey());
        return map;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> noSign2map() {
        Map map = new HashMap<>();
        map.put(KEY_SIGN_TYPE, SignTypeEnum.NONE.getCode());
        return map;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> noCipher2map() {
        Map map = new HashMap<>();
        map.put(KEY_CIPHER_TYPE, CipherTypeEnum.NONE.getCode());
        return map;
    }
}
