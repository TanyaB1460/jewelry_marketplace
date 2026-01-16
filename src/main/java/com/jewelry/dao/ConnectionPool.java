package com.jewelry.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static DataSource dataSource;

    public static void init() {
        try {
            Class.forName("org.postgresql.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/jewelry_db");
            config.setUsername("postgres");
            config.setPassword("2685");

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            config.setAutoCommit(true);

            config.setConnectionTestQuery("SELECT 1");
            config.setValidationTimeout(5000);

            dataSource = new HikariDataSource(config);

            logger.info("Пул соединений успешно создан");

        } catch (ClassNotFoundException e) {
            logger.error("Драйвер PostgreSQL не найден", e);
            throw new RuntimeException("Не удалось загрузить драйвер БД", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    init();
                }
            }
        }
        return dataSource.getConnection();
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Ошибка при закрытии соединения", e);
            }
        }
    }


    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void shutdown() {
        if (dataSource != null && dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
            logger.info("Пул соединений успешно закрыт");
        }
    }
}



