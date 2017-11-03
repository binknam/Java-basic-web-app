//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl extends GenericRepositoryImpl<Product, Long> implements ProductRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRepositoryImpl.class.getCanonicalName());

    private static final String INSERT_QUERY = "INSERT INTO PRODUCTS(NAME, CATEGORY, DESCRIPTION, PRICE) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE PRODUCTS SET NAME = ?, CATEGORY = ?, DESCRIPTION = ?, PRICE = ? WHERE ID = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM PRODUCTS WHERE ID = ?";

    @Override
    public void create(Product product) {
        // TODO: The Insert query should be generated by using Annotations (@Table, @Column) and Reflections
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory().getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.executeUpdate();
            LOG.debug("Save product successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot save Product", e);
        } finally {
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
    }

    @Override
    public void update(Product product) {
        // TODO: The Update query should be generated by using Annotations (@Table, @Column) and Reflections
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory().getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setLong(5, product.getId());
            statement.executeUpdate();
            LOG.debug("Save product successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot save Product", e);
        } finally {
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
    }

    @Override
    public Product findById(Long id) {
        Product result = super.findById(id);
        return result;
    }

    @Override
    public List<Product> search() {
        // TODO: need implementation and change the method parameters
        return new ArrayList<>();
    }

    // TODO: Remove all hard-coded column name using annotation and Java
    // reflect and move this method to GenericRepositoryImpl
    @Override
    protected Product buildEntity(ResultSet resultSet) throws ClassNotFoundException {
        Product product = super.buildEntity(resultSet);
        return product;
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}
