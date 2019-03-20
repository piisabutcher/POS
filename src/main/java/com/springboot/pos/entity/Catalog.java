package com.springboot.pos.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Column(name = "catalog_name", nullable = false, length = 255)
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
}
