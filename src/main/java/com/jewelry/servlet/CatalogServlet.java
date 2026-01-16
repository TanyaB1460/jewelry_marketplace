package com.jewelry.servlet;

import com.jewelry.model.Product;
import com.jewelry.service.OrderService;
import com.jewelry.service.ProductService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private ProductService productService;
    private OrderService orderService;
    private Configuration fmConfig;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        fmConfig = (Configuration) getServletContext().getAttribute("fmConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            List<Product> products = productService.getAllProducts();


            Map<String, Object> data = new HashMap<>();
            data.put("products", products);


            Object userId = req.getSession().getAttribute("userId");
            data.put("isLoggedIn", userId != null);


            data.put("username", req.getSession().getAttribute("username"));
            data.put("role", req.getSession().getAttribute("role"));
            data.put("request", req);


            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate("catalog.ftl");
            template.process(data, resp.getWriter());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CatalogServlet.doPost вызван");


        Integer userId = (Integer) req.getSession().getAttribute("userId");

        if (userId == null) {

            System.out.println("Пользователь не авторизован, перенаправляем на авторизацию");
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }


        String role = (String) req.getSession().getAttribute("role");

        if (!"CUSTOMER".equals(role)) {

            System.out.println("Пользователь не является покупателем, роль: " + role);
            resp.sendRedirect(req.getContextPath() + "/catalog");
            return;
        }

        try {

            Integer productId = Integer.parseInt(req.getParameter("productId"));
            Integer quantity = Integer.parseInt(req.getParameter("quantity"));


            System.out.println("Добавление в корзину: userId=" + userId +
                    ", productId=" + productId + ", quantity=" + quantity);


            orderService.addToCart(userId, productId, quantity);

            System.out.println("Товар успешно добавлен, перенаправляем в корзину");


            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (Exception e) {

            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Ошибка при добавлении в корзину: " + e.getMessage(), e);
        }
    }
}