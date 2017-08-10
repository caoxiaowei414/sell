package com.ldlood.controller;

import com.ldlood.dataobject.ProductCategory;
import com.ldlood.dataobject.ProductInfo;
import com.ldlood.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by Ldlood on 2017/8/10.
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {

        if (categoryId > 0) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
        }

        return new ModelAndView("product/index", map);
    }
}
