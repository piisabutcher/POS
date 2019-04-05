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
        Food fd = this.getFoodByFoodId(food.getFoodId());
        if(fd == null) return null;
        else {
            if(food.getFoodName()!=null) fd.setFoodName(food.getFoodName());
            if(food.getCatalog()!=null) fd.setCatalog(food.getCatalog());
            if(food.getSupPeriod()!=null) fd.setSupPeriod(food.getSupPeriod());
            if(food.getPrice()!=null) fd.setPrice(food.getPrice());
            if(food.getImage()!=null) fd.setImage(food.getImage());
            return fd;
        }
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

    //截取id
    @Transactional
    public String generateId(String catalogId){
        return foodReposity.generateId(catalogId);
    }
}
