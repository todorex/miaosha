package com.todorex.miaosha.exception;

import com.todorex.miaosha.result.CodeMsg;

/**
 * 全局异常
 * @Author rex
 * 2018/10/26
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
