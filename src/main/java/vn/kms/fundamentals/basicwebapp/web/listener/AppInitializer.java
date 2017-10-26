//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.web.listener;

import org.flywaydb.core.Flyway;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;
import vn.kms.fundamentals.basicwebapp.utils.ControllerManager;
import vn.kms.fundamentals.basicwebapp.utils.TinyIoC;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {
    public static final String IOC_ATTR_KEY = AppInitializer.class + "::IoC";
    public static final String CONTROLLER_MNG_ATTR_KEY = AppInitializer.class + "::ControllerManager";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initDbConnection();
        migrateDb();

        initIoC(event.getServletContext());
        initControllerManager(event.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // TODO: Clean up/release any resource if needed.
    }

    private void initDbConnection() {
        // TODO: The DB connection should be configurable from external file
        String url = "jdbc:postgresql://localhost:5432/basicwebapp";
        String username = "basicwebapp";
        String password = "basicwebapp@123";
        String driverClass = "org.postgresql.Driver";

        ConnectionManager connManager = ConnectionManager.getInstance();
        connManager.initialize(url, username, password, driverClass);
    }

    private void migrateDb() {
        ConnectionManager connManager = ConnectionManager.getInstance();

        Flyway flyway = new Flyway();
        flyway.setDataSource(connManager.getUrl(), connManager.getUsername(), connManager.getPassword());
        flyway.migrate();
    }

    private void initIoC(ServletContext servletContext) {
        TinyIoC ioc = new TinyIoC();
        ioc.initialize();

        servletContext.setAttribute(IOC_ATTR_KEY, ioc);
    }

    private void initControllerManager(ServletContext servletContext) {
        ControllerManager controllerManager = new ControllerManager();
        controllerManager.initialize(servletContext);

        servletContext.setAttribute(CONTROLLER_MNG_ATTR_KEY, controllerManager);
    }
}
