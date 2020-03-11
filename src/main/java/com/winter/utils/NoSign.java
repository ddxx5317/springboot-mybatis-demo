package com.winter.utils;

import java.io.Serializable;

import com.winter.enums.SignTypeEnum;

/**
 * 不验签类型的实现
 */
public class NoSign implements Signature,Serializable {
	private static final long serialVersionUID = -1294193917182555402L;
	private SignTypeEnum signType;

    public NoSign() {
        signType = SignTypeEnum.NONE;
    }

    @Override
    public SignTypeEnum getSignType() {
        return signType;
    }

    @Override
    public String calculateSignature(String msg) {
        return msg;
    }

    @Override
    public boolean verifySignature(String msg, String sign) {
        return true;
    }

    @Override
    public String getKey() {
        return null;
    }
}
