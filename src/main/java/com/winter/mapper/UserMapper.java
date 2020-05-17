package com.winter.mapper;

import com.winter.model.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAllUser();

    int insertBatch(List<User> users);
}