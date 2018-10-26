package com.todorex.miaosha.util;

import java.util.UUID;

/**
 * @Author rex
 * 2018/10/26
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
