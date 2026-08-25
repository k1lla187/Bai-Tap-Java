-- ============================================
-- Lab 10 - MySQL Setup Script
-- Run this as MySQL root to create DB and users
-- ============================================

CREATE DATABASE IF NOT EXISTS lab10_jpa
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Read-only user (for app use)
DROP USER IF EXISTS 'lab10user'@'localhost';
CREATE USER 'lab10user'@'localhost' IDENTIFIED BY '';
GRANT SELECT ON lab10_jpa.* TO 'lab10user'@'localhost';

-- Admin user (Hibernate create-drop + seed)
DROP USER IF EXISTS 'lab10admin'@'localhost';
CREATE USER 'lab10admin'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON lab10_jpa.* TO 'lab10admin'@'localhost';

FLUSH PRIVILEGES;
