package com.ldlood.service.impl;

import com.ldlood.dto.CartDTO;
import com.ldlood.enums.ProductStatusEnum;
import com.ldlood.dataobject.ProductInfo;
import com.ldlood.enums.ResultEnum;
import com.ldlood.exception.SellException;
import com.ldlood.repository.ProductInfoRepository;
import com.ldlood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by Ldlood on 2017/7/20.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;


    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {

        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
            }
            Integer resultNum = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(resultNum);
            productInfoRepository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
            }
            Integer num = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (num < 0) {
                throw new SellException(ResultEnum.PRODUC_STOCK_ERROR);
            }

            productInfo.setProductStock(num);
            productInfoRepository.save(productInfo);
        }
    }
}
