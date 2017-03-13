package com.colaui.system.utils;

import java.util.UUID;

/**
 * Created by Carl on 2017/3/13.
 */
public class CommonUtils {

    /**获得随机的字符串*/
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
