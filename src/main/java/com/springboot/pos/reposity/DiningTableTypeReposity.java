package com.springboot.pos.reposity;

import com.springboot.pos.entity.DiningTable;
import com.springboot.pos.entity.DiningTableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiningTableTypeReposity extends JpaRepository<DiningTableType,String> {


}