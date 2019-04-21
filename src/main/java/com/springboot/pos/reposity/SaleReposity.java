package com.springboot.pos.reposity;

import com.springboot.pos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleReposity extends JpaRepository<Sale,String> {


}
