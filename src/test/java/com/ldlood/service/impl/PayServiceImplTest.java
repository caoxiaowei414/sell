package com.ldlood.service.impl;

import com.ldlood.dto.OrderDTO;
import com.ldlood.service.OrderService;
import com.ldlood.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Ldlood on 2017/7/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1502536859211684476");
        payService.create(orderDTO);

    }

    @Test
    public void refund() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1502543762731180136");
        payService.refund(orderDTO);

    }



}