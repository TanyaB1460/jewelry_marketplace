package com.jewelry.dao;
import com.jewelry.model.Order;
import java.sql.*;
import java.util.*;

public class OrderDao {
    public Order findById(Integer id) throws SQLException {
        String sql = "SELECT o.*, p.title, p.price, u.username FROM orders o " +
                "JOIN products p ON o.product_id = p.id " +
                "JOIN users u ON o.customer_id = u.id WHERE o.id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrder(rs);
                }
            }
        }
        return null;
    }

    public List<Order> findByCustomerId(Integer customerId) throws SQLException {
        String sql = "SELECT o.*, p.title, p.price, u.username FROM orders o " +
                "JOIN products p ON o.product_id = p.id " +
                "JOIN users u ON o.customer_id = u.id " +
                "WHERE o.customer_id = ? ORDER BY o.id DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        }
        return orders;
    }

    public List<Order> findByCustomerIdAndStatus(Integer customerId, String status) throws SQLException {
        String sql = "SELECT o.*, p.title, p.price, u.username FROM orders o " +
                "JOIN products p ON o.product_id = p.id " +
                "JOIN users u ON o.customer_id = u.id " +
                "WHERE o.customer_id = ? AND o.status = ? ORDER BY o.id DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        }
        return orders;
    }

    public void save(Order order) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, product_id, quantity, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getProductId());
            stmt.setInt(3, order.getQuantity());
            stmt.setString(4, order.getStatus() != null ? order.getStatus() : "CART");
            stmt.executeUpdate();
        }
    }

    public void update(Order order) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus());
            stmt.setInt(2, order.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getString("status"),
                rs.getString("title"),
                rs.getDouble("price"),
                rs.getString("username")
        );
    }
}
