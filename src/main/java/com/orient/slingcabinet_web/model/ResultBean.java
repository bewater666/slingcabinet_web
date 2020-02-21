package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author zhoudun
 * @Description: Date: Created in 17:37 2018/10/15
 * @ModifiedBy:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean {
    private Integer code;

    private String message;

    private Object result;

    public ResultBean() {
        this.code = 200;
        this.message = "操作成功";
    }

    public ResultBean(Object data) {
        this.code = 200;
        this.message = "操作成功";
        this.result = data;
    }

    public ResultBean(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }
}
