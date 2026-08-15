package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if ("delete".equals(action) && id != null) {
            StudentStore.delete(id);
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        String keyword = request.getParameter("search");
        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = StudentStore.searchByName(keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            students = StudentStore.findAll();
        }

        request.setAttribute("students", students);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            StudentStore.delete(id);
        } else if ("update".equals(action)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String className = request.getParameter("className");
            String email = request.getParameter("email");
            Student student = new Student(id, name, className, email);
            StudentStore.update(student);
        } else {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String className = request.getParameter("className");
            String email = request.getParameter("email");
            Student student = new Student(id, name, className, email);
            StudentStore.add(student);
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
