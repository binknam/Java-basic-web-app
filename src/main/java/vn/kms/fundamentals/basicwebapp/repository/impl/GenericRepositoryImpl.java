//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.annotation.Table;
import vn.kms.fundamentals.basicwebapp.constants.Queries;
import vn.kms.fundamentals.basicwebapp.repository.GenericRepository;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;

// TODO: Need implementation using Java Annotation and Reflection to generate query
public abstract class GenericRepositoryImpl<E, I> implements GenericRepository<E, I> {
    protected ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public E findById(I id) {
        // TODO: implement me
        E result = null;
        try {
            String tableName = getTableName(getEntityClass());
            result = getEntityClass().newInstance();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            resultSet = loadResultSetById(tableName, connection, statement, resultSet, Queries.SELECT_BY_ID_QUERY, id);
            if (resultSet.next()) {
                result = buildEntity(resultSet);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            return null;
        }
        return result;
    }


    @Override
    public List<E> findAll() {
        String tableName = getTableName(getEntityClass());

        List<E> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            resultSet = loadResultSet(tableName, connection, statement, resultSet, Queries.SELECT_ALL_QUERY);
            while (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }
        } catch (Exception e) {
            throw new AppException("Cannot get all entities from table: " + tableName, e);
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return result;
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        ConnectionManager.close(resultSet);
        ConnectionManager.close(statement);
        ConnectionManager.close(connection);
    }

    private String getTableName(Class<?> eClz) {
        Table table = eClz.getAnnotation(Table.class);
        String tableName = table.name();
        if ("".equals(tableName)) {
            tableName = eClz.getSimpleName();
        }
        return tableName;
    }

    private ResultSet loadResultSet(String tableName, Connection connection,
                                    PreparedStatement statement, ResultSet resultSet,
                                    String queryStatement) throws SQLException, ClassNotFoundException {
        connection = connectionManager.getConnection();
        statement = connection.prepareStatement(String.format(queryStatement, tableName));
        resultSet = statement.executeQuery();
        return  resultSet;
    }

    private ResultSet loadResultSetById(String tableName, Connection connection,
                                    PreparedStatement statement, ResultSet resultSet,
                                    String queryStatement, I id) throws SQLException, ClassNotFoundException {
        connection = connectionManager.getConnection();
        statement = connection.prepareStatement(String.format(queryStatement, tableName));
        statement.setObject(1, id);
        resultSet = statement.executeQuery();
        return  resultSet;
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

    protected E buildEntity(ResultSet rs) throws ClassNotFoundException {
        try {
            E entity = getEntityClass().newInstance();

            //TODO: populate entity fields via Annotations (@Table, @Column) and Reflections
            loadEntityFromResultSet(rs, entity, getEntityClass().getDeclaredFields(), null);
            return entity;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            return null;
        }
    }

    private boolean loadEntityFromResultSet(ResultSet resultSet, Object entity, Field[] fields, Object mainTableObject) throws IllegalAccessException, SQLException, ClassNotFoundException {
        boolean isForeinObjectEqualTableObject = false;
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getDeclaredAnnotation(Column.class);
            Object columnObject = null;
            columnObject = resultSet.getObject(column.name());
            if (columnObject.equals(mainTableObject)){
                isForeinObjectEqualTableObject = true;
            }
            if (!"".equals(column.foreignKey()))
                columnObject = loadForeignObject(field, columnObject);
            field.set(entity, columnObject);
        }
        return isForeinObjectEqualTableObject;
    }

    private Object loadForeignObject(Field field, Object mainTableObject) throws SQLException, ClassNotFoundException {
        Field[] foreignFields = field.getType().getDeclaredFields();
        String tableName = getTableName(field.getType());

        Object foreignObject = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        resultSet = loadResultSet(tableName, connection, statement, resultSet, Queries.SELECT_ALL_QUERY);

        try {
            foreignObject = field.getType().newInstance();
            while (resultSet.next() && !loadEntityFromResultSet(resultSet, foreignObject, foreignFields, mainTableObject)) { }
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        } finally {
            closeConnection(connection, statement, resultSet);
        }

        return foreignObject;
    }
}
