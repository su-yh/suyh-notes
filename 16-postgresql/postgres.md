



## 命令

```shell
psql -U postgres -d postgres -p 5432

# 使用-D 来指定数据目录，默认找环境变量：PGDATA
pg_ctl start -D /usr/local/pg12.2/data

# 添加用户组以及用户
groupadd postgres
useradd -g postgres postgres
passwd postgres # 给用户设置密码


# 将目录的属主改为postgress
chown postgres:postgres /usr/local/postgres12.2/
```





## 环境准备

> 环境准备
>
> 配置文件：`.bash_profile`

```shell
export PGPORT=5432
export PG_HOME=/usr/local/pg12.2
export PATH=$PA_HOME/bin:$PATH
export PGDATA=$PG_HOME/data
export LD_LIBRARY_PATH=$PG_HOME/lib
export LANG=en_US.utf8
```

![image-20240416143355426](postgres.assets/image-20240416143355426.png)



## 内核参数配置



![image-20240416143425460](postgres.assets/image-20240416143425460.png)

## 安装方式

![image-20240416164015016](postgres.assets/image-20240416164015016.png)

## 源码安装依赖包

```shell
# 检查某个软件是否安装了
rpm -qa gcc
```



![image-20240416174303889](postgres.assets/image-20240416174303889.png)

## 源代码安装

![image-20240416175209750](postgres.assets/image-20240416175209750.png)

配置可选项

![image-20240416175305617](postgres.assets/image-20240416175305617.png)

编译可选项

![image-20240416175426497](postgres.assets/image-20240416175426497.png)



## 创建数据库集簇

![image-20240416182055865](postgres.assets/image-20240416182055865.png)

```txt
参数 -W                 可以为 postgres 超级管理员指定密码
参数 --data-checksums   主要是做主从复制时需要
```

