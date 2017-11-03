package vn.kms.fundamentals.basicwebapp.constants;

public class Queries {
    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";
    public static final String INSERT_QUERY = "INSERT INTO %s VALUES(?, ?, ?, ?)";
    public static final String UPDATE_QUERY = "UPDATE %s";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM %s WHERE ID = ?";
}
