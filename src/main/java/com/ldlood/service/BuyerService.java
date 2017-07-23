package com.ldlood.service;

import com.ldlood.dto.OrderDTO;

/**
 * Created by Ldlood on 2017/7/23.
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
