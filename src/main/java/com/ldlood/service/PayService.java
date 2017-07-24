package com.ldlood.service;

import com.ldlood.dto.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * Created by Ldlood on 2017/7/23.
 */
public interface PayService {
    void create(OrderDTO orderDTO);
}
