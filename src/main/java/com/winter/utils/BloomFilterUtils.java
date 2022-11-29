package com.winter.utils;

import com.google.common.hash.BloomFilter;

import java.util.HashMap;

/**
 * @Description:
 * @Author: DDxx
 * @Date: 2022/11/27
 */
public class BloomFilterUtils {
    public final static HashMap<String, BloomFilter> map = new HashMap<>();

    public static BloomFilter get(String str){
       return map.get(str);
    }

    public static void init(String chainId, BloomFilter bloomFilter){
       map.put(chainId, bloomFilter);
    }
}
