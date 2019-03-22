package com.springboot.pos.controller;

import com.springboot.pos.entity.Catalog;
import com.springboot.pos.entity.Food;
import com.springboot.pos.service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/food")
public class FoodController {
    @Resource
    private FoodService foodService;

    @RequestMapping("/getAllFood")
    @ResponseBody
    private Iterable<Food> getAllFood(){
        Iterable<Food> it = foodService.getAllFood();
        System.out.println(it.iterator().next().getFoodName());
        return it;
    }

    @RequestMapping("/getFoodById")
    @ResponseBody
    private Food getFoodById(String foodId){
        Food f = foodService.getFoodByFoodId(foodId);
        return f;
    }
    @RequestMapping("/save")
    @ResponseBody
    private Food save(String food_id,String food_name,String image,double price,String sup_period,
                      String catalog_id){
        Food food = new Food();
        food.setFoodId(food_id);
        food.setFoodName(food_name);
        food.setImage(image);
        food.setPrice(price);
        food.setSupPeriod(sup_period);
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalog_id);
        catalog.setCatalogName("2");
        food.setCatalog(catalog);
        Food fo = foodService.save(food);
        return fo;
    }

    @RequestMapping("/update")
    @ResponseBody
    private Food update(String food_id,String food_name,String image,double price,String sup_period,
                      String catalog_id){
        Food food = new Food();
        food.setFoodId(food_id);
        food.setFoodName(food_name);
        food.setImage(image);
        food.setPrice(price);
        food.setSupPeriod(sup_period);
        Catalog catalog = new Catalog();
        catalog.setCatalogId(catalog_id);
        catalog.setCatalogName("2");
        food.setCatalog(catalog);
        Food fo = foodService.update(food);
        return fo;
    }

    @RequestMapping("/getFoodByName")
    @ResponseBody
    private Food getFoodByName(String food_name){
        Food food = foodService.getFoodByName(food_name);
        return food;
    }

    @RequestMapping("/getFoodByCName")
    @ResponseBody
    private Iterable<Food> getFoodByCName(String food_name){
        Iterable<Food> food = foodService.getFoodByCName(food_name);
        return food;
    }

    @RequestMapping("/delete")
    @ResponseBody
    private void delete(String food_id){
        foodService.delete(food_id);
    }
}
