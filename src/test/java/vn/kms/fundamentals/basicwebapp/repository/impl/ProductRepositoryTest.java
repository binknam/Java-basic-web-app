//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import org.junit.Test;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.model.Category;
import vn.kms.fundamentals.basicwebapp.model.Product;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ProductRepositoryTest extends BaseDaoTest {
    private ProductRepository productRepository = new ProductRepositoryImpl();

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Phone ABC");
        //product.setCategory(Category.SMART_PHONE);
        productRepository.create(product);

        assertThat("There should 3 products in the system", productRepository.findAll().size(), equalTo(3));
    }
}
