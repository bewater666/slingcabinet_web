package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 9:46
 * @Func: 工具实体类
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tool {
    private Integer id;
    private String toolID;          //工具ID
    private String toolName;    //工具名称
    private String chestID;     //柜子ID
    private String standard;    //规格
    private String liftingEquipName;    //起重机具名称
    private String tonnage; //吨位
    private String startTime;       //始用日期
}
