package com.ldlood.repository;

import com.ldlood.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ldlood on 2017/7/21.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 通过openid查找订单
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
