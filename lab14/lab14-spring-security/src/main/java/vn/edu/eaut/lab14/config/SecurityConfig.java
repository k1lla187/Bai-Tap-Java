package vn.edu.eaut.lab14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/about", "/login", "/css/**", "/error").permitAll()
                .requestMatchers("/courses/**").hasRole("ADMIN")
                .requestMatchers("/students/create", "/students/edit/**", "/students/save", "/students/delete/**")
                    .hasRole("ADMIN")
                .requestMatchers("/students/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated())
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/students", true).permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
            .exceptionHandling(exception -> exception.accessDeniedPage("/error/403"));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin").password(encoder.encode("123456")).roles("ADMIN").build();
        UserDetails user = User.withUsername("user").password(encoder.encode("123456")).roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
