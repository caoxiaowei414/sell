package com.ldlood.service.impl;

import com.ldlood.dto.OrderDTO;
import com.ldlood.service.PayService;
import com.ldlood.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Ldlood on 2017/7/23.
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐(ldlood店)";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public void create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 request={}", JsonUtil.toJson(payRequest) );
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 response={}", JsonUtil.toJson(payResponse));

    }
}
