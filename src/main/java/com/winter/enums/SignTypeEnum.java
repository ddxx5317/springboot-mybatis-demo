package com.winter.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winter.utils.StringUtils;

/**
 * Created by suye on 2017/6/19.
 */
public enum SignTypeEnum {
    NONE("NONE", "不验签"), RSA("RSA", "RSA"), MD5("MD5", "MD5");

    private String code;

    private String description;

    private SignTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Map<String, SignTypeEnum> toMap() {
        SignTypeEnum[] ary = SignTypeEnum.values();
        Map<String, SignTypeEnum> enumMap = new HashMap<>();
        for (int num = 0; num < ary.length; num++) {
            String key = ary[num].name();
            enumMap.put(key, ary[num]);
        }

        return enumMap;
    }

    public static List<SignTypeEnum> toList() {
        SignTypeEnum[] ary = SignTypeEnum.values();

        return Arrays.asList(ary);
    }

    public static SignTypeEnum getEnumByName(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return toMap().get(name);
    }


    public static SignTypeEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty(code)) return null;

        for (SignTypeEnum signType : SignTypeEnum.values()) {
            if (signType.getCode().equals(code)) {
                return signType;
            }
        }

        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        SignTypeEnum[] enums = SignTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (SignTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{name:'").append(senum.name()).append("code:'").append(senum.getCode()).append("',description:'").append(senum.getDescription()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}