package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/25 9:46
 * @Func:
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SweepRecordView {
    private String chestID;
    private String chestName;
    private Integer chestStatus;
    private String time;
    private Integer num;
    private List<ToolView> sweepToolList;

}
