package com.winter.model;

import com.alibaba.fastjson.JSONObject;
import com.winter.enums.SexEnum;
import com.winter.utils.Signature;
import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String phone;
    
    private JSONObject config;
    
    private Signature signature;   
    
    private SexEnum sex;
}