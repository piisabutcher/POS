package com.springboot.pos.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class DiningTableType {
    private String dtt_Id;
    private int dtt_num;

    @Id
    @Column(name = "dtt_Id", nullable = false, length = 20)
    public String getDtt_Id() {
        return dtt_Id;
    }

    public void setDtt_Id(String dtt_Id) {
        this.dtt_Id = dtt_Id;
    }

    @Basic
    @Column(name = "dtt_num", nullable = false)
    public int getDtt_num() {
        return dtt_num;
    }

    public void setDtt_num(int dtt_num) {
        this.dtt_num = dtt_num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtt_Id, dtt_num);
    }

    //一个餐台类型对应多个餐台
    @OneToMany(mappedBy = "diningTableType",cascade = CascadeType.ALL)
    private List<DiningTable> DiningTableList;
}
