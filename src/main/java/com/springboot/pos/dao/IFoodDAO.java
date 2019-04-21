package com.springboot.pos.dao;

import com.springboot.pos.entity.Food;
import com.springboot.pos.reposity.FoodReposity;
import org.springframework.data.jpa.repository.Query;

public interface IFoodDAO extends FoodReposity {
    //按照菜品名称查询
    @Query(value = "select * from food where food_name = ?1", nativeQuery = true)
    Food findByName(String food_name);

    //按照菜品名称模糊查询
    @Query(value = "select * from food where food_name like %?1%", nativeQuery = true)
    Iterable<Food> findByCName(String food_name);

    //截取id
    @Query(value = "SELECT max(substr(food_id,8,5)) from food where catalog_id = ?1",nativeQuery = true)
    String generateId(String catalogId);
}
