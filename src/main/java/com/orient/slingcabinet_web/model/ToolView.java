package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/7/25 9:50
 * @Func:
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolView {
    private String toolName;
    private String toolID;
    private String standard;    //规格
    private String liftingEquipName;    //起重机具名称
    private String tonnage; //吨位
    private String startTime;       //始用日期
    private Integer count;  //计数单位
    private Integer status;     //工具状态 0代表丢失 1代表正常 2代表多出 3代表其他
}
