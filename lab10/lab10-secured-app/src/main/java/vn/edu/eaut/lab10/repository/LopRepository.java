package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.Lop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class LopRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public Lop findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(Lop.class, id); }
        finally { em.close(); }
    }

    public Lop findByMaLop(String maLop) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Lop l WHERE l.maLop = :ma", Lop.class)
                    .setParameter("ma", maLop).getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    public List<Lop> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Lop l WHERE l.active = true ORDER BY l.id DESC", Lop.class).getResultList();
        } finally { em.close(); }
    }

    public List<Lop> findAllActive() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Lop l WHERE l.active = true ORDER BY l.tenLop", Lop.class).getResultList();
        } finally { em.close(); }
    }

    public List<Lop> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Lop l WHERE l.maLop LIKE :k OR l.tenLop LIKE :k OR l.khoa LIKE :k ORDER BY l.id DESC",
                    Lop.class).setParameter("k", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(l) FROM Lop l WHERE l.active = true", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    public boolean existsByMaLop(String maLop) { return findByMaLop(maLop) != null; }

    public boolean existsByMaLopExcludingId(String maLop, Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(l) FROM Lop l WHERE l.maLop = :ma AND l.id != :id", Long.class)
                    .setParameter("ma", maLop).setParameter("id", id).getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }

    public void save(Lop lop) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(lop); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(Lop lop) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(lop); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Lop l = em.find(Lop.class, id);
            if (l != null) { l.setActive(false); em.merge(l); }
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
