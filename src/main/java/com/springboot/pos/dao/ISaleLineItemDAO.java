package com.springboot.pos.dao;

import com.springboot.pos.entity.SaleLineItem;
import com.springboot.pos.reposity.SaleLineItemReposity;
import org.springframework.data.jpa.repository.Query;

public interface ISaleLineItemDAO extends SaleLineItemReposity {
    //截取id
    @Query(value = "SELECT max(LEFT(sli_id , 1)) from sale_line_item",nativeQuery = true)
    String generateId();

    //查询订单明细
    @Query(value = "select * from sale_line_item where sale_id = ?1", nativeQuery = true)
    Iterable<SaleLineItem> displaySli(String sli);

    //查询订单数目
    @Query(value = "select count(*) from sale_line_item where sale_id = ?1",nativeQuery = true)
    int countSli(String sale_id);
}
