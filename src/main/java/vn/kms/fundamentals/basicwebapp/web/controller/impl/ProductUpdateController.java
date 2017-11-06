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

public class ProductUpdateController implements Controller {

    private final ProductRepository productRep;

    private final CategoryRepository categoryRepository;

    public ProductUpdateController(ProductRepository productRep, CategoryRepository categoryRepository) {
        this.productRep = productRep;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isSecured() {
        return UserLoginController.secured;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productRep.findById(getProductId(request));

        if (request.getMethod().equalsIgnoreCase("POST") && isSecured()) {
            product = getProduct(request);
            productRep.update(product);
            response.sendRedirect("/products");
            return new ViewModel("product/list")
                .addModelAttribute("products", productRep.findAll())
                .addModelAttribute("categories", categoryRepository.findAll())
                .addModelAttribute("secured", UserLoginController.secured);
        }
        return new ViewModel("product/detail")
            .addModelAttribute("product", product)
            .addModelAttribute("categories", categoryRepository.findAll())
            .addModelAttribute("secured", UserLoginController.secured);
    }

    private Product getProduct(HttpServletRequest request) {
        Map<String, String> pathParams = (Map<String, String>) request.getAttribute(PATH_PARAMS_KEY);
        Product product = new Product();

        product.setId(Integer.valueOf(pathParams.get("productId")));

        product.setName(request.getParameter(ProductProperties.NAME));

        Category category = categoryRepository.findById(Integer.valueOf(request.getParameter(ProductProperties.CATEGORYID)));
        product.setCategory(category);

        BigDecimal price = BigDecimal.valueOf(Double.valueOf(request.getParameter(ProductProperties.PRICE)));
        product.setPrice(price);

        product.setDescription(request.getParameter(ProductProperties.DESCRIPTION));

        return product;
    }

    private Long getProductId(HttpServletRequest request) {
        Map<String, String> pathParams = (Map<String, String>) request.getAttribute(PATH_PARAMS_KEY);

        String productId = pathParams.get("productId");

        return Long.valueOf(productId);
    }
}
