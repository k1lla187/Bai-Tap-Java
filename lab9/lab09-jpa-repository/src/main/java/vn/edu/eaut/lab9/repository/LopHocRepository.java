package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.LopHoc;
import java.util.List;

/**
 * Repository class for LopHoc entity CRUD operations
 */
public class LopHocRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<LopHoc> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM LopHoc l ORDER BY l.tenLop", LopHoc.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public LopHoc findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(LopHoc.class, id);
        } finally {
            em.close();
        }
    }

    public void save(LopHoc lopHoc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lopHoc);
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

    public void update(LopHoc lopHoc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(lopHoc);
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
            LopHoc lop = em.find(LopHoc.class, id);
            if (lop != null) {
                em.remove(lop);
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

    public long countSinhVienByLop(Integer lopHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM SinhVien s WHERE s.lopHoc.id = :lopId", Long.class)
                    .setParameter("lopId", lopHocId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
