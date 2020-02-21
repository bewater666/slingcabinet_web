package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/27 15:36
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SweepRecordVo {
    private String time;
    private Integer chestStatus;
    private String chestName;
    private List<ToolView> missedToolList;  //缺失的工具list 如果有的话
    private List<ToolView> extraToolList;   //多出的的工具list 如果有的话
    private List<ToolView> sweepToolList;   //扫描到的工具list 如果有的话
}
