package com.winter.service;

import com.winter.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    int addUser(User user);

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<User> findAllUser(int pageNum, int pageSize);

    int addBatchUsers(List<User> users);

}
