-- ============================================
-- Script tạo user và database cho Lab 09 JPA
-- ============================================

-- Tạo database
CREATE DATABASE IF NOT EXISTS lab09_jpa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================
-- User chỉ đọc (không thể thêm, sửa, xóa)
-- ============================================
DROP USER IF EXISTS 'lab09user'@'localhost';
CREATE USER 'lab09user'@'localhost' IDENTIFIED BY '';
GRANT SELECT ON lab09_jpa.* TO 'lab09user'@'localhost';

-- ============================================
-- Admin toàn quyền
-- ============================================
DROP USER IF EXISTS 'lab09admin'@'localhost';
CREATE USER 'lab09admin'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON lab09_jpa.* TO 'lab09admin'@'localhost';

-- Áp dụng thay đổi
FLUSH PRIVILEGES;

-- Kiểm tra quyền
SHOW GRANTS FOR 'lab09user'@'localhost';
SHOW GRANTS FOR 'lab09admin'@'localhost';
