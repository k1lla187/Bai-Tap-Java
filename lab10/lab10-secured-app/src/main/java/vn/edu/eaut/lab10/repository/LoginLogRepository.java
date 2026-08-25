package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.LoginLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class LoginLogRepository {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public void save(LoginLog log) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<LoginLog> findByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM LoginLog l WHERE l.userId = :uid ORDER BY l.loggedAt DESC",
                    LoginLog.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<LoginLog> findRecent(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM LoginLog l ORDER BY l.loggedAt DESC",
                    LoginLog.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<LoginLog> findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM LoginLog l WHERE l.email = :email ORDER BY l.loggedAt DESC",
                    LoginLog.class)
                    .setParameter("email", email)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
