package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentStore {
    private static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        students.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
    }

    public static List<Student> findAll() {
        return students;
    }

    public static void add(Student student) {
        students.add(student);
    }

    public static Student findById(String id) {
        for (Student sv : students) {
            if (sv.getId().equals(id)) {
                return sv;
            }
        }
        return null;
    }

    public static boolean update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                return true;
            }
        }
        return false;
    }

    public static boolean delete(String id) {
        return students.removeIf(sv -> sv.getId().equals(id));
    }

    public static List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return students;
        }
        String lowerKeyword = keyword.toLowerCase();
        for (Student sv : students) {
            if (sv.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(sv);
            }
        }
        return result;
    }
}
