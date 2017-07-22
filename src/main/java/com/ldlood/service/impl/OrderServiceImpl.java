package com.ldlood.service.impl;

import com.ldlood.dataobject.OrderDetail;
import com.ldlood.dataobject.OrderMaster;
import com.ldlood.dataobject.ProductInfo;
import com.ldlood.dto.OrderDTO;
import com.ldlood.enums.ResultEnum;
import com.ldlood.exception.SellException;
import com.ldlood.repository.OrderDetailRepository;
import com.ldlood.repository.OrderMasterRepository;
import com.ldlood.service.OrderService;
import com.ldlood.service.ProductService;
import com.ldlood.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Ldlood on 2017/7/22.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal ordeAmount = new BigDecimal(BigInteger.ZERO);

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            ordeAmount = orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(ordeAmount);  //订单总金额

            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(ordeAmount);
        BeanUtils.copyProperties(orderDTO, orderMaster);

//        productService
        return null;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
