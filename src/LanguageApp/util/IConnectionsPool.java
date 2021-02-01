package LanguageApp.util;

import java.sql.Connection;

/**
 *
 * @author ROBEG
 */
public interface IConnectionsPool
 {

  /**
   *
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  String getDb_url() throws Exception;

  /**
   *
   * @param className
   * @param methodName
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  Connection getConnection() throws Exception;

  /**
   *
   * @param connection
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  boolean releaseConnection(Connection connection)
          throws Exception;

  /**
   * 
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public void shutdown() throws Exception;

 }
