//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ProductListController implements Controller {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepository;

    public ProductListController(ProductRepository productRepo, CategoryRepository categoryRepository) {
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isSecured(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null ;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) {
        return new ViewModel("product/list")
                .addModelAttribute("products", productRepo.findAll())
                .addModelAttribute("categories", categoryRepository.findAll());
    }
}
