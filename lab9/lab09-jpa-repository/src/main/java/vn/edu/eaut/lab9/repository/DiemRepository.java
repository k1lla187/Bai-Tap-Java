package vn.edu.eaut.lab9.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import vn.edu.eaut.lab9.config.JPAUtil;
import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.model.MonHoc;
import java.math.BigDecimal;
import java.util.List;

/**
 * Repository class for Diem entity CRUD operations
 */
public class DiemRepository {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Diem> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d ORDER BY d.id DESC", Diem.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Diem findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Diem.class, id);
        } finally {
            em.close();
        }
    }

    public List<Diem> findBySinhVienId(Integer sinhVienId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d WHERE d.sinhVien.id = :svId ORDER BY d.id DESC", Diem.class)
                    .setParameter("svId", sinhVienId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Diem> findByMonHocId(Integer monHocId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Diem d WHERE d.monHoc.id = :mhId ORDER BY d.id DESC", Diem.class)
                    .setParameter("mhId", monHocId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Save Diem and calculate final grade
     */
    public void save(Diem diem) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            diem.tinhDiemTongKet();
            em.persist(diem);
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
     * Update Diem and recalculate final grade
     */
    public void update(Diem diem) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            diem.tinhDiemTongKet();
            em.merge(diem);
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
            Diem d = em.find(Diem.class, id);
            if (d != null) {
                em.remove(d);
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

    public void saveDiemVaDiemMacDinh(SinhVien sinhVien, MonHoc monHoc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Persist sinhVien
            if (sinhVien.getId() == null) {
                em.persist(sinhVien);
            } else {
                em.merge(sinhVien);
            }
            
            // Tao diem mac dinh voi diem = 0
            Diem diem = new Diem(sinhVien, monHoc);
            diem.setDiemGiuaKy(BigDecimal.ZERO);
            diem.setDiemCuoiKy(BigDecimal.ZERO);
            diem.setDiemTongKet(BigDecimal.ZERO);
            diem.setXepLoai("Chua co diem");
            em.persist(diem);
            
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
}
