package com.orient.slingcabinet_web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/15 16:16
 * @func
 */
@Data
@JsonInclude
public class ChestViewReal {
    private Integer num_status0;
    private Integer num_status1;
    private Integer num_status2;
    private List<ChestView> chestViewList;

}
