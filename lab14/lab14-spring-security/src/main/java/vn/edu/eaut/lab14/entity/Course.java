package vn.edu.eaut.lab14.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String code;
    @Column(nullable = false) private String name;
    private int credits;
    public Course() { }
    public Course(String code, String name, int credits) { this.code = code; this.name = name; this.credits = credits; }
    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String value) { code = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    public int getCredits() { return credits; }
    public void setCredits(int value) { credits = value; }
}
