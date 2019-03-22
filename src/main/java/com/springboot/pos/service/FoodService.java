package com.springboot.pos.service;


import com.springboot.pos.entity.Food;
import com.springboot.pos.reposity.FoodReposity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service("foodService")
public class FoodService {
    @Resource
    private FoodReposity foodReposity;

    //增加菜品
    @Transactional
    public Food save(Food food){
        return foodReposity.save(food);
    }

    //列出所有菜品
    @Transactional
    public Iterable<Food> getAllFood(){return foodReposity.findAll();}

    //更新菜品信息
    @Transactional
    public Food update(Food food){
        Optional<Food> fd = foodReposity.findById(food.getFoodId());
        if(fd.isPresent()) {
            foodReposity.deleteById(food.getFoodId());
            return foodReposity.save(food);
        }
        return food;
    }

    //通过id查询菜品信息
    @Transactional
    public Food getFoodByFoodId(String foodId){
        Optional<Food> food = foodReposity.findById(foodId);
        //异常处理
        return food.isPresent()?food.get():null;
    }

    //通过菜名查询菜品信息
    @Transactional
    public Food getFoodByName(String food_name){
        Food food = foodReposity.findByName(food_name);
        return (food != null)?food:null;
    }

    //通过菜名模糊查询菜品信息
    public Iterable<Food> getFoodByCName(String food_name){
        Iterable<Food> food = foodReposity.findByCName(food_name);
        return (food != null)?food:null;
    }
    //通过菜品id删除菜品
    @Transactional
    public void delete(String foodid){
        foodReposity.deleteById(foodid);
    }
}
