package com.hanyin.website.service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回值结构
 * @param <T> 返回数据类型
 */
@Setter
@Getter
public class Response<T> {

    private boolean status = false;
    private T data;
    private String errorMessage;

    public String response() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", status);
        if (status) resultMap.put("data", data);
        else resultMap.put("errorMessage", errorMessage);
        return JSONObject.valueToString(resultMap);
    }
}
