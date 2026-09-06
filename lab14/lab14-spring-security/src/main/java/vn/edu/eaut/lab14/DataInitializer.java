package vn.edu.eaut.lab14;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.eaut.lab14.entity.Course;
import vn.edu.eaut.lab14.entity.Student;
import vn.edu.eaut.lab14.repository.CourseRepository;
import vn.edu.eaut.lab14.repository.StudentRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(StudentRepository students, CourseRepository courses) {
        return args -> {
            if (students.count() == 0) {
                students.save(new Student("20230199", "Vi Anh Tuan", "tuan@example.com", "DCCNTT14.10"));
                students.save(new Student("20230200", "Nguyen Minh Anh", "anh@example.com", "DCCNTT14.10"));
            }
            if (courses.count() == 0) {
                courses.save(new Course("JAVA14", "Cong nghe Java", 3));
                courses.save(new Course("WEB14", "Lap trinh Web", 3));
            }
        };
    }
}
