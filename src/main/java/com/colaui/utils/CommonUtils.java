package com.colaui.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Carl on 2017/3/13.
 */
public class CommonUtils {
    private static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z'};

    /**
     * 生成uuid
     * @return 移除了 "-" 的uuid字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机码生成器
     * @param length 长度参数
     * @return
     */
    public static String keyGenerator(int length) {
        if (length < 1) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        int i = 0;
        do {
            buffer.append(chars[random.nextInt(chars.length)]);
            i++;
        } while (i < length);
        return buffer.toString();
    }
}