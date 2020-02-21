package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 13:41
 * @Func: 角色实体类
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private Integer roleID;
    private String roleName;
}
