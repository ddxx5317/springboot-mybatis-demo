package com.winter.Controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.winter.model.User;
import com.winter.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
    	user.setUserName("user");
    	user.setPassword("user001");
    	user.setPhone("111");
    	JSONObject config = new JSONObject();
    	config.put("AA", "A001");
    	user.setConfig(config);
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
    	List<User> users= userService.findAllUser(pageNum,pageSize);
        return users;
    }
}
