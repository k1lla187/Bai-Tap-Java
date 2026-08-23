package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.SinhVien;
import java.util.List;

/**
 * Repository class for SinhVien entity CRUD operations
 */
public class SinhVienRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<SinhVien> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id DESC", SinhVien.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public SinhVien findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SinhVien.class, id);
        } finally {
            em.close();
        }
    }

    public SinhVien findByMaSV(String maSV) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SinhVien> query = em.createQuery(
                    "SELECT s FROM SinhVien s WHERE s.maSinhVien = :maSV", SinhVien.class);
            query.setParameter("maSV", maSV);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public void save(SinhVien sinhVien) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sinhVien);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(SinhVien sinhVien) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sinhVien);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SinhVien sv = em.find(SinhVien.class, id);
            if (sv != null) {
                em.remove(sv);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Search by name or class using JPQL
     */
    public List<SinhVien> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT s FROM SinhVien s WHERE LOWER(s.hoTen) LIKE :kw OR LOWER(s.lop) LIKE :kw";
            return em.createQuery(jpql, SinhVien.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Pagination support
     */
    public List<SinhVien> findAll(int first, int max) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s ORDER BY s.id DESC", SinhVien.class)
                    .setFirstResult(first)
                    .setMaxResults(max)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM SinhVien s", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<SinhVien> findByLopHocId(Integer lopHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SinhVien s WHERE s.lopHoc.id = :lopHocId", SinhVien.class)
                    .setParameter("lopHocId", lopHocId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
