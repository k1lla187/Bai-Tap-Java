package vn.edu.eaut.lab10.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_logs")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String action; // LOGIN, LOGOUT, LOGIN_FAILED

    @Column(length = 50)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt;

    public LoginLog() {
    }

    public LoginLog(Integer userId, String email, String action, String ipAddress, String userAgent) {
        this.userId = userId;
        this.email = email;
        this.action = action;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.loggedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (loggedAt == null) loggedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }
}
