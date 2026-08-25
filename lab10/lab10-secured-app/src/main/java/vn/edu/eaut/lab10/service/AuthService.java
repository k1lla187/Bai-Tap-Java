package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return null;
        }
        User user = userRepository.findByEmail(email);
        if (user == null || !user.isActive()) {
            return null;
        }
        // BCrypt check: support both bcrypt and plain-text (for seed data)
        if (BCrypt.checkpw(password, user.getPassword())
                || user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword.startsWith("$2")) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }
        return plainPassword.equals(hashedPassword);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Integer id) {
        return userRepository.findById(id);
    }
}
