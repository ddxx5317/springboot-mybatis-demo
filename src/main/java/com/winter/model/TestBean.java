package com.winter.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: DDxx
 * @Date: 2020/6/1
 */
@Data
@ToString
public class TestBean implements Serializable {
    private static final long serialVersionUID = 3425780236909048941L;
    private String name;
    private Integer age;
    public TestBean() {}
    public TestBean(String name, Integer age) {
        this.name = name;
        this.age = age;


    }
}
