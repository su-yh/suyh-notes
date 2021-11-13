-- oracle 中的主键

-- 建表时指定主键
    CREATE TABLE Student(
        id NUMBER PRIMARY KEY,
        name VARCHAR2(10),
        school VARCHAR2(10));

-- 建好表之后添加主键
    ALTER TABLE table_name ADD CONSTRAINT constraint_name PRIMARY KEY (col1, col2,...coln);  

-- 删除主键
    ALTER TABLE table_name DROP CONSTRAINT constraint_name;

-- 禁用主键
    ALTER TABLE table_name DISABLE CONSTRAINT constraint_name;  
    alter table ECS_STORE.TF_B_AIR_CONFIG disable constraint TF_B_AIR_CONFIG_PK ;  

-- 启用主键
    ALTER TABLE table_name ENABLE CONSTRAINT constraint_name;  
    alter table ECS_STORE.TF_B_AIR_CONFIG enable constraint TF_B_AIR_CONFIG_PK ;  



