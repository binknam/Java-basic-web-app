//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.kms.fundamentals.basicwebapp.constants.Queries;
import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl extends GenericRepositoryImpl<Product, Long> implements ProductRepository {

    @Override
    public void create(Product product) {
        // TODO: The Insert query should be generated by using Annotations (@Table, @Column) and Reflections
        super.create(product);
    }

    @Override
    public void update(Product product) {
        // TODO: The Update query should be generated by using Annotations (@Table, @Column) and Reflections
        super.update(product);
    }

    @Override
    public Product findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Product> search(String productName, String categoryId,String minPrice, String maxPrice) {
        // TODO: need implementation and change the method parameters
        List<Product> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            resultSet = buildSearchResultSet(connection, statement, resultSet, productName, categoryId, minPrice, maxPrice);
            if (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }
        } catch ( SQLException | ClassNotFoundException e) {
            throw new AppException("Cannot find entity in table: PRODUCTS", e);
        } finally {
            closeConnection(connection, statement, resultSet);
        }

        return result;
    }

    // TODO: Remove all hard-coded column name using annotation and Java
    // reflect and move this method to GenericRepositoryImpl
    @Override
    protected Product buildEntity(ResultSet resultSet) throws ClassNotFoundException {
        return super.buildEntity(resultSet);
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    public String buildSearchQueryStatement(String productName, String categoryId,String minPrice, String maxPrice){
        String queryStatement = "";

        queryStatement += "SELECT * FROM PRODUCTS";
        if (productName.equals("") && categoryId.equals("0") && minPrice.equals("") && maxPrice.equals(""))
            return queryStatement;
        queryStatement += " WHERE ";

        if (!productName.equals(""))
            queryStatement += "NAME LIKE %" + productName +"% AND ";
        if (!categoryId.equals("0"))
            queryStatement += "CATEGORYID = " + categoryId + " AND ";
        if (!minPrice.equals(""))
            queryStatement += "PRICE >= " + minPrice + " AND ";
        if (!maxPrice.equals(""))
            queryStatement += " PRICE <=" +maxPrice+ " AND ";

        if (!queryStatement.equals("SELECT * FROM PRODUCT"))
            queryStatement = queryStatement.substring(0, queryStatement.length() - 5);

        return queryStatement;
    }


    private ResultSet buildSearchResultSet(Connection connection, PreparedStatement statement, ResultSet resultSet,
                                            String productName, String categoryId,String minPrice, String maxPrice) throws SQLException, ClassNotFoundException {

        String queryStatement = buildSearchQueryStatement(productName, categoryId, minPrice, maxPrice);
        connection = connectionManager.getConnection();
        statement = connection.prepareStatement(queryStatement);
        resultSet = statement.executeQuery();
        return resultSet;
    }

}
