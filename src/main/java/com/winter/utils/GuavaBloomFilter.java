package com.winter.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.*;

public class GuavaBloomFilter {
    public static void main(String[] args) throws Exception {
        final BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),100000,0.01);
        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put("1000000"+i);
        }
        //System.out.println(bloomFilter.mightContain("123456"));
        //System.out.println(bloomFilter.mightContain("10086"));

        //数据持久化到本地
        File file= new File("D:" + File.separator + "test");
        OutputStream out = null;
        out = new FileOutputStream(file);
        try {
            bloomFilter.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //重新构建
        BloomFilter<CharSequence> filter = BloomFilter.create(
                Funnels.stringFunnel(Charsets.UTF_8), 1000000, 0.01);

        InputStream in = new FileInputStream(file);
        filter = BloomFilter.readFrom(in, Funnels.stringFunnel(Charsets.UTF_8));
        for (int i = 0; i < 10; i++) {
            System.out.println(filter.mightContain("1000000"+i));
        }
    }
}