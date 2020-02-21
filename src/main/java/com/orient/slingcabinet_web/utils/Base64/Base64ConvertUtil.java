package com.orient.slingcabinet_web.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:11
 * @Func: Base64工具类
 * @Version 1.0
 */

public class Base64ConvertUtil {

    /**
     * 加密JDK1.8
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * 解密
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }
}
