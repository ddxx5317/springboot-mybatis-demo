package com.winter.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public enum SexEnum implements CodedEnum{
	
	FEMALE(0,"女"),
	MALE(1,"男")
	;
    private Integer code;

    private String description;
    

    private SexEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Map<String, SexEnum> toMap() {
        SexEnum[] ary = SexEnum.values();
        Map<String, SexEnum> enumMap = new HashMap<>();
        for (int num = 0; num < ary.length; num++) {
            String key = ary[num].name();
            enumMap.put(key, ary[num]);
        }

        return enumMap;
    }

    public static List<SexEnum> toList() {
        SexEnum[] ary = SexEnum.values();

        return Arrays.asList(ary);
    }

    public static SexEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) return null;

        return toMap().get(name);
    }


    public static SexEnum getEnumByCode(Integer code) {
        if (null == code) return null;

        for (SexEnum enableStatus : SexEnum.values()) {
            if (enableStatus.getCode().equals(code)) {
                return enableStatus;
            }
        }

        return null;
    }
}

