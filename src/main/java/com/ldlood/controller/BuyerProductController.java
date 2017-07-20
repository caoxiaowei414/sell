package com.ldlood.controller;

import com.ldlood.VO.ProductInfoVO;
import com.ldlood.VO.ProductVO;
import com.ldlood.VO.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by Ldlood on 2017/7/20.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("/list")
    public ResultVO list() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("aaaa");

        ProductVO productVO = new ProductVO();
        productVO.setCategoryName("热榜");
        productVO.setCategoryType(1);

        ProductInfoVO productInfoVO=new ProductInfoVO();
        productInfoVO.setProductDescription("这是一个好外卖");
        productInfoVO.setProductIcon("htttp://aaa.png");
        productInfoVO.setProductId("123456");

        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
        resultVO.setData(Arrays.asList(productVO));
        return resultVO;

    }
}
