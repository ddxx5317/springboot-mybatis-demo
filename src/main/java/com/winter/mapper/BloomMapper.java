package com.winter.mapper;

import com.winter.model.Bloom;

public interface BloomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bloom record);

    int insertSelective(Bloom record);

    Bloom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bloom record);

    int updateByPrimaryKeyWithBLOBs(Bloom record);

    int updateByPrimaryKey(Bloom record);
}