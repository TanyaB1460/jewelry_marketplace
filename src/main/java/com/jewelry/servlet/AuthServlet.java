package com.jewelry.servlet;

import com.jewelry.model.User;
import com.jewelry.service.UserService;
import com.jewelry.util.ValidationUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private UserService userService;
    private Configuration fmConfig;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        fmConfig = (Configuration) getServletContext().getAttribute("fmConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            String templateName = "login.ftl";

            if ("register".equals(action)) {
                templateName = "register.ftl";
            }

            Map<String, Object> data = new HashMap<>();
            data.put("request", req);
            data.put("contextPath", req.getContextPath());

            String error = req.getParameter("error");
            if (error != null && !error.isEmpty()) {
                data.put("error", error);
            }

            resp.setContentType("text/html; charset=UTF-8");
            Template template = fmConfig.getTemplate(templateName);
            template.process(data, resp.getWriter());

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("register".equals(action)) {
                handleRegister(req, resp);
            } else {
                handleLogin(req, resp);
            }
        } catch (Exception e) {
            String error = e.getMessage();
            String encodedError = java.net.URLEncoder.encode(error, "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/auth?action=" + action + "&error=" + encodedError);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        String role = req.getParameter("role");

        if (!ValidationUtil.isValidEmail(email)) throw new IllegalArgumentException("Invalid email");
        if (!ValidationUtil.isValidPassword(password)) throw new IllegalArgumentException("Password too short");
        if (!ValidationUtil.isValidUsername(username)) throw new IllegalArgumentException("Invalid username");
        if (!("MAKER".equals(role) || "CUSTOMER".equals(role))) throw new IllegalArgumentException("Invalid role");

        User user = userService.register(email, password, username, role);

        HttpSession session = req.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());

        if ("MAKER".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/product");
        } else {
            resp.sendRedirect(req.getContextPath() + "/catalog");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.authenticate(email, password);
        if (user == null) {
            throw new IllegalArgumentException("Wrong credentials");
        }

        HttpSession session = req.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());

        if ("MAKER".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/product");
        } else {
            resp.sendRedirect(req.getContextPath() + "/catalog");
        }
    }
}