package com.springboot.pos.reposity;

import com.springboot.pos.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiningTableReposity extends JpaRepository<DiningTable,String> {

}
