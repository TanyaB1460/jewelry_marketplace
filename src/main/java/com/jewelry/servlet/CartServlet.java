package com.jewelry.servlet;

import com.jewelry.model.Order;
import com.jewelry.service.OrderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private OrderService orderService;  // Сервис для работы с заказами
    private Configuration fmConfig;     // Конфигурация Freemarker для шаблонов

    @Override
    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        fmConfig = (Configuration) getServletContext().getAttribute("fmConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            System.out.println("Пользователь не авторизован, перенаправляем на авторизацию");
            resp.sendRedirect(req.getContextPath() + "/auth"); // Перенаправляем на страницу авторизации
            return;
        }

        try {
            List<Order> cartItems = orderService.getCart(userId);
            System.out.println("Найдено " + cartItems.size() + " товаров в корзине"); // Логирование количества товаров

            Double total = 0.0;
            for (Order order : cartItems) {
                total += order.getProductPrice() * order.getQuantity();
            }

            Map<String, Object> data = new HashMap<>();
            data.put("cartItems", cartItems);
            data.put("total", total);
            data.put("username", req.getSession().getAttribute("username"));
            data.put("request", req);


            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate("cart.ftl");
            template.process(data, resp.getWriter());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            String action = req.getParameter("action");

            if ("add".equals(action)) {

                Integer productId = Integer.parseInt(req.getParameter("productId"));
                Integer quantity = Integer.parseInt(req.getParameter("quantity"));
                System.out.println("Adding to cart - userId: " + userId +
                        ", productId: " + productId + ", quantity: " + quantity);
                orderService.addToCart(userId, productId, quantity);
            } else if ("remove".equals(action)) {

                Integer orderId = Integer.parseInt(req.getParameter("orderId"));
                orderService.removeFromCart(orderId);

                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            } else if ("checkout".equals(action)) {

                List<Order> cartItems = orderService.getCart(userId);
                orderService.checkout(cartItems);

                resp.sendRedirect(req.getContextPath() + "/cart?success=order");
                return;
            }


            resp.sendRedirect(req.getContextPath() + "/cart?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error in CartServlet: " + e.getMessage(), e);
        }
    }
}