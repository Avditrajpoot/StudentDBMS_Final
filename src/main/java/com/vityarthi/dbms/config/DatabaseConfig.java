package com.vityarthi.dbms.config;

public final class DatabaseConfig {
    private DatabaseConfig() {}
    public static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/student_dbms?useSSL=false&serverTimezone=UTC");
    public static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    public static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "root");
}
