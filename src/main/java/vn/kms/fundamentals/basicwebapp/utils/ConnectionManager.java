//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO: This class should use Connection Pool instead. Ex: http://brettwooldridge.github.io/HikariCP/
public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class.getCanonicalName());
    private static final ConnectionManager instance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return instance;
    }

    private String url;
    private String username;
    private String password;
    private String driverClass;

    private ConnectionManager() {
    }

    /**
     * This method should only be called ONCE when starting up the application
     */
    public void initialize(String url, String username, String password, String driverClass) {
        logger.debug("Init attribute for Connection Manager");
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties userInfo = new Properties();
        userInfo.put("user", username);
        userInfo.put("password", password);

        return DriverManager.getConnection(url, userInfo);
    }

    public static void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignore) {
        }
    }
}
