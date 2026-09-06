package vn.edu.eaut.lab14.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String studentCode;
    @Column(nullable = false) private String fullName;
    private String email;
    private String className;

    public Student() { }
    public Student(String studentCode, String fullName, String email, String className) {
        this.studentCode = studentCode; this.fullName = fullName; this.email = email; this.className = className;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String value) { studentCode = value; }
    public String getFullName() { return fullName; }
    public void setFullName(String value) { fullName = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getClassName() { return className; }
    public void setClassName(String value) { className = value; }
}
