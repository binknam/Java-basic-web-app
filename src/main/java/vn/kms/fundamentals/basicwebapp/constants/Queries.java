package vn.kms.fundamentals.basicwebapp.constants;

public class Queries {
    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";
    public static final String INSERT_QUERY = "INSERT INTO %s(%s) VALUES(%s)";
    public static final String UPDATE_QUERY = "UPDATE %s SET %s";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM %s WHERE %s = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM %s WHERE ID = ?";

    public static final String _BY_NAME = "NAME = ?";
    public static final String _BY_PRICE_RANGE = "PRICE >= ? AND PRICE <=?";
    public static final String _BY_CATEGORY = "CATEGORYID = ?";
}
