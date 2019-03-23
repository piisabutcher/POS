package com.springboot.pos.controller;

import com.springboot.pos.entity.Catalog;
import com.springboot.pos.service.CatalogService;
import com.springboot.pos.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    //显示所有菜品类别
    @RequestMapping("/getAllCatalog")
    @ResponseBody
    private Iterable<Catalog> getAllCatalog(){
        Iterable<Catalog> it = catalogService.getAllCatalog();
        System.out.println(it.iterator().next().getCatalogName());
        return it;
    }

    //按菜品类别编号查询
    @RequestMapping("/getCatalogById")
    @ResponseBody
    public Catalog getCatalogById(@RequestParam("cid") String catalogId) throws Exception{
        try{
            Catalog catalog = catalogService.getCatalogById(catalogId);
            if(catalog == null)
                return null;
            else
                return catalog;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //按菜品类别名称查询
    @RequestMapping("/getCatalogByName")
    @ResponseBody
    public Iterable<Catalog> getCatalogByName(@RequestParam("cname") String catalogName) throws Exception{
        try{
            Iterable<Catalog> catalog = catalogService.getCatalogByCName(catalogName);
            if(catalog == null)
                return null;
            else
                return catalog;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除菜品类别名称
    @RequestMapping("/deleteCatalog")
    @ResponseBody
    public Result<Catalog> deleteCatalog(@RequestBody Catalog catalog) throws Exception{
        try{
            catalogService.deleteCatalog(catalog);
            return Result.success(catalog);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500);
        }
    }

    //修改菜品类别名称
    @RequestMapping("/updateCatalog")
    @ResponseBody
    public Result<Catalog> updateCatalog(@RequestBody Catalog catalog,
                                         @RequestParam("newName") String newCatalogName) throws Exception{
        try{
            if(catalogService.getCatalogByName(newCatalogName) != null)
                return Result.error(222);
            String catalogId = catalog.getCatalogId();
            if(catalogId != null) {
                catalogService.updateCatalog(catalogId, newCatalogName);
                Catalog c = new Catalog(catalogId, newCatalogName);
                return Result.success(c);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500);
        }
        return Result.error(500);
    }

    //增加菜品类别
    @RequestMapping("/saveCatalog")
    @ResponseBody
    public Result<Catalog> saveCatalog(@RequestParam("cname")String catalogName) throws Exception{
        try{
            if(catalogService.getCatalogByName(catalogName) == null){
                Catalog catalog = new Catalog();
                catalog.setCatalogId(String.valueOf((int)(Math.random()*9+1)*100000));//同userId
                catalog.setCatalogName(catalogName);
                catalogService.saveCatalog(catalog);
                return Result.success(catalog);
            }
            return Result.error(222);//该类别已存在
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500);
        }
    }
}