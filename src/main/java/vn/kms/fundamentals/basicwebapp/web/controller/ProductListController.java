//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.web.controller;

import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ProductListController implements Controller {
    private final ProductRepository productRepo;

    public ProductListController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) {
        return new ViewModel("product/list")
            .addModelAttribute("products", productRepo.findAll());
    }
}
