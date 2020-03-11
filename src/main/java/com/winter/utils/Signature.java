package com.winter.utils;

import com.winter.enums.SignTypeEnum;


/**
 * 签名接口
 */
public interface Signature {

    /**
     * 加签
     * @param msg   签名内容
     * @return
     */
    String calculateSignature(String msg);

    /**
     * 验签
     * @param msg       签名内容
     * @param sign      需要验的签名
     * @return
     */
    boolean verifySignature(String msg, String sign);

    String getKey();

    SignTypeEnum getSignType();
}
