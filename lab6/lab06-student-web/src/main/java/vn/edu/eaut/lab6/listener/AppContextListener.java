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

        // Khoi tao 5 sinh vien mau
        Student student1 = new Student("SV001", "Nguyen Van A", "CNTT1", "nva@eaut.edu.vn");
        Student student2 = new Student("SV002", "Tran Thi B", "CNTT2", "ttb@eaut.edu.vn");
        Student student3 = new Student("SV003", "Le Van C", "CNTT1", "lvc@eaut.edu.vn");
        Student student4 = new Student("SV004", "Pham Thi D", "CNTT2", "ptd@eaut.edu.vn");
        Student student5 = new Student("SV005", "Hoang Van E", "CNTT1", "hve@eaut.edu.vn");

        // Luu vao StudentStore
        StudentStore.add(student1);
        StudentStore.add(student2);
        StudentStore.add(student3);
        StudentStore.add(student4);
        StudentStore.add(student5);

        System.out.println("Da khoi tao 5 sinh vien mau thanh cong!");
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
