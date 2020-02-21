package com.orient.slingcabinet_web.utils.exception;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 13:58
 * @Func: 自定义401无权限异常(UnauthorizedException)
 * @Version 1.0
 */

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String msg){
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
