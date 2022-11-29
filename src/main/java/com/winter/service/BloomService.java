package com.winter.service;

import com.winter.model.Bloom;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface BloomService {

    int addBloom(Bloom bloom);

    Bloom getBloom(Integer id);
}