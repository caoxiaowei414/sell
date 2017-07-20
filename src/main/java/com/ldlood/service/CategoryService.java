package com.ldlood.service;

import com.ldlood.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by Ldlood on 2017/7/20.
 */
public interface CategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
