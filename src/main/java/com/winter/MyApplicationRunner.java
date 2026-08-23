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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@ConditionalOnProperty(prefix = "app.bloom.preload", name = "enabled", havingValue = "true")
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private BloomService bloomService;

    @Value("${app.bloom.preload.id:7}")
    private Integer bloomId;

    @Value("${app.bloom.preload.key:eth}")
    private String bloomKey;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bloom bloom = bloomService.getBloom(bloomId);
        if (bloom == null || bloom.getData() == null || bloom.getData().length == 0) {
            log.warn("Skip bloom filter preload: no data found for id {}", bloomId);
            return;
        }
        BloomFilter<CharSequence> filter = BloomFilter.readFrom(
                new ByteArrayInputStream(bloom.getData()), Funnels.stringFunnel(Charsets.UTF_8));
        BloomFilterUtils.init(bloomKey, filter);

        log.info("bloomFilter init success");
    }
}
