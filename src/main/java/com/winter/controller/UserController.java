package com.winter.controller;

import com.alibaba.fastjson.JSONObject;
import com.winter.enums.SexEnum;
import com.winter.mapper.UserMapper;
import com.winter.model.User;
import com.winter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
    	user.setUserName("user");
    	user.setPassword("user001");
    	user.setPhone("111");
    	JSONObject config = new JSONObject();
    	config.put("AA", "A001");
    	user.setConfig(config);
    	user.setSex(SexEnum.FEMALE);
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/add2", produces = {"application/json;charset=UTF-8"})
    public String addUser2(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 20000; i++) {
            User user = new User();
            user.setUserName("user"+i);
            user.setPassword("user00"+i);
            user.setPhone("111");
            JSONObject config = new JSONObject();
            config.put("AA", "A001");
            user.setConfig(config);
            user.setSex(SexEnum.FEMALE);
            userService.addUser(user);
        }
        stopWatch.stop();
        log.info("耗时：{}",stopWatch.getTotalTimeSeconds());//30ms
        return "OK";
    }

    @ResponseBody
    @RequestMapping(value = "/add3", produces = {"application/json;charset=UTF-8"})
    public String addUser3(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<User> users = new ArrayList<>();
        User user = null;
        for (int i = 1; i <= 25000; i++) {
            user = new User();
            user.setUserName("user"+i);
            user.setPassword("user00"+i);
            user.setPhone("111");
            JSONObject config = new JSONObject();
            config.put("AA", "短信推送内容：马上就要过年了，本公司将进行打折处理一部分商品");
            config.put("BB", "App推送内容：马上就要放进了，我们可以去happy了啊");
            user.setConfig(config);
            user.setSex(SexEnum.FEMALE);
            users.add(user);
        }
        userService.addBatchUsers(users);
        stopWatch.stop();
        log.info("耗时：{}",stopWatch.getTotalTimeSeconds());
        return "OK";
    }

    @ResponseBody
    @RequestMapping(value = "/add4", produces = {"application/json;charset=UTF-8"})
    public String addUser4(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<User> users = new ArrayList<>();
        User user = null;
        for (int i = 1; i <= 25000; i++) {
            user = new User();
            user.setUserName("user"+i);
            user.setPassword("user00"+i);
            user.setPhone("111");
            JSONObject config = new JSONObject();
            config.put("AA", "短信推送内容：马上就要过年了，本公司将进行打折处理一部分商品");
            config.put("BB", "App推送内容：马上就要放进了，我们可以去happy了啊");
            user.setConfig(config);
            user.setSex(SexEnum.FEMALE);
            users.add(user);
        }

        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            int i = 0;
            for (User u : users) {
                mapper.insert(u);
                i++;
                if(i%1000==999 || i== users.size()-1) {
                    session.commit();
                    session.clearCache();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        stopWatch.stop();
        log.info("耗时：{}",stopWatch.getTotalTimeSeconds());
        return "OK";
    }


    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
    	List<User> users= userService.findAllUser(pageNum,pageSize);
        return users;
    }
}
