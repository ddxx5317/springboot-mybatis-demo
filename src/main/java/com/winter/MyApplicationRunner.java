package com.winter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.winter.model.Bloom;
import com.winter.service.BloomService;
import com.winter.utils.BloomFilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private BloomService bloomService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bloom bloom = bloomService.getBloom(7);
        BloomFilter<CharSequence> filter = BloomFilter.readFrom(
                new ByteArrayInputStream(bloom.getData()), Funnels.stringFunnel(Charsets.UTF_8));
        BloomFilterUtils.init("eth",filter);

        log.info("bloomFilter init success");
    }
}