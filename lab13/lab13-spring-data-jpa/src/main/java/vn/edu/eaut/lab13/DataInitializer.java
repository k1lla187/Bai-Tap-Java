package vn.edu.eaut.lab13;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.eaut.lab13.entity.Course;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.CourseRepository;
import vn.edu.eaut.lab13.repository.StudentRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository, CourseRepository courseRepository) {
        return args -> {
            // Check if data already exists
            if (studentRepository.count() == 0) {
                System.out.println("Initializing sample data...");

                // Add sample students
                studentRepository.save(new Student("SV001", "Nguyễn Văn A", "nguyenvana@eaut.edu.vn", "CNTT01"));
                studentRepository.save(new Student("SV002", "Trần Thị B", "tranthib@eaut.edu.vn", "CNTT01"));
                studentRepository.save(new Student("SV003", "Lê Văn C", "levanc@eaut.edu.vn", "CNTT02"));
                studentRepository.save(new Student("SV004", "Phạm Thị D", "phamthid@eaut.edu.vn", "CNTT02"));
                studentRepository.save(new Student("SV005", "Hoàng Văn E", "hoangvane@eaut.edu.vn", "KTPM01"));

                System.out.println("Added " + studentRepository.count() + " students");
            }

            if (courseRepository.count() == 0) {
                // Add sample courses
                courseRepository.save(new Course("JAVA101", "Lập trình Java cơ bản", 3));
                courseRepository.save(new Course("WEB201", "Lập trình Web", 4));
                courseRepository.save(new Course("DB301", "Cơ sở dữ liệu", 3));
                courseRepository.save(new Course("SE401", "Công nghệ phần mềm", 3));
                courseRepository.save(new Course("NET501", "Lập trình .NET", 4));

                System.out.println("Added " + courseRepository.count() + " courses");
            }

            System.out.println("Database initialization completed!");
        };
    }
}
