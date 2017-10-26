//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository;

import java.util.List;

import vn.kms.fundamentals.basicwebapp.model.Product;

public interface ProductRepository extends GenericRepository<Product, Long> {
    List<Product> search();
}
