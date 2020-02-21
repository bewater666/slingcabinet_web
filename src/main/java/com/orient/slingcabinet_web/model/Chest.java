package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 9:52
 * @Func: 柜子实体类
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chest {
    private Integer id;
    private String chestID;
    private String chestName;//消防柜名称
}
