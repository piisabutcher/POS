package com.springboot.pos.reposity;

import com.springboot.pos.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodReposity extends JpaRepository<Food, String> {
    //按照菜品名称查询
    @Query(value = "select * from food where food_name = ?1", nativeQuery = true)
    Food findByName(String food_name);

    //按照菜品名称模糊查询
    @Query(value = "select * from food where food_name like %?1%", nativeQuery = true)
    Iterable<Food> findByCName(String food_name);
}
