package com.springboot.pos.reposity;

import com.springboot.pos.entity.SaleLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleLineItemReposity extends JpaRepository<SaleLineItem,String> {

}
