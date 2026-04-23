







### 启动与停止

```shell
sudo systemctl start mysql    # 启动
sudo systemctl stop mysql     # 停止
sudo systemctl restart mysql  # 重启
sudo systemctl enable mysql   # 开机自启
sudo systemctl disable mysql  # 取消开机自启
```



### 创建数据库

```sql
-- 创建数据库，库名为：'suyh_flink_metric'
CREATE DATABASE suyh_flink_metric DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- 创建一个Mysq 用户，用户名和密码都是：'suyh_flink_metric'
-- 同时允许远程连接并访问
CREATE USER 'suyh_flink_metric'@'%' IDENTIFIED BY 'suyh_flink_metric';

-- 绑定该用户，只允许它访问该数据库
GRANT ALL PRIVILEGES ON suyh_flink_metric.* TO 'suyh_flink_metric'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
```





### 安装

```shell

# 初始化系统
sudo apt update
sudo apt upgrade -y

# 安装服务（自动含客户端）
sudo apt install -y mysql-server

```



#### 查看版本号

```shell
# 进入到mysql 控制台
sudo mysql
```

使用SQL查询

```sql
SELECT VERSION();
-- exit 退出mysql 控制台
exit; 
```



```TXT
mysql> SELECT VERSION();
+-------------------------+
| VERSION()               |
+-------------------------+
| 8.0.45-0ubuntu0.24.04.1 |
+-------------------------+
1 row in set (0.00 sec)

```







### 安全初始化（必做）

```shell
# 安全初始化（必做）
sudo mysql_secure_installation
```



#### 是否需要强制密码的强度

```txt
root@iZwz9csjm93ya173qq2wf8Z:~# sudo mysql_secure_installation

Securing the MySQL server deployment.

Connecting to MySQL using a blank password.

VALIDATE PASSWORD COMPONENT can be used to test passwords
and improve security. It checks the strength of password
and allows the users to set only those passwords which are
secure enough. Would you like to setup VALIDATE PASSWORD component?

Press y|Y for Yes, any other key for No: 
```

- yes 强制超级复杂 密码
- no 不强制密码强度





#### 移除匿名用户

```txt
Skipping password set for root as authentication with auth_socket is used by default.
If you would like to use password authentication instead, this can be done with the "ALTER_USER" command.
See https://dev.mysql.com/doc/refman/8.0/en/alter-user.html#alter-user-password-management for more information.

By default, a MySQL installation has an anonymous user,
allowing anyone to log into MySQL without having to have
a user account created for them. This is intended only for
testing, and to make the installation go a bit smoother.
You should remove them before moving into a production
environment.

Remove anonymous users? (Press y|Y for Yes, any other key for No) : 

```

输入y

> Remove anonymous users? (Press y|Y for Yes, any other key for No) : y

#### 控制root 远程访问

```txt
Normally, root should only be allowed to connect from
'localhost'. This ensures that someone cannot guess at
the root password from the network.

Disallow root login remotely? (Press y|Y for Yes, any other key for No) : 
```

输入y

> Disallow root login remotely? (Press y|Y for Yes, any other key for No) :  y

#### 删除 test 数据库

```txt
By default, MySQL comes with a database named 'test' that
anyone can access. This is also intended only for testing,
and should be removed before moving into a production
environment.


Remove test database and access to it? (Press y|Y for Yes, any other key for No) :
```

输入y

> Remove test database and access to it? (Press y|Y for Yes, any other key for No) : y

#### 重新加载配置

```txt
Reload privilege tables now? (Press y|Y for Yes, any other key for No) : 
```

输入y

> Reload privilege tables now? (Press y|Y for Yes, any other key for No) :  y





### 为root 用户设置密码

root 免密登录

```shell
# 通过命令行，免密登录mysql，使用root 权限
sudo mysql
```

mysql 控制台

```sql
mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
mysql> FLUSH PRIVILEGES;
mysql> exit;
```



### 使用root 密码登录

通过前面我们为root 设置了密码，在这以后，我们就无法无密码登录了。

```shell
mysql -u root -p'root'
```





### 修改监听IP

`sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf`

找到：`bind-address            = 127.0.0.1`

修改为：`bind-address            = 0.0.0.0`