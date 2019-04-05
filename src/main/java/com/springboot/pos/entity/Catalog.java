package com.springboot.pos.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Catalog {
    private String catalogId;
    private String catalogName;
    private String catalogImg;//新增菜品类别图片字段

    @Id
    @Column(name = "catalog_id", nullable = false, length = 7)
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "catalog_name", nullable = false, length = 255)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Basic
    @Column(name = "catalog_img", nullable = false, length = 255)
    public String getCatalogImg(){
        return catalogImg;
    }

    public void setCatalogImg(String catalogImg){
        this.catalogImg = catalogImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(catalogId, catalog.catalogId) &&
                Objects.equals(catalogName, catalog.catalogName)&&
                Objects.equals(catalogImg,catalog.catalogImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId, catalogName);
    }

    //一对多
    @OneToMany(mappedBy = "catalog",cascade = CascadeType.ALL)
    private List<Food> foodList;

}
