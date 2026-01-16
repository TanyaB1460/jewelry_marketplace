package com.jewelry.dao;
import com.jewelry.model.Product;
import java.sql.*;
import java.util.*;

public class ProductDao {
    public Product findById(Integer id) throws SQLException {
        String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.maker_id = u.id WHERE p.id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.maker_id = u.id WHERE p.is_active = TRUE ORDER BY p.id DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    public List<Product> findByMakerId(Integer makerId) throws SQLException {
        String sql = "SELECT p.*, u.username FROM products p JOIN users u ON p.maker_id = u.id WHERE p.maker_id = ? ORDER BY p.id DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, makerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO products (title, description, price, maker_id, category, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getTitle());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getMakerId());
            stmt.setString(5, product.getCategory());
            stmt.setBoolean(6, product.getIsActive() != null ? product.getIsActive() : true);
            stmt.executeUpdate();
        }
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE products SET title = ?, description = ?, price = ?, category = ? WHERE id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getTitle());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("maker_id"),
                rs.getString("category"),
                rs.getBoolean("is_active"),
                rs.getString("username")
        );
    }
}
