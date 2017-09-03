package com.ldlood.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Ldlood on 2017/7/21.
 */
@Data
public class ProductInfoVO implements Serializable {


    private static final long serialVersionUID = 5836171251278038743L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
