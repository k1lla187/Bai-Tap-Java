package vn.edu.eaut.lab15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Bài 10: Cấu hình Spring Security 6 và Phân quyền người dùng.
 * Phân chia 2 vai trò:
 * - ADMIN: Quản lý toàn bộ sinh viên, khóa học (thêm, sửa, xóa), đăng ký và xem dashboard.
 * - USER: Xem danh sách sinh viên, khóa học, thực hiện đăng ký/hủy học phần, xem dashboard.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Tài nguyên công khai và trang login, H2 console
                .requestMatchers("/login", "/css/**", "/js/**", "/webjars/**", "/h2-console/**", "/error", "/403").permitAll()

                // Phân quyền cho ADMIN đối với thao tác thay đổi dữ liệu Sinh viên
                .requestMatchers(
                    "/students/new", "/students/create", "/students/edit/**", 
                    "/students/save", "/students/delete/**"
                ).hasRole("ADMIN")

                // Phân quyền cho ADMIN đối với thao tác thay đổi dữ liệu Khóa học
                .requestMatchers(
                    "/courses/new", "/courses/create", "/courses/edit/**", 
                    "/courses/save", "/courses/delete/**"
                ).hasRole("ADMIN")

                // Các chức năng xem danh sách và đăng ký học phần: Dành cho cả USER và ADMIN
                .requestMatchers(
                    "/", "/dashboard", "/students", "/courses", "/enrollments/**"
                ).hasAnyRole("ADMIN", "USER")

                // Mọi yêu cầu khác đều cần đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            )
            // Hỗ trợ truy cập giao diện H2 Console trong môi trường phát triển
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Tài khoản Quản trị viên (ADMIN)
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Tài khoản Sinh viên / Người dùng thông thường (USER)
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
