package com.winter.utils;


import java.io.Serializable;

import com.winter.enums.CipherTypeEnum;

/**
 * Created by suye on 2017/7/17.
 */
public class NoCipher implements Cipher,Serializable {
    private static final long serialVersionUID = -5384244312990669637L;

    private CipherTypeEnum cipherType;

    public NoCipher() {
        this.cipherType = CipherTypeEnum.NONE;
    }

    @Override
    public String encrypt(String text) {
        return text;
    }

    @Override
    public String decrypt(String text) {
        return text;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public CipherTypeEnum getCipherType() {
        return cipherType;
    }
}
