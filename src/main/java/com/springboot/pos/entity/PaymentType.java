package com.springboot.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PaymentType implements Serializable {
    private static final long serialVersionUID = 6788933059497808914L;
    private String ptId;//1代表现金，2代表支付宝/微信，3代表信用卡
    private String ptName;

    @Id
    @Column(name = "pt_id", nullable = false, length = 20)
    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    @Basic
    @Column(name = "pt_name",nullable = false)
    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }
}
