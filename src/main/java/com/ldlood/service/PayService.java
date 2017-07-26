package com.ldlood.service;

import com.ldlood.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.stereotype.Service;

/**
 * Created by Ldlood on 2017/7/23.
 */
public interface PayService {
    PayResponse create(OrderDTO orderDTO);
}
