package com.ldlood.controller;

import com.ldlood.dto.OrderDTO;
import com.ldlood.enums.ResultEnum;
import com.ldlood.exception.SellException;
import com.ldlood.service.OrderService;
import com.ldlood.service.PayService;
import com.ldlood.utils.JsonUtil;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by Ldlood on 2017/7/23.
 */
@Controller
//@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private BestPayServiceImpl bestPayService;

    /**
     * 发起支付
     */
    @GetMapping(value = "/pay")
    public ModelAndView pay(@RequestParam("openid") String openid,
                            @RequestParam("orderId") String orderId,
                            @RequestParam("returnUrl") String returnUrl,
                            Map<String, Object> map) {

        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EX);
        }
        log.info(orderDTO.getBuyerPhone() + "//////////////////////////////////////////////");

        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EX);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", "http://sell.com/#/order/" + orderId);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 异步回调
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {

        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");

    }


    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EX);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

}
