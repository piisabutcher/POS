package com.springboot.pos.dao;

import com.springboot.pos.entity.Catalog;
import com.springboot.pos.reposity.CatalogReposity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICatalogDAO extends CatalogReposity {
    //按类别名称查询
    @Query(value = "select * from catalog where catalog_name = ?1", nativeQuery = true)
    Catalog findByCatalogName(String catalogName);

    //按类别名称模糊查询
    @Query(value = "select * from catalog where catalog_name like %?1%", nativeQuery = true)
    Iterable<Catalog> findByCName(String catalogName);

    //修改菜品类别
    @Query(value = "update catalog as c set c.catalog_name = :cname where c.catalog_id = :cid",nativeQuery = true)
    @Modifying
    Catalog updateCatalog(@Param("cname") String name, @Param("cid")String cid);

    //截取id
    @Query(value = "SELECT max(LEFT(catalog_id , 1)) from catalog",nativeQuery = true)
    String generateId();
}