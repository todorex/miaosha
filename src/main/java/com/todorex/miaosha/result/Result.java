package com.todorex.miaosha.result;

import lombok.Data;

/**
 * 返回类
 * @Author rex
 * 2018/10/24
 */
@Data
public class Result<T> {
    /**
     * 结果代号
     */
    private int code;

    /**
     * 结果信息
     */
    private String msg;

    /**
     * 结果数据
     */
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    /**
     * 返回成功的时候调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 返回失败的时候调用
     * @param cm
     * @param <T>
     * @return
     */
    public static <T> Result<T> failure(CodeMsg cm) {
        return new Result<T>(cm);
    }
}
