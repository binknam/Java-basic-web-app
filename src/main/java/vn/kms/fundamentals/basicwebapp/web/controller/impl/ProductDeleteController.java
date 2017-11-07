package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.constants.ProductProperties;
import vn.kms.fundamentals.basicwebapp.model.Category;
import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static vn.kms.fundamentals.basicwebapp.utils.ControllerManager.PATH_PARAMS_KEY;

public class ProductDeleteController implements Controller {

    private final ProductRepository productRep;

    private final CategoryRepository categoryRepository;

    public ProductDeleteController(ProductRepository productRep, CategoryRepository categoryRepository) {
        this.productRep = productRep;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isSecured(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null ;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getMethod().equalsIgnoreCase("POST") && isSecured(request))
            productRep.delete(getProductId(request));
        response.sendRedirect("/products");
        return new ViewModel("product/list")
            .addModelAttribute("products", productRep.findAll())
            .addModelAttribute("categories", categoryRepository.findAll())
            .addModelAttribute("secured", UserLoginController.secured);
    }

    private Long getProductId(HttpServletRequest request) {
        Map<String, String> pathParams = (Map<String, String>) request.getAttribute(PATH_PARAMS_KEY);

        String productId = request.getParameter(ProductProperties.PRODUCTID);

        return Long.valueOf(productId);
    }
}
