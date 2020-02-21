package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 14:19
 * @Func: 硬件扫描的数据实体类
 * @Version 1.0
 */
@Data@JsonInclude(JsonInclude.Include.NON_NULL)
public class SweepRecord {
    private Integer id;
    private String toolNames;   //扫描到的工具名称列表
    private String toolIDs;     //扫描到的工具id列表
    private String chestID;
    private Integer chestStatus;    //柜子状态 0异常 1正常  2未扫描
    private Integer nums;
    private String time;
    private String missedToolNames; //丢失的工具名称列表
    private String missedToolIDs;   //丢失到的工具id列表
    private String extraToolNames;  //多出的工具名称列表
    private String extraToolIDs;    //多出的工具id列表
}
