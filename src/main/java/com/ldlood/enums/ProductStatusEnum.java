package com.ldlood.enums;

import lombok.Getter;

/**
 * Created by Ldlood on 2017/7/20.
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {

    UP(0, "上架"),
    DOWN(1, "下架");
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

