-- ============================================
-- Lab 09 JPA Repository - MySQL Schema
-- Run this script to create database manually
-- ============================================

-- Tạo database
CREATE DATABASE IF NOT EXISTS lab09_jpa 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE lab09_jpa;

-- ============================================
-- Table: lop_hoc
-- ============================================
CREATE TABLE IF NOT EXISTS lop_hoc (
    ma_lop VARCHAR(20) PRIMARY KEY,
    ten_lop VARCHAR(100),
    giao_vien VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: mon_hoc
-- ============================================
CREATE TABLE IF NOT EXISTS mon_hoc (
    ma_mon VARCHAR(20) PRIMARY KEY,
    ten_mon VARCHAR(100),
    so_tin_chi INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: sinh_vien
-- ============================================
CREATE TABLE IF NOT EXISTS sinh_vien (
    ma_sv VARCHAR(20) PRIMARY KEY,
    ho_ten VARCHAR(100),
    email VARCHAR(100),
    lop VARCHAR(20),
    ngay_sinh DATE,
    ma_lop_hoc VARCHAR(20),
    FOREIGN KEY (ma_lop_hoc) REFERENCES lop_hoc(ma_lop) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: diem
-- ============================================
CREATE TABLE IF NOT EXISTS diem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_sv VARCHAR(20),
    ma_mon VARCHAR(20),
    diem_cc FLOAT,
    diem_gk FLOAT,
    diem_ck FLOAT,
    FOREIGN KEY (ma_sv) REFERENCES sinh_vien(ma_sv) ON DELETE CASCADE,
    FOREIGN KEY (ma_mon) REFERENCES mon_hoc(ma_mon) ON DELETE CASCADE,
    UNIQUE KEY unique_score (ma_sv, ma_mon)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: role
-- ============================================
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ten_role VARCHAR(50) UNIQUE NOT NULL,
    mo_ta VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: users
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    ho_ten VARCHAR(100),
    email VARCHAR(100),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Dữ liệu mẫu (sample data)
-- ============================================

-- Roles
INSERT IGNORE INTO role (ten_role, mo_ta) VALUES 
('ADMIN', 'Quan tri he thong'),
('TEACHER', 'Giao vien'),
('USER', 'Nguoi dung thong thuong');

-- LopHoc
INSERT IGNORE INTO lop_hoc (ma_lop, ten_lop, giao_vien) VALUES 
('CNTT1', 'Cong nghe thong tin 1', 'Nguyen Van A'),
('CNTT2', 'Cong nghe thong tin 2', 'Tran Thi B'),
('DTVT1', 'Dien tu vien thong 1', 'Le Van C');

-- MonHoc
INSERT IGNORE INTO mon_hoc (ma_mon, ten_mon, so_tin_chi) VALUES 
('IT3242', 'Cong nghe Java', 3),
('IT3201', 'Co so du lieu', 4),
('IT3101', 'Lap trinh Web', 3),
('IT2101', 'Cau truc du lieu', 3);

-- Users (password: 123456 - bcrypt hash)
INSERT IGNORE INTO users (username, password, ho_ten, email, role_id) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye1JqCGBHVfLJwLUlHkCQ1lLwI4VvC1yW', 'Quan tri vien', 'admin@eaut.edu.vn', 1),
('user', '$2a$10$N9qo8uLOickgx2ZMRZoMye1JqCGBHVfLJwLUlHkCQ1lLwI4VvC1yW', 'Nguoi dung', 'user@eaut.edu.vn', 3);

-- SinhVien
INSERT IGNORE INTO sinh_vien (ma_sv, ho_ten, email, lop, ngay_sinh, ma_lop_hoc) VALUES 
('SV001', 'Nguyen Van A', 'nva@eaut.edu.vn', 'CNTT1', '2003-05-15', 'CNTT1'),
('SV002', 'Tran Thi B', 'ttb@eaut.edu.vn', 'CNTT1', '2003-08-20', 'CNTT1'),
('SV003', 'Le Van C', 'lvc@eaut.edu.vn', 'CNTT2', '2004-01-10', 'CNTT2'),
('SV004', 'Pham Thi D', 'ptd@eaut.edu.vn', 'CNTT2', '2003-03-25', 'CNTT2'),
('SV005', 'Hoang Van E', 'hve@eaut.edu.vn', 'DTVT1', '2004-07-30', 'DTVT1');
