//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.model;

import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.annotation.Id;
import vn.kms.fundamentals.basicwebapp.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "PRODUCTS")
public class Product implements Serializable {

    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="PRICE")
    private BigDecimal price;

    @Column(name = "CATEGORYID", foreignKey = "ID")
    private Category category;

    @Column(name="DESCRIPTION")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
