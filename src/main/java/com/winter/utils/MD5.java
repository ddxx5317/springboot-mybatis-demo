package com.winter.utils;


import java.io.Serializable;

import com.winter.enums.SignTypeEnum;


/**
 * MD5加签类
 */
public class MD5 implements Signature, Serializable {
	private static final long serialVersionUID = -4043598309029769949L;

	private SignTypeEnum signType;

    public static final String KEY_MD5 = "MD5";
    private String charset;
    private String key;

    public MD5(String key) {
        this(key, "UTF-8");
    }

    public MD5(String key, String charset) {
        this.key = key;
        this.charset = charset;
        signType = SignTypeEnum.MD5;
    }

    @Override
    public String calculateSignature(String msg) {
        return SecurityUtil.doMd5(msg + getKey(), charset);
    }

    @Override
    public boolean verifySignature(String msg, String sign) {
        try {
            String digest = calculateSignature(msg);

            return sign.equals(digest);
        } catch (Exception e) {
        }
		return false;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public SignTypeEnum getSignType() {
        return signType;
    }

}
