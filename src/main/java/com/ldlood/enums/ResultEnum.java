package com.ldlood.enums;

import lombok.Getter;

/**
 * Created by Ldlood on 2017/7/22.
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIT(10, "商品不存在"),;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
