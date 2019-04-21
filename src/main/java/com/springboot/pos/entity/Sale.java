package com.springboot.pos.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Sale implements Serializable {
    private static final long serialVersionUID = 6788933059497808914L;
    private String saleId;
    private Date sale_time = new Date();
    private int dinners_num;
    private double total_amt = 0;
    private DiningTable diningTable;
    private int is_complete = 0;//判断该订单是否完成，完成之后为1

    @OneToMany( mappedBy="sale")
    private List<SaleLineItem> saleLineItems = new ArrayList<SaleLineItem>();


    @Id
    @Column(name = "sale_id",nullable = false,length = 20)
    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    @Basic
    @Column(name = "sale_time", nullable = false)
    public Date getSale_time() {
        return sale_time;
    }

    public void setSale_time(Date sale_time) {
        this.sale_time = sale_time;
    }

    @Basic
    @Column(name = "dinner_num",nullable = false)
    public int getDinners_num() {
        return dinners_num;
    }

    public void setDinners_num(int dinners_num) {
        this.dinners_num = dinners_num;
    }

    @Basic
    @Column(name = "total_num")
    public double getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(double total_amt) {
        this.total_amt = total_amt;
    }

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "dt_Id",nullable = false)
    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    @Basic
    @Column(name = "is_complete",nullable = false)
    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }


}
