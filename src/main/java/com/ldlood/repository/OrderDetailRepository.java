package com.ldlood.repository;

import com.ldlood.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Ldlood on 2017/7/21.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {


    /**
     * 通过订单id查找订单详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
