package com.springboot.pos.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DiningTable {
    private String dt_Id;
    private DiningTableType diningTableType;
    private int is_people = 0;//0表示没有，1表示有

    @Id
    @Column(name = "dt_Id", nullable = false, length = 20)
    public String getDt_Id() {
        return dt_Id;
    }

    public void setDt_Id(String dt_Id) {
        this.dt_Id = dt_Id;
    }

    //一个餐台类型对应多个餐台
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "dtt_Id")
    public DiningTableType getDiningTableType() {
        return diningTableType;
    }

    public void setDiningTableType(DiningTableType diningTableType) {
        this.diningTableType = diningTableType;
    }


    @Basic
    @Column(name = "is_people",nullable = false)
    public int getIs_people() {
        return is_people;
    }

    public void setIs_people(int is_people) {
        this.is_people = is_people;
    }
}