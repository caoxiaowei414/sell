package com.ldlood.repository;

import com.ldlood.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ldlood on 2017/7/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;


    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId("123123456456");
        productInfo.setProductName("milk");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("good milk");
        productInfo.setProductIcon("http://aa.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() throws Exception {

        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }

}