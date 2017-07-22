package com.ldlood.repository;

import com.ldlood.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Ldlood on 2017/7/20.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


    /**
     * 通过类目id查找类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
