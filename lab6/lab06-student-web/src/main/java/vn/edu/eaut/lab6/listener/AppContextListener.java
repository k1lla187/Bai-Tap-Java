package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Ung dung Lab 6 da khoi dong ===");
        ServletContext context = sce.getServletContext();
        context.setAttribute("appName", "Lab 6 - Quan ly Sinh Vien");
        System.out.println("Ung dung Lab 6 da khoi dong thanh cong!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        int totalStudents = StudentStore.findAll().size();
        System.out.println("Ung dung Lab 6 da dung. Tong so sinh vien: " + totalStudents);
        context.removeAttribute("appName");
    }
}
