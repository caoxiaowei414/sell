package com.ldlood.enums;

import lombok.Getter;

/**
 * Created by Ldlood on 2017/7/20.
 */
@Getter
public enum ProductStatusEnum {

    UP(0,"上架"),
    DOWN(1,"下架")
    ;
    private Integer code;

    private String mssage;

    ProductStatusEnum(Integer code, String mssage) {
        this.code = code;
        this.mssage = mssage;
    }
}
