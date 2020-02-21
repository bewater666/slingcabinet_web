package com.orient.slingcabinet_web.utils.exception;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 11:30
 * @Func: 自定义异常类
 * @Version 1.0
 */

public class CustomException extends RuntimeException{
    public CustomException(String msg){
        super(msg);
    }

    public CustomException() {
        super();
    }
}
