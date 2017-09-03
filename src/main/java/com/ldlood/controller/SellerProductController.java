package com.ldlood.controller;

import com.ldlood.dataobject.ProductCategory;
import com.ldlood.dataobject.ProductInfo;
import com.ldlood.form.ProductForm;
import com.ldlood.service.CategoryService;
import com.ldlood.service.ProductService;
import com.ldlood.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Ldlood on 2017/8/4.
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView Index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {

        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", map);
    }

    @PostMapping("/save")
    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }


        try {
            ProductInfo productInfo = new ProductInfo();
            //更新
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            }
            //新增
            else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);

        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);

    }
}
