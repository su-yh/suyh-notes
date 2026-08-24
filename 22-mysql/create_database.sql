

-- 1. 创建数据库
CREATE DATABASE `medcase-test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 创建用户，允许从任意地址 % 连接，密码 medcase-test
CREATE USER 'medcase-test'@'%' IDENTIFIED BY 'medcase-test';

-- 3. 授予该用户对 medcase‑test 库全部权限
GRANT ALL PRIVILEGES ON `medcase-test`.* TO 'medcase-test'@'%';

-- 4. 刷新权限，立即生效
FLUSH PRIVILEGES;
