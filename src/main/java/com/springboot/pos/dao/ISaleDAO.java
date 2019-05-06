package com.springboot.pos.dao;

import com.springboot.pos.entity.Sale;
import com.springboot.pos.reposity.SaleReposity;
import org.springframework.data.jpa.repository.Query;

public interface ISaleDAO extends SaleReposity {
    //截取id
    @Query(value = "SELECT max(LEFT(sale_id , 1)) from sale",nativeQuery = true)
    String generateId();

    //查询所有
    @Query(value = "select * from sale", nativeQuery = true)
    Iterable<Sale> displayAllSale();

    //查询所有没有支付的订单
    @Query(value = "select * from sale where is_complete = 0", nativeQuery = true)
    Iterable<Sale> displayAllUncompleteSale();
}
