package com.springboot.pos.service;

import com.springboot.pos.entity.Catalog;

public interface ICatalogService{
    void deleteCatalog(Catalog catalog);

    Iterable<Catalog> getAllCatalog();

    Catalog getCatalogByName(String catalogName);

    Catalog getCatalogById(String catalogId);

    Catalog updateCatalog(String catalogName,String catalogId);

    Catalog saveCatalog(Catalog catalog);

}
