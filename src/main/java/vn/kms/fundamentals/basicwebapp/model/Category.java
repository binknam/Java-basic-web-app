//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.model;

import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.annotation.Id;
import vn.kms.fundamentals.basicwebapp.annotation.Table;

//TODO: Move this into DB and use it as an entity
@Table(name="CATEGORIES")
public class Category {

    @Id
    @Column(name = "ID")
    public Integer id;
    @Column(name = "NAME")
    public String name;

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
}
