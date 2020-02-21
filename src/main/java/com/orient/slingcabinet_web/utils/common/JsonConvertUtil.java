package com.orient.slingcabinet_web.utils.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: zhoudun
 * @Date: 2019/3/15 15:55
 * @Func: Json和Object的互相转换，转List必须Json最外层加[]，转Object，Json最外层不要加[]
 * @Version 1.0
 */

public class JsonConvertUtil {
    /**
     * JSON 转 Object
     */
    public static <T> T jsonToObject(String pojo, Class<T> clazz) {
        return JSONObject.parseObject(pojo, clazz);
    }

    /**
     * Object 转 JSON
     */
    public static <T> String objectToJson(T t){
        return JSONObject.toJSONString(t);
    }
}
