package com.ldlood.service.impl;

import com.ldlood.dto.OrderDTO;
import com.ldlood.enums.ResultEnum;
import com.ldlood.exception.SellException;
import com.ldlood.service.OrderService;
import com.ldlood.service.PayService;
import com.ldlood.utils.JsonUtil;
import com.ldlood.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;

import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
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

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
//        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOpenid("oTgZpwZqTlluE5vYtpaL8NEQ09uQ");   //使用测试支付账号openid
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 response={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    public PayResponse notify(String notifyData) {


        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付 异步回:" + payResponse);
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            log.error("【微信支付】 异步通知错误,订单不存在，orerid={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EX);
        }
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付】 异步通知错误,订单金额不一致，orerid={},微信通知金额={},订单金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】 request:{}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response:{}", refundResponse);
        return refundResponse;
    }
}
