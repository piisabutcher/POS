package com.springboot.pos.controller;

import com.springboot.pos.entity.Catalog;
import com.springboot.pos.entity.Food;
import com.springboot.pos.service.CatalogService;
import com.springboot.pos.service.FoodService;
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

    @RequestMapping("/getAllFood")
    @ResponseBody
    private Result<Food> getAllFood(){
        Iterable<Food> it = foodService.getAllFood();
        return Result.success(it);
    }

    @RequestMapping("/getFoodById")
    @ResponseBody
    private Result<Food> getFoodById(@RequestParam("foodId")String foodId){
        Food f = foodService.getFoodByFoodId(foodId);
        return Result.success(f);
    }
    @RequestMapping("/saveFood")
    @ResponseBody
    private Result<Food> save(@RequestBody Food food){
        System.out.println(JSONObject.fromObject(food));
        food.setCatalog(catalogService.getCatalogById(food.getCatalog().getCatalogId()));
        Food f = foodService.save(food);
        return Result.success(f);
    }

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

    @RequestMapping("/getFoodByName")
    @ResponseBody
    private Result<Food> getFoodByName(@RequestParam("foodName") String foodName){
        Food f = foodService.getFoodByName(foodName);
        return Result.success(f);
    }


    @RequestMapping("/getFoodByCName")
    @ResponseBody
    private Result getFoodByCName(@RequestParam("foodName") String foodName){
        Iterable<Food> it = foodService.getFoodByCName(foodName);
        return Result.success(it);
    }

    @RequestMapping("/deleteByFoodId")
    @ResponseBody
    private Result deleteByFoodId(@RequestParam("foodId") String foodId){
        foodService.delete(foodId);
        return Result.success(null);
    }
}
