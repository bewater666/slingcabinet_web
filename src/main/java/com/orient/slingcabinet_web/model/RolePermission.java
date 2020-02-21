package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 13:52
 * @Func:
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePermission {
    private Integer id;
    private Integer roleID;
    private Integer permID;
}
