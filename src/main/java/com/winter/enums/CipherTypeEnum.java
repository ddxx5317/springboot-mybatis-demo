package com.winter.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winter.utils.StringUtils;
public enum CipherTypeEnum {

    NONE("NONE","不加密"), RSA("RSA","RSA"), DES("DES","DES"), AES("AES","AES");

    private String code;

    private String description;

    private CipherTypeEnum(String code, String description) {
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

    public static Map<String, CipherTypeEnum> toMap() {
        CipherTypeEnum[] ary = CipherTypeEnum.values();
        Map<String, CipherTypeEnum> enumMap = new HashMap<>();
        for (int num = 0; num < ary.length; num++) {
            String key = ary[num].name();
            enumMap.put(key, ary[num]);
        }

        return enumMap;
    }

    public static List<CipherTypeEnum> toList() {
        CipherTypeEnum[] ary = CipherTypeEnum.values();

        return Arrays.asList(ary);
    }

    public static CipherTypeEnum getEnumByName(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return toMap().get(name);
    }


    public static CipherTypeEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty(code)) return null;

        for (CipherTypeEnum cipherType : CipherTypeEnum.values()) {
            if (cipherType.getCode().equals(code)) {
                return cipherType;
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
        CipherTypeEnum[] enums = CipherTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (CipherTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{name:'").append(senum.name()).append("code:'").append(senum.getCode()).append("',description:'").append(senum.getDescription()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
