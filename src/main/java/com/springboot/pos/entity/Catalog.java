package com.springboot.pos.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Catalog {
    private String catalogId;
    private String catalogName;

    @Id
    @Column(name = "catalog_id", nullable = false, length = 20)
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "catalog_name", nullable = false, length = 254)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(catalogId, catalog.catalogId) &&
                Objects.equals(catalogName, catalog.catalogName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId, catalogName);
    }

    //一对多
    @OneToMany(mappedBy = "catalog",cascade = CascadeType.ALL)
    private List<Food> foodList;

}
