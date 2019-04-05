package com.springboot.pos.service;

import com.springboot.pos.entity.Catalog;
import com.springboot.pos.reposity.CatalogReposity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("catalogService")
public class CatalogService {

    @Resource
    private CatalogReposity catalogReposity;

    //删除菜品类别
    @Transactional
    public void deleteCatalog(Catalog catalog){
        catalogReposity.delete(catalog);
    }

    //显示菜品类别
    @Transactional
    public Iterable<Catalog> getAllCatalog(){
        return catalogReposity.findAll();
    }

    //按菜品类别名称查询
    @Transactional
    public Catalog getCatalogByName(String catalogName){
        Catalog catalog = catalogReposity.findByCatalogName(catalogName);
        return (catalog != null)? catalog:null;
    }

    //按类别名称模糊查询
    @Transactional
    public Iterable<Catalog> getCatalogByCName(String catalogName){
        Iterable<Catalog> catalog = catalogReposity.findByCName(catalogName);
        return catalog;
    }


    //按菜品类别编号查询
    @Transactional
    public Catalog getCatalogById(String catalogId){
        Optional<Catalog> catalog = catalogReposity.findById(catalogId);
        return catalog.isPresent()?catalog.get():null;
    }

    //修改菜品类别名称
    @Transactional
    public Catalog updateCatalog(String catalogName,String catalogId){
        return catalogReposity.updateCatalog(catalogName,catalogId);
    }

    //增加菜品类别
    @Transactional
    public Catalog saveCatalog(Catalog catalog){
        return catalogReposity.save(catalog);
    }

    //截取id
    @Transactional
    public String generateId(){
        return catalogReposity.generateId();
    }
}
