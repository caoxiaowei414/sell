package com.ldlood.utils;

import java.util.Random;

/**
 * Created by Ldlood on 2017/7/22.
 */
public class KeyUtil {

    /**
     * 生产主键
     *
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();

        Integer result = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(result);

    }
}
