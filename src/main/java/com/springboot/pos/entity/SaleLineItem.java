package com.springboot.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SaleLineItem implements Serializable {
    private static final long serialVersionUID = 1207408560047174539L;
    private String sli_id;
    private Sale sale;
    private Food food;
    private int sli_num;//数量
    private int sli_spicy = 0;//是否加辣，0表示不是，1表示是4
    private int is_export = 0;//表示是否出菜，0表示没有，1表示有

    @Id
    @Column(name = "sli_id",nullable = false)
    public String getSli_id() {
        return sli_id;
    }

    public void setSli_id(String sli_id) {
        this.sli_id = sli_id;
    }

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name="sale_id")
    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name="food_id")
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Basic
    @Column(name = "sli_num",nullable = false)
    public int getSli_num() {
        return sli_num;
    }

    public void setSli_num(int sli_num) {
        this.sli_num = sli_num;
    }

    @Basic
    @Column(name = "sli_spicy",nullable = false)
    public int getSli_spicy() {
        return sli_spicy;
    }

    public void setSli_spicy(int sli_spicy) {
        this.sli_spicy = sli_spicy;
    }


    @Basic
    @Column(name = "is_export",nullable = false)
    public int getIs_export() {
        return is_export;
    }

    public void setIs_export(int is_export) {
        this.is_export = is_export;
    }

    @Override
    public String toString() {
        return "SaleLineItem{" +
                "sale=" + sale +
                ", food=" + food +
                ", sli_num=" + sli_num +
                ", sli_spicy=" + sli_spicy +
                ", is_export=" + is_export +
                '}';
    }
}
