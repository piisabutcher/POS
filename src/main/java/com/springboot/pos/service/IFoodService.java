package com.springboot.pos.service;

import com.springboot.pos.entity.Food;

public interface IFoodService {
    public Food save(Food food);

    public Iterable<Food> getAllFood();

    public Food update(Food food);

    public Food getFoodByFoodId(String foodId);

    public Food getFoodByName(String food_name);

    public Iterable<Food> getFoodByCName(String food_name);

    public void delete(String foodid);

    public String generateId(String catalogId);
}
