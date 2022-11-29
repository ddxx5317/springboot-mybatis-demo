package com.winter.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.winter.model.Bloom;
import com.winter.service.BloomService;
import com.winter.utils.BloomFilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/bloom")
@Slf4j
public class BloomController {
    private final static String SUCCESS = "SUCCESS";

    @Autowired
    private BloomService bloomService;

    @GetMapping(value = "/test/{value}")
    public String test(@PathVariable("value") String value) throws Exception {
        BloomFilter filter = BloomFilterUtils.get("eth");
        boolean result = filter.mightContain(value);
        log.info("result:{}",result);
        return SUCCESS;
    }

    @GetMapping(value = "/check/{id}")
    public String checkBloom(@PathVariable("id") Integer id) throws Exception {
        Bloom bloom = bloomService.getBloom(id);
        BloomFilter<CharSequence> filter = BloomFilter.readFrom(new ByteArrayInputStream(bloom.getData()), Funnels.stringFunnel(Charsets.UTF_8));
        for (int i = 5; i < 15; i++) {
            final boolean b = filter.mightContain("1000000" + i);
            log.info("result:{}",b);
        }
        return SUCCESS;
    }

    @GetMapping(value = "/add")
    public String addBloom() throws Exception {
        Bloom bloom = new Bloom();
        bloom.setChainId("eth");

        int count = 10000000;
        final BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charsets.UTF_8),count,0.01);
        for (int i = 0; i < count; i++) {
            bloomFilter.put("0x000E633ddeF00DA46aBd5044779257a64ead9bce"+i);
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bloomFilter.writeTo(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bloom.setData(out.toByteArray());
        bloomService.addBloom(bloom);
        return SUCCESS;
    }
}
