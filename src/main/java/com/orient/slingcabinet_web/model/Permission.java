package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 13:43
 * @Func:
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    private Integer permID;
    private String remark;
    /**
     * 权限代码字符串
     */
    private String perm_code;


}
