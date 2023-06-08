package com.sxia.liteflow.pojo;

import lombok.Data;

import java.util.List;
@Data
public class JsonParam {
    private String key;

    private String hashKey;

    private Object hashValue;

    private Object[] values;
    private List<String> list;
}
