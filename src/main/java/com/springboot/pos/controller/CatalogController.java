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
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    /**
     * 显示所有菜品类别
     * @return
     */
    @RequestMapping("/getAllCatalog")
    @ResponseBody
    private Result getAllCatalog(){
        Iterable<Catalog> it = catalogService.getAllCatalog();
        return Result.success(it);
    }

    /**
     * 按菜品类别编号查询
     * @param catalogId
     * @return
     * @throws Exception
     */
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

    /**
     * 按菜品类别名称查询
     * @param catalogName
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCatalogByName")
    @ResponseBody
    public Iterable<Catalog> getCatalogByName(@RequestParam("ctlName") String catalogName) throws Exception{
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

    /**
     * 删除菜品类别名称
     * @param catalog
     * @return
     * @throws Exception
     */
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

    /**
     * 修改菜品类别名称
     * @param catalog
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCatalog")
    @ResponseBody
    public Result<Catalog> updateCatalog(@RequestBody Catalog catalog) throws Exception{
        try{
            if(catalogService.getCatalogByName(catalog.getCatalogName()) == null)
                return Result.error(204);
            String catalogId = catalog.getCatalogId();
            if(catalogId != null) {
                Catalog ctl = catalogService.updateCatalog(catalogId, catalog.getCatalogName());
                return Result.success(ctl);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500);
        }
        return Result.error(500);
    }

    /**
     * 增加菜品类别
     * @param catalogName
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveCatalog")
    @ResponseBody
    public Result<Catalog> saveCatalog(@RequestParam("ctlName")String catalogName) throws Exception{
        try{
            if(catalogService.getCatalogByName(catalogName) == null){
                Catalog catalog = new Catalog();
                catalog.setCatalogName(catalogName);

                /**
                 * 菜品类别id(头编号[A……Z] + 时间戳)组成
                 */
                char str = catalogService.generateId().charAt(0);
                str += 1;
                String rn = str + "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
                rn.concat(sdf.format(new Date()));
                catalog.setCatalogId(rn);

                catalogService.saveCatalog(catalog);
                return Result.success(catalog);
            }
            return Result.error(204);//该类别已存在
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(500);
        }
    }
}