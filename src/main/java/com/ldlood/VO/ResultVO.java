package com.ldlood.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Ldlood on 2017/7/21.
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3236329195874147801L;
    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
