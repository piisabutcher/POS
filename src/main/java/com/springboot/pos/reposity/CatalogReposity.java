package com.springboot.pos.reposity;

import com.springboot.pos.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CatalogReposity extends JpaRepository<Catalog,String> {

}
