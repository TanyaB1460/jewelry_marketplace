package com.jewelry.servlet;

import com.jewelry.model.Product;
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

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private Configuration fmConfig;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
        fmConfig = (Configuration) getServletContext().getAttribute("fmConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.startsWith("/edit/")) {
            showEditForm(req, resp, pathInfo);
        } else if (pathInfo != null && pathInfo.equals("/create")) {
            showCreateForm(req, resp);
        } else {
            listProducts(req, resp);
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("request", req);

            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate("product-form.ftl");
            template.process(data, resp.getWriter());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp, String pathInfo) throws ServletException, IOException {
        try {
            Integer productId = Integer.parseInt(pathInfo.replace("/edit/", ""));

            Product product = productService.getProductById(productId);

            Map<String, Object> data = new HashMap<>();
            data.put("product", product);
            data.put("request", req);

            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate("product-form.ftl");
            template.process(data, resp.getWriter());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer userId = (Integer) req.getSession().getAttribute("userId");

            List<Product> products = productService.getProductsByMaker(userId);

            Map<String, Object> data = new HashMap<>();
            data.put("products", products);
            data.put("username", req.getSession().getAttribute("username"));
            data.put("request", req);

            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate("product-list.ftl");
            template.process(data, resp.getWriter());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null || !"MAKER".equals(req.getSession().getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                Integer productId = Integer.parseInt(req.getParameter("id"));
                productService.deleteProduct(productId);
            } else {
                String title = req.getParameter("title");
                String description = req.getParameter("description");
                Double price = Double.parseDouble(req.getParameter("price"));
                String category = req.getParameter("category");
                String idParam = req.getParameter("id");


                Product product = new Product();
                product.setTitle(title);
                product.setDescription(description);
                product.setPrice(price);
                product.setCategory(category);
                product.setMakerId(userId);

                if (idParam != null && !idParam.isEmpty()) {
                    product.setId(Integer.parseInt(idParam));
                    productService.updateProduct(product);
                } else {
                    productService.createProduct(product);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/product");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}