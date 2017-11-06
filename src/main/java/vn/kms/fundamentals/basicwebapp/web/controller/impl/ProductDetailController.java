//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static vn.kms.fundamentals.basicwebapp.utils.ControllerManager.PATH_PARAMS_KEY;

public class ProductDetailController implements Controller {
    private final ProductRepository productRep;

    private final CategoryRepository categoryRepository;

    public ProductDetailController(ProductRepository productRep, CategoryRepository categoryRepository) {
        this.productRep = productRep;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) {
        long productId = getProductId(request);

        Product product = productRep.findById(productId);
        if (product == null) {
            return new ViewModel("error/404");
        }

        return new ViewModel("product/detail")
            .addModelAttribute("product", product)
            .addModelAttribute("categories", categoryRepository.findAll())
            .addModelAttribute("secured", UserLoginController.secured);
    }

    private Long getProductId(HttpServletRequest request) {
        Map<String, String> pathParams = (Map<String, String>) request.getAttribute(PATH_PARAMS_KEY);

        String productId = pathParams.get("productId");

        return Long.valueOf(productId);
    }
}
