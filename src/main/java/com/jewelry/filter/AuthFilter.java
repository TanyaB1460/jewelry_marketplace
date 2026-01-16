package com.jewelry.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/auth",
            "/catalog",
            "/css/"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String requestURI = httpReq.getRequestURI();
        String contextPath = httpReq.getContextPath();
        String path = requestURI.substring(contextPath.length());

        boolean isPublic = PUBLIC_URLS.stream().anyMatch(path::startsWith);

        if (!isPublic) {
            HttpSession session = httpReq.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                httpResp.sendRedirect(contextPath + "/auth");
                return;
            }
        }

        chain.doFilter(request, response);
    }

}