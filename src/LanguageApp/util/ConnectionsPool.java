package LanguageApp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

public class ConnectionsPool implements IConnectionsPool
 {
  // Field Class
  private static String db_url, driver;
  private final List<Connection> connectionsPool;
  private List<Connection> usedConnections;
  private static final int INITIAL_POOL_SIZE = 0b11;//2
  private static final int MAX_POOL_SIZE = 0xa;//10


  // Helper to directoriy
  private static String path, se;


  public ConnectionsPool(ArrayList connectionsPool)
   {
    this.connectionsPool = connectionsPool;
    usedConnections = new ArrayList<>();
   }


  public static ConnectionsPool create() throws Exception
   {
    //  
    path = System.getProperty("user.dir");
    se = System.getProperty("file.separator");
    //
    db_url = "jdbc:sqlite:" + path + se + "database.db";
    driver = "org.sqlite.JDBC";

    // To force to sqlite to enable Foreign Keys
    SQLiteConfig config = handleCreate();

    ArrayList<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);

    for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
      pool.add(createConnection(db_url, config.toProperties()));
    }
    return new ConnectionsPool(pool);

   }


  private static SQLiteConfig handleCreate() throws ClassNotFoundException
   {
    // To force to sqlite to enable Foreign Keys
    Class.forName(driver);// See the explication
    SQLiteConfig config = new SQLiteConfig();
    config.setOpenMode(SQLiteOpenMode.READWRITE);
    config.setOpenMode(SQLiteOpenMode.CREATE);
    config.setOpenMode(SQLiteOpenMode.NOMUTEX);
    config.setEncoding(SQLiteConfig.Encoding.UTF_8);
    config.setTransactionMode(SQLiteConfig.TransactionMode.DEFERRED);
    config.enforceForeignKeys(true);
    config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "true");
    config.enableFullSync(true);
    config.setSynchronous(SQLiteConfig.SynchronousMode.OFF);
    return config;
   }


  @Override
  public Connection getConnection() throws Exception
   {
    if (connectionsPool.isEmpty()) {
      if (usedConnections.size() < MAX_POOL_SIZE) {
        connectionsPool.add(createConnection(db_url, handleCreate().toProperties()));
      } else {
        throw new IndexOutOfBoundsException();
      }

    }
    Connection connection = connectionsPool.remove(connectionsPool.size() - 1);
    connection.setClientInfo("", "");
    usedConnections.add(connection);
    /*/*System.out.println("getConnection()");
    System.out.println(connection);
    System.out.println(
            "total " + getSize() +
            "  usedConnections " + usedConnections.size() + " " +
            "  connectionsPool " + connectionsPool.size() + "\n"); */
    return connection;
   }


  @Override
  public boolean releaseConnection(Connection connection) throws
          UnsupportedOperationException, ClassCastException,
          NullPointerException, IllegalArgumentException
   {
    connectionsPool.add(connection);
    boolean b = usedConnections.remove(connection);
    /*/*System.out.println("releaseConnection()");
    System.out.println(connection);
    System.out.println(
            "total " + getSize() +
            "  usedConnections " + usedConnections.size() + " " +
            "  connectionsPool " + connectionsPool.size() + "\n"); */
    return b;
   }


  private static Connection createConnection(String db_url, Properties properties)
          throws Exception
   {
    return DriverManager.getConnection(db_url, properties);
   }


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


  public int getSize() //throws Exception
   {
    return connectionsPool.size() + usedConnections.size();
   }


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
