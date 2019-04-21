package com.springboot.pos.reposity;

import com.springboot.pos.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodReposity extends JpaRepository<Food, String> {

}
