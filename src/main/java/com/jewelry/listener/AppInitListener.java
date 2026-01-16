package com.jewelry.listener;

import com.jewelry.dao.*;
import com.jewelry.service.*;
import freemarker.template.Configuration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@WebListener
public class AppInitListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(AppInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Начало инициализации контекста приложения");
        ServletContext context = sce.getServletContext();

        try {
            UserDao userDao = new UserDao();
            ProductDao productDao = new ProductDao();
            OrderDao orderDao = new OrderDao();

            UserService userService = new UserService(userDao);
            ProductService productService = new ProductService(productDao);
            OrderService orderService = new OrderService(orderDao);

            context.setAttribute("userService", userService);
            context.setAttribute("productService", productService);
            context.setAttribute("orderService", orderService);

            Configuration fmConfig = new Configuration(Configuration.VERSION_2_3_32);
            String templatePath = context.getRealPath("/WEB-INF/templates");
            fmConfig.setDirectoryForTemplateLoading(new File(templatePath));
            fmConfig.setDefaultEncoding("UTF-8");
            context.setAttribute("fmConfig", fmConfig);

            logger.info("Приложение успешно инициализировано. Путь к шаблонам: {}", templatePath);

        } catch (Exception e) {
            logger.atError().withThrowable(e).log("Ошибка при инициализации приложения");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Контекст приложения уничтожен");
    }
}

