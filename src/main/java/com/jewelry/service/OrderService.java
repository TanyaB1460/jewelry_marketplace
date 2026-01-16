package com.jewelry.service;
import com.jewelry.dao.OrderDao;
import com.jewelry.model.Order;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order getOrderById(Integer orderId) throws SQLException {
        return orderDao.findById(orderId);
    }

    public List<Order> getOrdersByCustomer(Integer customerId) throws SQLException {
        return orderDao.findByCustomerId(customerId);
    }

    public List<Order> getCart(Integer customerId) throws SQLException {
        return orderDao.findByCustomerIdAndStatus(customerId, "CART");
    }

    public void addToCart(Integer customerId, Integer productId, Integer quantity) throws SQLException {
        Order order = new Order(null, customerId, productId, quantity, "CART", null, null, null);
        orderDao.save(order);
    }

    public void removeFromCart(Integer orderId) throws SQLException {
        orderDao.delete(orderId);
    }

    public void checkout(List<Order> cartItems) throws SQLException {
        for (Order order : cartItems) {
            order.setStatus("PAID");
            orderDao.update(order);
        }
    }
}
