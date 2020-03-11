package com.winter.utils;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.security.*;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.winter.enums.CipherTypeEnum;
import com.winter.enums.SignTypeEnum;

import static java.lang.String.format;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

/**
 * RSA加密、加签实现类
 */
public class RSA implements Cipher, Signature,Serializable {
	private static final long serialVersionUID = -8859195565347164391L;
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_ENCRYPT_BLOCK = 245;
    private static final int MAX_DECRYPT_BLOCK = 256;

    private String privateKeyStr;
    private String publicKeyStr;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String charset;

    private SignTypeEnum signType;
    private CipherTypeEnum cipherType;

    public RSA(String publicKeyStr, String privateKeyStr) {
        this(publicKeyStr, privateKeyStr, "UTF8");
    }

    public RSA(String publicKeyStr, String privateKeyStr, String charset) {
        this.privateKeyStr = privateKeyStr;
        this.publicKeyStr = publicKeyStr;
        this.charset = charset;
        this.signType = SignTypeEnum.RSA;
        this.cipherType = CipherTypeEnum.RSA;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeBase64(publicKeyStr));
            publicKey = keyFactory.generatePublic(keySpec);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKeyStr));
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("使用公私钥[" + publicKeyStr + "][" + privateKeyStr + "]构造秘钥对时发生异常:" + e.getMessage(), e);
        }
    }

    @Override
    public String encrypt(String text) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = StringUtils.getBytes(text, "utf8");
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            return encodeBase64String(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(this + "对[" + text + "]进行加密时发生异常:" + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String text) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedData = decodeBase64(text);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, charset);

        } catch (Exception e) {
            throw new RuntimeException(this + "对[" + text + "]进行加密时发生异常:" + e.getMessage(), e);
        }
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public CipherTypeEnum getCipherType() {
        return cipherType;
    }

    @Override
    public SignTypeEnum getSignType() {
        return signType;
    }

    @Override
    public String calculateSignature(String msg) {
        try {
            java.security.Signature signature =   java.security.Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(StringUtils.getBytes(msg, charset));
            byte[] result = signature.sign();
            return encodeBase64String(result);
        } catch (Exception e) {
            throw new RuntimeException(this + "验证[" + msg + "]的签名时发生异常:" + e.getMessage(), e);
        }
    }

    @Override
    public boolean verifySignature(String msg, String sign) {
        try {
            java.security.Signature signature =   java.security.Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(StringUtils.getBytes(msg, charset));
            return signature.verify(decodeBase64(sign));
        } catch (Exception e) {
        }
		return false;
        
    }

    @Override
    public String toString() {
        return format("%s[(public:%s)(private:%s)]", getClass().getSimpleName(), publicKeyStr, privateKeyStr);
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

//    public static void main(String[] args) {
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxAbF5OMdlj/NvI5tiohgj59N1R8MgJKJjddSCizDFJQmXU7L92iXDd+kflZpl8r7/GVswYKQGLhnm8kyrX+/daw+Ur5ianEjuXaMlJFnFZkaJGNWLx+odknLWYWnfUMEfvUy5kS2xNBXiPz9GjwNmgIj2HeeBnrIMsSmgE408mwIDAQAB";
//        String privateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAPEBsXk4x2WP828jm2KiGCPn03VHwyAkomN11IKLMMUlCZdTsv3aJcN36R+VmmXyvv8ZWzBgpAYuGebyTKtf791rD5SvmJqcSO5doyUkWcVmRokY1YvH6h2SctZhad9QwR+9TLmRLbE0FeI/P0aPA2aAiPYd54GesgyxKaATjTybAgMBAAECgYEAplzL3GjUQ4hNux8yKLDJxydE8YU67Vo8ejmhGwfn/35kk4AkY1UNklOYqcPEU7FwJHmlV8yuDNIP8Tq6r+XGlZNN8fc2BQ8RuCdASpJ21wrDVJdpy/gAs5hylAXkod0Fa5Pf6Yegu2YtKwKaVI/DzNaIIG/Zebnye13KAQuNPOkCQQD9gc09SkHQPcymhKtzcE9hPbWAo656Xo2/WaNaMQyueeA/Qe7gJ0jDxwZMOXWee84Km/8ndNudtXCgS/GdS7L1AkEA82BsCGaqwOE+Zm1dvODIfPRSlIgEnyLXedXCpZHFDItAo7F54VsMB8wn8W4/wBUUeNzfA78hR3djOKsHu4kXTwJBAOagff1yXul6L4KWU/xTgoPuxf7f6k29U6tveyMEWIsqqY4jB5S5aINjvyD9bTnfXBVe0gQtVdbmSC4sqQT25zkCQQC0KgXvdikjndq2snF4+CIStj9HqyVYtM80ZvSv4qgvcAqK4z/pfp/6Sbyr8kSJKlG8Yy1Itb2qDQxLj/iqcILrAkEAqvOfjxgWL4mx3MVvyAKHXTry4Wa1gZV3bZJqtSMnm9JDg3Nv8cn90CcUS2/saLZMefziJ7EVb//0WNqBu+QmWg==";
//        String msg = "{\n" +
//                "      \"requestTime\": 1465905306361,\n" +
//                "      \"amount\": 1000,\n" +
//                "      \"orderNO\": \"acquirer3251076969\",\n" +
//                "      \"service\": \"acquirer\",\n" +
//                "      \"traceNO\": \"acquirer3251076969\",\n" +
//                "      \"businessId\": 9,\n" +
//                "      \"notifyUrl\": \"http://test.api.51kupai.com/kupai/result/payResultBaofu\",\n" +
//                "      \"describe\": \"1000\",\n" +
//                "      \"payer\": \"100030\"\n" +
//                "    }\n";
//
//        RSA rsa = new RSA(publicKey, privateKey);
//
//        //签名和验签
//        String sign = rsa.calculateSignature(msg);
//        boolean verifySignature = rsa.verifySignature(msg, sign);
//        System.out.println(sign + "\n" + verifySignature);
//
//        //加密和解密
//        String encrypt = rsa.encrypt(msg);
//        String decrypt = rsa.decrypt(encrypt);
//        System.out.println(encrypt + "\n" + decrypt);
//    }
}

