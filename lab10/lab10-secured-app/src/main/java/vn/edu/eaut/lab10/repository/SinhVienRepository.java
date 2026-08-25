package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.SinhVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class SinhVienRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public SinhVien findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(SinhVien.class, id); }
        finally { em.close(); }
    }

    public SinhVien findByMaSV(String maSV) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s WHERE s.maSV = :ma", SinhVien.class)
                    .setParameter("ma", maSV).getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    public List<SinhVien> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id DESC", SinhVien.class).getResultList();
        } finally { em.close(); }
    }

    public List<SinhVien> findAll(int page, int size) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id DESC", SinhVien.class)
                    .setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
        } finally { em.close(); }
    }

    public List<SinhVien> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM SinhVien s WHERE s.maSV LIKE :k OR s.hoTen LIKE :k OR s.lop LIKE :k ORDER BY s.id DESC",
                    SinhVien.class).setParameter("k", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(s) FROM SinhVien s", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    public boolean existsByMaSV(String maSV) { return findByMaSV(maSV) != null; }

    public boolean existsByMaSVExcludingId(String maSV, Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(s) FROM SinhVien s WHERE s.maSV = :ma AND s.id != :id", Long.class)
                    .setParameter("ma", maSV).setParameter("id", id).getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }

    public void save(SinhVien sv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(sv); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(SinhVien sv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(sv); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SinhVien s = em.find(SinhVien.class, id);
            if (s != null) em.remove(s);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
