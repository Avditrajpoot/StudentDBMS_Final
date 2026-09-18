package com.vityarthi.dbms.config;
import java.sql.*;
public final class DatabaseConnection {
    private DatabaseConnection() {}
    public static Connection getConnection() throws SQLException { return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD); }
}
