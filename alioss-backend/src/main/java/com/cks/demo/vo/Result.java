package com.cks.demo.vo;

import com.cks.demo.common.ResultCodeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: Result
 * @description: 返回结果的链式写法
 * @author: cksspk
 * @date: 2020/3/28
 **/
@Data
public class Result {

    private boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();



    private Result(){}

    /**
     * 操作成功
     * @return
     */
    public static Result ok(){
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }


    /**
     * 操作失败
     * @return
     */
    public static Result error(){
        Result r = new Result();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }


    /**
     * 根据参数返回
     * @param resultCodeEnum
     * @return
     */
    public static Result setResult(ResultCodeEnum resultCodeEnum){
        Result r = new Result();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }


    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
