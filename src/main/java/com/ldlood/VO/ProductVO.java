package com.ldlood.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ldlood on 2017/7/21.
 */
@Data
public class ProductVO implements Serializable {


    private static final long serialVersionUID = 3226073569081191364L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}