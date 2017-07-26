package com.ldlood.controller;

import com.ldlood.dto.OrderDTO;
import com.ldlood.enums.ResultEnum;
import com.ldlood.exception.SellException;
import com.ldlood.service.OrderService;
import com.ldlood.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by Ldlood on 2017/7/23.
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {

        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EX);
        }
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        //发起支付
        return new ModelAndView("pay/create", map);
    }

}
