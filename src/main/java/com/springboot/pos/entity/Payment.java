package com.springboot.pos.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Payment implements Serializable {
    private static final long serialVersionUID = 1207408560047174539L;
    private String payment_id;
    private Sale sale;
    private PaymentType paymentType;
    private double pay_num;
    private double pay_change;//零钱

    @Id
    @Column(name = "payment_id",nullable = false)
    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
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
    @JoinColumn(name="pt_id")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Basic
    @Column(name = "pay_num")
    public double getPay_num() {
        return pay_num;
    }

    public void setPay_num(double pay_num) {
        this.pay_num = pay_num;
    }

    @Basic
    @Column(name = "pay_change")
    public double getPay_change() {
        return pay_change;
    }

    public void setPay_change(double pay_change) {
        this.pay_change = pay_change;
    }
}
