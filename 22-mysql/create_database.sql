

-- 1. 创建数据库
CREATE DATABASE `suyh_medcase_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 创建用户，允许从任意地址 % 连接，密码 
CREATE USER 'suyh_medcase_test'@'%' IDENTIFIED BY 'suyh_medcase_test';

-- 3. 授予该用户对数据库全部权限
GRANT ALL PRIVILEGES ON `suyh_medcase_test`.* TO 'suyh_medcase_test'@'%';

-- 4. 刷新权限，立即生效
FLUSH PRIVILEGES;


-- 回滚
DROP USER 'suyh_medcase_test'@'%';
DROP DATABASE `suyh_medcase_test`;
