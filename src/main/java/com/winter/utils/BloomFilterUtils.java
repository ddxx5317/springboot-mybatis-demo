package com.winter.utils;

import com.google.common.hash.BloomFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: DDxx
 * @Date: 2022/11/27
 */
public class BloomFilterUtils {
    private static final Map<String, BloomFilter<CharSequence>> FILTERS = new ConcurrentHashMap<>();

    public static BloomFilter<CharSequence> get(String str){
       return FILTERS.get(str);
    }

    public static void init(String chainId, BloomFilter<CharSequence> bloomFilter){
       FILTERS.put(chainId, bloomFilter);
    }
}
