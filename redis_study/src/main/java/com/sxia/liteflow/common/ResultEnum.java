package com.sxia.liteflow.common;

import java.util.HashMap;
import java.util.Map;

public enum ResultEnum {
    SUCCESS ("200","success"),
    ERROR("400", "error");
    private String code;
    private String msg;

    ResultEnum (String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static Map<String, ResultEnum> map = new HashMap<>();
    static {
        ResultEnum[] ens = ResultEnum.values();
        for(ResultEnum en : ens){
            map.put(en.code, en);
        }
    }
    public  String getCode() {
        return this.code;
    }
    public static ResultEnum getEnumByCode(String code) {
        return map.get(code);
    }
}
