package vn.edu.eaut.lab6.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionLogListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session moi duoc tao: " + se.getSession().getId());
        System.out.println("  - Thoi gian tao: " + new java.util.Date(se.getSession().getCreationTime()));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session da bi huy: " + se.getSession().getId());
    }
}
