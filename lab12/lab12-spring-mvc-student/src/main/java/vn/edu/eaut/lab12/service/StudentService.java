package vn.edu.eaut.lab12.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentService - Service layer for student management
 * Implements in-memory CRUD operations using ArrayList.
 * This service simulates database operations for Lab 12.
 */
@Service
public class StudentService {
    
    // In-memory storage for students
    private final List<Student> students = new ArrayList<>();
    
    // Auto-increment ID counter
    private long nextId = 1;
    
    /**
     * Initialize with sample data
     */
    public StudentService() {
        // Add sample students for testing
        students.add(new Student(1L, "2023001", "Nguyen Van A", 
                "nguyenvana@eaut.edu.vn", "CNTT2023A", "0901234567", "Ha Noi"));
        students.add(new Student(2L, "2023002", "Tran Thi B", 
                "tranthib@eaut.edu.vn", "CNTT2023A", "0902345678", "Ho Chi Minh"));
        students.add(new Student(3L, "2023003", "Le Van C", 
                "levanc@eaut.edu.vn", "CNTT2023B", "0903456789", "Da Nang"));
        nextId = 4;
    }
    
    /**
     * Get all students
     * @return List of all students
     */
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
    
    /**
     * Find student by ID
     * @param id Student ID
     * @return Optional containing student if found
     */
    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
    
    /**
     * Find students by name (case-insensitive partial match)
     * @param name Search keyword
     * @return List of matching students
     */
    public List<Student> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        String searchName = name.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getFullName().toLowerCase().contains(searchName))
                .toList();
    }
    
    /**
     * Find student by student code
     * @param studentCode Student code
     * @return Optional containing student if found
     */
    public Optional<Student> findByStudentCode(String studentCode) {
        return students.stream()
                .filter(s -> s.getStudentCode().equals(studentCode))
                .findFirst();
    }
    
    /**
     * Check if student code exists (excluding specific student by ID)
     * @param studentCode Student code to check
     * @param excludeId ID to exclude from check (for update operations)
     * @return true if code already exists
     */
    public boolean isStudentCodeExists(String studentCode, Long excludeId) {
        return students.stream()
                .filter(s -> s.getStudentCode().equals(studentCode))
                .anyMatch(s -> excludeId == null || !s.getId().equals(excludeId));
    }
    
    /**
     * Save or update student
     * If student.id is null, creates new student
     * If student.id exists, updates existing student
     * @param student Student to save
     */
    public void save(Student student) {
        if (student.getId() == null) {
            // Create new student
            student.setId(nextId++);
            students.add(student);
        } else {
            // Update existing student
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId().equals(student.getId())) {
                    students.set(i, student);
                    break;
                }
            }
        }
    }
    
    /**
     * Delete student by ID
     * @param id Student ID to delete
     * @return true if student was deleted
     */
    public boolean deleteById(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }
    
    /**
     * Get total number of students
     * @return Count of students
     */
    public int count() {
        return students.size();
    }
}
