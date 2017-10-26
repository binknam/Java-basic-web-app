package vn.kms.fundamentals.basicwebapp.repository.impl;

import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.Ignore;
import vn.kms.fundamentals.basicwebapp.utils.ConnectionManager;

@Ignore
public abstract class BaseDaoTest {
  /**
   * Initialize DB schema
   */
  @BeforeClass
  public static void initDb() throws Exception {
    // TODO: The following code should be called ONCE. Currently, it is
    // called for each unit-test class
    String jdbc = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;MODE=PostgreSQL;IGNORECASE=TRUE";

    ConnectionManager connManager = ConnectionManager.getInstance();
    connManager.initialize(jdbc, "", "", "org.h2.Driver");

    Flyway flyway = new Flyway();
    flyway.setDataSource(connManager.getUrl(), connManager.getUsername(), connManager.getPassword());
    flyway.migrate();
  }
}
