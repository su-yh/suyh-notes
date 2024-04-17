





## 使用yum 安装postgresql

### 安装系统自带yum 对应的默认版本

> 参考博客：`https://blog.csdn.net/feinifi/article/details/96474115`
>
> 相关命令执行顺序
>
> ```shell
> # 在CentOS7.9 中默认安装的pgsql 的版本是: 9.2.24
> yum install postgresql-server -y
> ```
>

### 安装自定义版本

- 下载官方提供的yum 源

  ```shell
  yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
  ```

- 安装对应版本的postgresql

  ```shell
  # 安装13 版本
  yum install -y postgresql13-server
  # 安装14 版本
  yum install -y postgresql14-server
  # 安装15 版本
  yum install -y postgresql15-server
  # 安装16 版本
  yum install -y postgresql16-server
  
  # 安装v变量对应的版本
  yum install -y postgresql${v}-server
  ```

- 其他

## postgresql 的初始化

```shell
# 查看版本
# 同时还会生成postgres 用户，在postgresql 启动之后，在本机只能切换到postgres 用户下才能通过psql 访问。
psql --version

which psql
which postgresql-setup

# 安装完成之后，不能直接启动数据库，需要先初始化数据库集簇。
# 初始化完会生成postgresql 相关的配置文件和数据库文件。
# 它们会存放在路径 /var/lib/pgsql/data 下。
postgresql-setup initdb

# 启动数据库
# 默认是只监听本地IP，不能进行远程访问的。
# 若要配置远程访问需要修改相关的配置文件：postgresql.conf 和 pg_hdb.conf
service postgresql start


# 登录需要切换到postgres 用户才可以
# 所以这里先给postgres 用户设置密码
passwd postgres

# 切换到postgres 用户
su - postgres

# 默认情况下，只监听了localhost 端口，远程连接不了。
# 同时远程连接还需要其他配置
# 具体参考：postgres.conf 和pg_hba.conf 文件的配置修改
```

