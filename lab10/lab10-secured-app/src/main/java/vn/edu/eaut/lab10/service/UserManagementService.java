package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;
import java.util.List;
import java.util.regex.Pattern;

public class UserManagementService {

    private final UserRepository userRepository = new UserRepository();
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.search(keyword.trim());
    }

    public String save(User user) {
        String validate = validate(user, true);
        if (validate != null) return validate;
        userRepository.save(user);
        return null;
    }

    public String update(User user) {
        String validate = validate(user, false);
        if (validate != null) return validate;
        userRepository.update(user);
        return null;
    }

    public void delete(Integer id) {
        userRepository.delete(id);
    }

    public void toggleActive(Integer id) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.update(user);
        }
    }

    public String changePassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return "Mat khau cu khong duoc trong";
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "Mat khau moi khong duoc trong";
        }
        if (newPassword.length() < 6) {
            return "Mat khau moi phai co it nhat 6 ky tu";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Mat khau moi khong khop";
        }
        User user = userRepository.findById(userId);
        if (user == null) return "Khong tim thay nguoi dung";

        // Verify old password
        if (!verifyPassword(oldPassword, user.getPassword())) {
            return "Mat khau cu khong dung";
        }

        user.setPassword(hashPassword(newPassword));
        userRepository.update(user);
        return null;
    }

    private String validate(User user, boolean isNew) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email khong duoc trong";
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return "Email khong dung dinh dang";
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            return "Ho ten khong duoc trong";
        }
        if (user.getRole() == null) {
            return "Vai tro khong duoc trong";
        }
        if (isNew) {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return "Mat khau khong duoc trong";
            }
            if (user.getPassword().length() < 6) {
                return "Mat khau phai co it nhat 6 ky tu";
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                return "Email da ton tai";
            }
        } else {
            if (userRepository.existsByEmailExcludingId(user.getEmail(), user.getId())) {
                return "Email da ton tai";
            }
        }
        return null;
    }

    private String hashPassword(String plainPassword) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword, org.mindrot.jbcrypt.BCrypt.gensalt(10));
    }

    private boolean verifyPassword(String plain, String hashed) {
        if (hashed.startsWith("$2")) {
            return org.mindrot.jbcrypt.BCrypt.checkpw(plain, hashed);
        }
        return plain.equals(hashed);
    }
}
