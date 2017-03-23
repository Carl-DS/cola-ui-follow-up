package com.colaui.utils;

import java.util.UUID;

/**
 * Created by Carl on 2017/3/13.
 */
public class CommonUtils {

    /**生成uuid*/
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
