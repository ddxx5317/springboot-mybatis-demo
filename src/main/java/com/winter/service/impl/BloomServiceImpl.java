package com.winter.service.impl;

import com.winter.mapper.BloomMapper;
import com.winter.model.Bloom;
import com.winter.service.BloomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "bloomService")
public class BloomServiceImpl implements BloomService {

    @Resource
    private BloomMapper bloomMapper;
    @Override
    public int addBloom(Bloom bloom) {
        return bloomMapper.insert(bloom);
    }

    @Override
    public Bloom getBloom(Integer id) {
        return bloomMapper.selectByPrimaryKey(id);
    }
}
