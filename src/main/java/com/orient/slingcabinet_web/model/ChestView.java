package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/15 9:54
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChestView{
    private String chestID;
    private String chestName;//消防柜名称
    private Integer status;   //柜子的状态   0代表异常 1代表正常  2代表未对该柜子进行扫描  3代表其他
}
