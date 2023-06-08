package com.sxia.liteflow.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应操作, 用于请求完成后, 对响应数据的操作
 */
public class Response {
    private final static String MESSAGE = "message";//消息
    private final static String CODE = "code";//响应码

    private Response() {

    }
	//HttpStatus:这是Spring提供的一个枚举类，其遵循RESTful风格封装了大量了响应状态码。详见org.springframework.http.HttpStatus;
    private static ResponseEntity<Object> getEntity(Object body, HttpStatus statusCode) {
        //一次获取所有Header,如果 Header中有多个参数，可以使用 MultiValueMap来接收参数值。
        //MultiValueMap继承以Map,是一个可以让key拥有多个value的
        MultiValueMap<String, String> headers = new HttpHeaders();
        List<String> contentType = new ArrayList<String>();
        contentType.add("application/json;charset=utf-8");
        headers.put("Content-Type", contentType);
        return new ResponseEntity<Object>(body, headers, statusCode);
    }


    /**
     * 请求成功,无需返回结果集
     */
    public static ResponseEntity<Object> success() {
        Map<String, Object> result = new HashMap<String, Object>();
        return getEntity(result, HttpStatus.OK);
    }

    /**
     * 请求成功，根据msg和code来设置响应
     * 多用于 添加、修改、删除、更新等提示信息。
     */
    public static ResponseEntity<Object> success(String msg) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", true);
        vo.put("message", msg);
        vo.put("code", ResultEnum.SUCCESS.getCode());
        return getEntity(vo, HttpStatus.OK);
    }


    public static ResponseEntity<Object> success(String msg, Object object) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", true);
        vo.put("message", msg);
        vo.put("code", ResultEnum.SUCCESS.getCode());
        vo.put("data", object);
        return getEntity(vo, HttpStatus.OK);
    }
    
    /**
     * 返回分页使用
     *
     * @param msg  响应信息
     * @param page 分页对象
     * @return 响应
     */
//    public static ResponseEntity<Object> success(String msg, Page page) {
//        Map<String, Object> vo = new HashMap<>();
//        vo.put("sucFlag", true);
//        vo.put("message", msg);
//        vo.put("totalCount", page.getTotalCount());
//        vo.put("pageSize", page.getTotalPageCount());
//        vo.put("code", ResultEnum.SUCCESS.getCode());
//        vo.put("data", page.getResult());
//        return getEntity(vo, HttpStatus.OK);
//    }


    /**
     * 请求成功,并返回请求结果集
     *
     * @param object 返回到客户端的对象
     * @return Spring mvc ResponseEntity
     */
    public static ResponseEntity<Object> success(Object object) {
        return getEntity(object, HttpStatus.OK);
    }


    /**
     * 服务器错误(new)   多用于controller 错误返回的信息展示
     *
     * @param msg  请求失败的错误信息
     * @param code
     * @return Spring mvc ResponseEntity
     */
    public static ResponseEntity<Object> serverError(String msg, Integer code) {
        Map<String, Object> vo = new HashMap<>();
        vo.put("sucFlag", false);
        vo.put("message", msg);
        vo.put("code", code);
//        return getEntity(vo, HttpStatus.INTERNAL_SERVER_ERROR);
        return getEntity(vo, HttpStatus.OK);
    }
}
