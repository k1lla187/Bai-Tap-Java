package vn.edu.eaut.lab7.listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void contextInitialized(ServletContextEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] *** Ứng dụng Lab 7 CRUD MVC đã khởi động ***");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] *** Ứng dụng Lab 7 CRUD MVC đã dừng ***");
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] Session tạo: " + event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] Session hủy: " + event.getSession().getId());
    }
}
