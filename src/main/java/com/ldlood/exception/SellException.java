package com.ldlood.exception;

import com.ldlood.enums.ResultEnum;

/**
 * Created by Ldlood on 2017/7/22.
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
