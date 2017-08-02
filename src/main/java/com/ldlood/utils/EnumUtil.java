package com.ldlood.utils;

import com.ldlood.enums.CodeEnum;
import com.ldlood.enums.OrderStatusEnum;

/**
 * Created by Ldlood on 2017/8/2.
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
