package com.springboot.pos.entity;


import javax.persistence.*;

@Entity
public class Food {
    private String foodId;//菜品编号
    private String foodName;//菜名
    private Double price;//价格
    private String supPeriod;//供应时段
    private String image;
    private Catalog catalog;//所属菜品

    @Id
    @Column(name = "food_id", nullable = false, length = 12)
    public String getFoodId(){ return this.foodId; }

    public void setFoodId(String foodId){ this.foodId = foodId; }

    @Basic
    @Column(name = "food_name", nullable = false, length = 254)
    public String getFoodName(){
        return this.foodName;
    }

    public void setFoodName(String foodName){
        this.foodName = foodName;
    }

    @Basic
    @Column(name = "price", nullable = false,scale = 2)
    public Double getPrice(){ return this.price; }

    public void setPrice(Double price){
        this.price = price;
    }

    @Basic
    @Column(name = "sup_period", nullable = false, length = 20)
    public String getSupPeriod(){
        return this.supPeriod;
    }

    public void setSupPeriod(String supPeriod){
        this.supPeriod = supPeriod;
    }

    @Basic
    @Column(name = "image", nullable = false, length = 254)
    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    //菜品：菜品类别=n：1
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "catalog_id")
    public Catalog getCatalog(){
        return this.catalog;
    }

    public void setCatalog(Catalog catalog){
        this.catalog = catalog;
    }



}
