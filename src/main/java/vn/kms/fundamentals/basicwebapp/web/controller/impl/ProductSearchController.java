package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductSearchController implements Controller {

    private final ProductRepository productRep;

    private final CategoryRepository categoryRepository;

    public ProductSearchController(ProductRepository productRep, CategoryRepository categoryRepository) {
        this.productRep = productRep;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isSecured(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null ;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productRep.findAll();
        if (request.getMethod().equalsIgnoreCase("POST")) {

            String productName = request.getParameter("NAME");
            String minPrice = request.getParameter("MIN PRICE");
            String maxPrice = request.getParameter("MAX PRICE");
            String categoryId = request.getParameter("CATEGORYID");

            products = productRep.search(productName, categoryId, minPrice, maxPrice);
        }
        return new ViewModel("product/list")
            .addModelAttribute("products", products)
            .addModelAttribute("categories", categoryRepository.findAll());
    }
}
