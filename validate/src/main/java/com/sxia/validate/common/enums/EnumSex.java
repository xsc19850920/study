package com.sxia.validate.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EnumSex {
    MALE(0, "男"),
    FEMALE(1, "女");
    private Integer code;
    private String message;


    public Integer getCode() {
        return this.code;
    }

    public static EnumSex getEnumByCode(String code) {
        return map.get(code);
    }

    EnumSex(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static Map<Integer, EnumSex> map;

    static {
        EnumSex[] values = EnumSex.values();
        map = Arrays.stream(values).collect(Collectors.toMap(item -> item.code, item -> item));
    }
}
