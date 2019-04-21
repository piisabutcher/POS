package com.springboot.pos.service.impl;

import com.springboot.pos.dao.ICatalogDAO;
import com.springboot.pos.entity.Catalog;
import com.springboot.pos.reposity.CatalogReposity;
import com.springboot.pos.service.ICatalogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("catalogService")
public class CatalogService implements ICatalogService{

    @Resource
    private ICatalogDAO IcatalogDao;

    //删除菜品类别
    @Transactional
    public void deleteCatalog(Catalog catalog){
        IcatalogDao.delete(catalog);
    }

    //显示菜品类别
    @Transactional
    public Iterable<Catalog> getAllCatalog(){
        return IcatalogDao.findAll();
    }

    //按菜品类别名称查询
    @Transactional
    public Catalog getCatalogByName(String catalogName){
        Catalog catalog = IcatalogDao.findByCatalogName(catalogName);
        return (catalog != null)? catalog:null;
    }

    //按类别名称模糊查询
    @Transactional
    public Iterable<Catalog> getCatalogByCName(String catalogName){
        Iterable<Catalog> catalog = IcatalogDao.findByCName(catalogName);
        return catalog;
    }


    //按菜品类别编号查询
    @Transactional
    public Catalog getCatalogById(String catalogId){
        Optional<Catalog> catalog = IcatalogDao.findById(catalogId);
        return catalog.isPresent()?catalog.get():null;
    }

    //修改菜品类别名称
    @Transactional
    public Catalog updateCatalog(String catalogName,String catalogId){
        return IcatalogDao.updateCatalog(catalogName,catalogId);
    }

    //增加菜品类别
    @Transactional
    public Catalog saveCatalog(Catalog catalog){
        return IcatalogDao.save(catalog);
    }

}