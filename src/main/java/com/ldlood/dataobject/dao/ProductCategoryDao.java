package com.ldlood.dataobject.dao;

import com.ldlood.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Ldlood on 2017/8/16.
 */
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper mapper;

    public int insertByMap(Map<String, Object> map) {
        return mapper.insertByMap(map);
    }
}
