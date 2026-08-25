package vn.edu.eaut.lab10.repository;

import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.GiaoVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class GiaoVienRepository {
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public GiaoVien findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try { return em.find(GiaoVien.class, id); }
        finally { em.close(); }
    }

    public GiaoVien findByMaGV(String maGV) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT g FROM GiaoVien g WHERE g.maGV = :ma", GiaoVien.class)
                    .setParameter("ma", maGV).getResultStream().findFirst().orElse(null);
        } finally { em.close(); }
    }

    public List<GiaoVien> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT g FROM GiaoVien g WHERE g.active = true ORDER BY g.id DESC", GiaoVien.class).getResultList();
        } finally { em.close(); }
    }

    public List<GiaoVien> findAllActive() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT g FROM GiaoVien g WHERE g.active = true ORDER BY g.hoTen", GiaoVien.class).getResultList();
        } finally { em.close(); }
    }

    public List<GiaoVien> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT g FROM GiaoVien g WHERE (g.maGV LIKE :k OR g.hoTen LIKE :k OR g.chuyenNganh LIKE :k) ORDER BY g.id DESC",
                    GiaoVien.class).setParameter("k", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try { return em.createQuery("SELECT COUNT(g) FROM GiaoVien g WHERE g.active = true", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    public boolean existsByMaGV(String maGV) { return findByMaGV(maGV) != null; }

    public boolean existsByMaGVExcludingId(String maGV, Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(g) FROM GiaoVien g WHERE g.maGV = :ma AND g.id != :id", Long.class)
                    .setParameter("ma", maGV).setParameter("id", id).getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }

    public void save(GiaoVien gv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.persist(gv); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(GiaoVien gv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); em.merge(gv); em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            GiaoVien g = em.find(GiaoVien.class, id);
            if (g != null) { g.setActive(false); em.merge(g); }
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
