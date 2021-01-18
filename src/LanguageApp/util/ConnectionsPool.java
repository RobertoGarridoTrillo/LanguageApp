package LanguageApp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class ConnectionsPool implements IConnectionsPool
 {
  // Field Class
  private static String db_url, driver;
  private final List<Connection> connectionsPool;
  private List<Connection> usedConnections;
  private static final int INITIAL_POOL_SIZE = 0b11;

// Helper to directoriy
  private static String path, se;


  /**
   *
   * @param url
   * @param user
   * @param password
   * @param connectionsPool
   * @param poolSize
   */
  public ConnectionsPool(ArrayList connectionsPool)
   {
    this.connectionsPool = connectionsPool;
    usedConnections = new ArrayList<>();
   }


  /**
   *
   * @param url
   * @param user
   * @param password
   * @return
   * @throws Exception
   */
  public static ConnectionsPool create() throws Exception
   {
    //  
    path = System.getProperty("user.dir");
    se = System.getProperty("file.separator");
    //
    db_url = "jdbc:sqlite:" + path + se + "database.db";
    driver = "org.sqlite.JDBC";
    
    // To force to sqlite to enable Foreign Keys
    Class.forName(driver);// See the explication
    SQLiteConfig config = new SQLiteConfig();
    config.enforceForeignKeys(true);
    config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "true");

    //
    ArrayList<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);

    for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
      pool.add(createConnection(db_url, config.toProperties()));
    }

    return new ConnectionsPool(pool);

   }


  /**
   *
   * @return
   */
  @Override
  public Connection getConnection() throws Exception
   {
    Connection connection = connectionsPool.remove(connectionsPool.size() - 1);
    usedConnections.add(connection);
    return connection;
   }


  /**
   *
   * @param url
   * @param user
   * @param password
   * @return
   * @throws SQLException
   */
  private static Connection createConnection(String db_url, Properties properties)
          throws Exception
   {
    return DriverManager.getConnection(db_url, properties);
   }


  /**
   *
   * @param connection
   * @return
   */
  @Override
  public boolean releaseConnection(Connection connection) throws
          UnsupportedOperationException, ClassCastException,
          NullPointerException, IllegalArgumentException
   {
    connectionsPool.add(connection);
    return usedConnections.remove(connection);
   }


  /**
   *
   * @throws SQLException
   */
  @Override
  public void shutdown() throws Exception
   {
    usedConnections.forEach(this::releaseConnection);
    //connectionsPool.forEach(Connection::close);
    for (Connection c : connectionsPool) {
      c.close();
    }
    connectionsPool.clear();
   }


  /**
   *
   * @return @throws java.lang.Exception
   */
  public int getSize() throws Exception
   {
    return connectionsPool.size() + usedConnections.size();
   }


  /**
   *
   * @return
   */
  @Override
  public String getDb_url() throws Exception
   {
    return db_url;
   }

  public static void main(String[] args)
   {
    try {
      ConnectionsPool cp = ConnectionsPool.create();
      System.out.println(cp.getSize());
      cp.shutdown();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
   }

 }

/*
En sistemas antiguos, para que DriverManager tuviera "registrados" los drivers, era necesario cargar la clase en la máquina virtual. Para eso es el Class.forName(), simplemente carga la clase con el nombre indicado.

A partir de JDK 6, los drivers JDBC 4 ya se registran automáticamente y no es necesario el Class.forName(), sólo que estén en el classpath de la JVM.
 */
