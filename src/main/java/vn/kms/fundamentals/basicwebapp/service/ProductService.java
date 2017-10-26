package vn.kms.fundamentals.basicwebapp.service;

import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;

public class ProductService {
  private final ProductRepository productRep;

  public ProductService(ProductRepository productRep) {
    this.productRep = productRep;
  }
}
