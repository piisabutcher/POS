package com.springboot.pos.controller;

import com.springboot.pos.entity.Food;
import com.springboot.pos.service.impl.CatalogService;
import com.springboot.pos.service.impl.FoodService;
import com.springboot.pos.util.Result;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/food")
public class FoodController {
    @Resource
    private FoodService foodService;
    @Resource
    private CatalogService catalogService;

    /**
     *获取所有菜品
     * @return
     */
    @RequestMapping("/getAllFood")
    @ResponseBody
    private Result<Food> getAllFood(){
        Iterable<Food> it = foodService.getAllFood();
        return Result.success(it);
    }

    /**
     *按id查询菜品
     * @param foodId
     * @return
     */
    @RequestMapping("/getFoodById")
    @ResponseBody
    private Result<Food> getFoodById(@RequestParam("foodId")String foodId){
        Food f = foodService.getFoodByFoodId(foodId);
        return Result.success(f);
    }

    /**
     *增加菜品
     * @param food
     * @return
     */
    @RequestMapping("/saveFood")
    @ResponseBody
    private Result<Food> save(@RequestBody Food food){
        System.out.println(JSONObject.fromObject(food));
        food.setCatalog(catalogService.getCatalogById(food.getCatalog().getCatalogId()));
        String catalogId = food.getCatalog().getCatalogId();

        /**
         * 菜品id ( 头编号 [类别编号] + ‘00000’ [5位数，0补足]) 组成
         */
        String rearNo = foodService.generateId(catalogId);
        int i = Integer.parseInt(rearNo) + 1;
        String str = (catalogId).concat(String.format("%05d",i));
        food.setFoodId(str);

        Food f = foodService.save(food);
        return Result.success(f);
    }

    /**
     *更新菜品
     * @param food
     * @return
     */
    @RequestMapping("/updateFood")
    @ResponseBody
    private Result<Food> update(@RequestBody Food food){
        Food oldFood = foodService.getFoodByFoodId(food.getFoodId());
        if(oldFood == null){
            return Result.error(500);
        }else {
            Food f = foodService.update(food);
            return Result.success(f);
        }
    }

    /**
     *按菜品名称查询
     * @param foodName
     * @return
     */
    @RequestMapping("/getFoodByName")
    @ResponseBody
    private Result<Food> getFoodByName(@RequestParam("foodName") String foodName){
        Food f = foodService.getFoodByName(foodName);
        return Result.success(f);
    }

    /**
     *按菜品名称模糊查询
     * @param foodName
     * @return
     */
    @RequestMapping("/getFoodByCName")
    @ResponseBody
    private Result getFoodByCName(@RequestParam("foodName") String foodName){
        Iterable<Food> it = foodService.getFoodByCName(foodName);
        return Result.success(it);
    }

    /**
     * 删除菜品
     * @param foodId
     * @return
     */
    @RequestMapping("/deleteByFoodId")
    @ResponseBody
    private Result deleteByFoodId(@RequestParam("foodId") String foodId){
        foodService.delete(foodId);
        return Result.success(null);
    }
}
