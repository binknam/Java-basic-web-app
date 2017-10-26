//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.kms.fundamentals.basicwebapp.annotation.Table;
import vn.kms.fundamentals.basicwebapp.repository.GenericRepository;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;

// TODO: Need implementation using Java Annotation and Reflection to generate query
public abstract class GenericRepositoryImpl<E, I> implements GenericRepository<E, I> {
    protected ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public E findById(I id) {
        // TODO: implement me
        return null;
    }

    @Override
    public List<E> findAll() {
        Class<E> eClz = getEntityClass();
        Table table = eClz.getAnnotation(Table.class);
        String tableName = table.name();
        if ("".equals(tableName)) {
            tableName = eClz.getSimpleName();
        }

        List<E> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(String.format("SELECT * FROM %s", tableName));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }
        } catch (Exception e) {
            throw new AppException("Cannot get all entities from table: " + tableName, e);
        } finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
        return result;
    }

    @Override
    public void create(E entity) {
        // TODO: implement me
    }

    @Override
    public void update(E entity) {
        // TODO: implement me
    }

    @Override
    public boolean delete(I id) {
        // TODO: implement me
        return false;
    }

    protected abstract Class<E> getEntityClass();

    protected E buildEntity(ResultSet rs) {
        try {
            E entity = getEntityClass().newInstance();
            //TODO: populate entity fields via Annotations (@Table, @Column) and Reflections
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
