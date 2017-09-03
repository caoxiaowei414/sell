package com.ldlood.controller;

import com.ldlood.dataobject.ProductCategory;
import com.ldlood.service.CategoryService;
import com.ldlood.service.ProductService;
import com.ldlood.VO.ProductInfoVO;
import com.ldlood.VO.ProductVO;
import com.ldlood.VO.ResultVO;
import com.ldlood.dataobject.ProductInfo;
import com.ldlood.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ldlood on 2017/7/20.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "#sellerid", condition = "#sellerid.length()>10", unless = "#result.getCode() !=0")
    public ResultVO list(@RequestParam(value = "sellerid", required = false) String sellerid) {

        //查询上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();


        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());


            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);

    }
}
