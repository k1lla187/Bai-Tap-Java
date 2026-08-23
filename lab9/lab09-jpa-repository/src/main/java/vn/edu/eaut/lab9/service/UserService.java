package vn.edu.eaut.lab9.service;

import vn.edu.eaut.lab9.model.User;
import vn.edu.eaut.lab9.repository.UserRepository;
import vn.edu.eaut.lab9.repository.RoleRepository;
import vn.edu.eaut.lab9.model.Role;
import java.util.List;

/**
 * Service class for User authentication and management
 */
public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public String register(User user) {
        // Validate
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return "Username khong duoc trong";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Mat khau phai co it nhat 6 ky tu";
        }
        if (user.getEmail() != null && !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Email khong dung dinh dang";
        }
        
        // Check duplicate
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username da ton tai";
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            return "Email da ton tai";
        }
        
        // Set default role
        if (user.getRole() == null) {
            Role userRole = roleRepository.findByName("USER");
            if (userRole != null) {
                user.setRole(userRole);
            }
        }
        
        userRepository.save(user);
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(Integer id) {
        userRepository.delete(id);
    }

    public boolean isAdmin(User user) {
        return user != null && user.getRole() != null && "ADMIN".equals(user.getRole().getName());
    }

    public boolean isTeacher(User user) {
        return user != null && user.getRole() != null && "TEACHER".equals(user.getRole().getName());
    }
}
