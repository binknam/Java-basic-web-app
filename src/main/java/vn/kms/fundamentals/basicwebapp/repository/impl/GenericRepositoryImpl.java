//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.kms.fundamentals.basicwebapp.annotation.Column;
import vn.kms.fundamentals.basicwebapp.annotation.Id;
import vn.kms.fundamentals.basicwebapp.annotation.Table;
import vn.kms.fundamentals.basicwebapp.constants.Queries;
import vn.kms.fundamentals.basicwebapp.repository.GenericRepository;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;

// TODO: Need implementation using Java Annotation and Reflection to generate query
public abstract class GenericRepositoryImpl<E, I> implements GenericRepository<E, I> {
    private static final Logger LOG = LoggerFactory.getLogger(ProductRepositoryImpl.class.getCanonicalName());
    protected ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public E findById(I id) {
        // TODO: implement me
        E result;
        String tableName = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            tableName = getTableName(getEntityClass());
            result = getEntityClass().newInstance();

            resultSet = loadResultSetById(tableName, connection, statement, resultSet, Queries.SELECT_BY_ID_QUERY, id);
            if (resultSet.next()) {
                result = buildEntity(resultSet);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            throw new AppException("Cannot find entity in table: " + tableName, e);
        } finally {
            closeConnection(connection, statement, resultSet);
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
            LOG.debug("find entity successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot get all entities from table: " + tableName, e);
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return result;
    }


    @Override
    public void create(E entity) {
        // TODO: implement me
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            Field[] fields = getEntityClass().getDeclaredFields();

            statement = buildInsertStatement(fields, connection, entity);

            statement.executeUpdate();

            LOG.debug("Save entity successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot save Product", e);
        } finally {
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
    }

    @Override
    public void update(E entity) {
        // TODO: implement me
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            Field[] fields = getEntityClass().getDeclaredFields();

            statement = buildUpdateStatment(fields, connection, entity);

            statement.executeUpdate();

            LOG.debug("Save entity successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot save Product", e);
        } finally {
            ConnectionManager.close(statement);
            ConnectionManager.close(connection);
        }
    }

    @Override
    public boolean delete(I id) {
        // TODO: implement me
        String tableName = getTableName(getEntityClass());

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            deleteById(tableName, connection, statement, Queries.DELETE_BY_ID_QUERY, id);
            LOG.debug("Delete entity successfully!");
        } catch (Exception e) {
            throw new AppException("Cannot delete entity from table: " + tableName, e);
        } finally {
            ConnectionManager.close(connection);
            ConnectionManager.close(statement);
        }
        return false;
    }

    protected void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            statement = (PreparedStatement) resultSet.getStatement();
            connection = statement.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionManager.close(resultSet);
        ConnectionManager.close(statement);
        ConnectionManager.close(connection);
    }

    protected String getTableName(Class<?> eClz) {
        Table table = eClz.getAnnotation(Table.class);
        String tableName = table.name();
        if ("".equals(tableName)) {
            tableName = eClz.getSimpleName();
        }
        return tableName;
    }

    protected ResultSet loadResultSet(String tableName, Connection connection,
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
        String idColumn = "";
        Field[] fields = getEntityClass().getDeclaredFields();
        for (Field field: fields){
            Id idAnnotation = field.getDeclaredAnnotation(Id.class);
            Column column = field.getDeclaredAnnotation(Column.class);
            if (idAnnotation != null){
                idColumn = column.name();
                break;
            }
        }
        statement = connection.prepareStatement(String.format(queryStatement, tableName, idColumn));
        statement.setObject(1, id);
        resultSet = statement.executeQuery();
        return  resultSet;
    }

    private void deleteById(String tableName, Connection connection,
                                        PreparedStatement statement,
                                        String queryStatement, I id) throws SQLException, ClassNotFoundException {
        connection = connectionManager.getConnection();
        statement = connection.prepareStatement(String.format(queryStatement, tableName));
        statement.setObject(1, id);
        statement.executeUpdate();
    }

    public PreparedStatement buildInsertStatement(Field[] fields, Connection connection, E entity) throws IllegalAccessException, SQLException {
        PreparedStatement statement = buildParamInsertStatement(fields, connection);

        List<Object> entityFieldObjects = getEntityFieldObjects(fields, entity);

        for (int i = 0; i < entityFieldObjects.size() - 1; i++){
            statement.setObject(i + 1, entityFieldObjects.get(i));
        }

        return statement;
    }

    private PreparedStatement buildParamInsertStatement(Field[] fields, Connection connection) throws SQLException {
        String tableName = getTableName(getEntityClass());
        String columnNames = "";
        String columnValues = "";

        for (Field field: fields){
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation: annotations){
                if (annotation instanceof Id){
                    break;
                }
                if (annotation instanceof Column){
                    Column column = (Column) annotation;
                    columnNames += column.name() + ",";
                    columnValues += "?,";
                }
            }
        }

        columnNames = columnNames.substring(0, columnNames.length() -1);
        columnValues = columnValues.substring(0, columnValues.length() -1);
        return connection.prepareStatement(String.format(Queries.INSERT_QUERY, tableName, columnNames, columnValues));
    }

    public PreparedStatement buildUpdateStatment(Field[] fields, Connection connection, E entity) throws IllegalAccessException, SQLException {
        PreparedStatement statement = buildParamUpdateStatement(fields, connection);

        List<Object> entityFieldObjects = getEntityFieldObjects(fields, entity);

        for (int i = 0; i < entityFieldObjects.size(); i++){
            statement.setObject(i + 1, entityFieldObjects.get(i));
        }

        return statement;
    }

    private List<Object> getEntityFieldObjects(Field[] fields, E entity) throws IllegalAccessException {
        List<Object> entityFieldObjects = new ArrayList<>();
        Object entityId = null;
        for (Field field: fields){
            field.setAccessible(true);
            Id id = field.getDeclaredAnnotation(Id.class);
            Column column = field.getDeclaredAnnotation(Column.class);
            if (id != null){
                entityId = field.get(entity);
                continue;
            }
            Object entityObject = field.get(entity);
            if (!"".equals(column.foreignKey())){
                entityObject = getForeignKeyObject(entity, field);
            }
            entityFieldObjects.add(entityObject);
        }
        entityFieldObjects.add(entityId);
        return entityFieldObjects;
    }

    private PreparedStatement buildParamUpdateStatement(Field[] fields, Connection connection) throws SQLException {
        String tableName = getTableName(getEntityClass());
        String updateQueryParam = "";
        List<String> columnNames = new ArrayList<>();

        int idIndex = 0;
        for (int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true);
            Annotation[] annotations = fields[i].getAnnotations();
            for (Annotation annotation: annotations){
                if (annotation instanceof Column){
                    Column column = (Column) annotation;
                    columnNames.add(column.name());
                }
                if (annotation instanceof Id)
                    idIndex = i;
            }
        }

        for (int i = 0; i < columnNames.size(); i++){
            if (i != idIndex)
                updateQueryParam += columnNames.get(i) + "=?,";
        }
        updateQueryParam = updateQueryParam.substring(0, updateQueryParam.length()-1);
        updateQueryParam += " WHERE " + columnNames.get(idIndex) + "=?";

        return connection.prepareStatement(String.format(Queries.UPDATE_QUERY, tableName, updateQueryParam));
    }

    private Object getForeignKeyObject(E entity, Field field) throws IllegalAccessException {
        Object foreingKeyObject = field.get(entity);
        Field[] foreignKeyFields = field.getType().getDeclaredFields();
        for (Field foreignField: foreignKeyFields){
            Annotation[] annotations = foreignField.getDeclaredAnnotations();
            for (Annotation annotation: annotations)
                if (annotation instanceof Id)
                    return foreignField.get(foreingKeyObject);
        }
        return null;
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
            if (columnObject.equals(mainTableObject)) {
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
