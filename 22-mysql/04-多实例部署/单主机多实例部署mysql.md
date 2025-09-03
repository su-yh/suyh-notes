

## 说明

ubuntu 系统 24.4 

强制安装==5.7.33== 版本的mysql

部署三个mysql 服务，端口分别是3306、3307、3308



## mysql 用户



```shell
# 创建用户
adduser mysql
# 添加sudo 权限
sudo echo "mysql ALL=(ALL:ALL) ALL" > /etc/sudoers.d/mysql
```



==后面的操作都可以切换到mysql 用户操作==



## 下载

==这里面会有需要输入密码的地方==

包括sudo 密码，以及新安装的mysql root 的密码

```shell
mkdir installer
cd installer 
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-common_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-server_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-server_5.7.33-1ubuntu18.04_amd64.deb

echo "deb http://archive.ubuntu.com/ubuntu focal main universe" | sudo tee -a /etc/apt/sources.list
sudo apt update
# MySQL 5.7的核心依赖
sudo apt install -y libaio1 libmecab2  

# 4. 强制安装本地5.7.33包（--force-depends忽略依赖检查，避免被apt替换）
sudo dpkg -i --force-depends *.deb

# 5. 停止默认服务（如果已启动），避免冲突
sudo systemctl stop mysql || true

# 6. 禁止mysql 默认服务的开机启动
sudo systemctl disable mysql

# 7. 查看是否禁止成功
sudo systemctl is-enabled mysql

cd ~

```



## 配置文件

准备好三个不同的配置文件

```shell
mkdir ~/3306 ~/3307 ~/3308
touch ~/3306/my.cnf ~/3307/my.cnf ~/3308/my.cnf
```

```shell
# 三个文件一起编辑
vim -p ~/330*/my.cnf   
```



### 3306

```shell
vim ~/3306/my.cnf
```

```ini
[client]
port = 3306  # 与实例端口一致
socket = /var/lib/mysql/3306/mysql.sock  # 与 [mysqld] 中的 socket 一致

[mysqld]
port = 3306
datadir=/var/lib/mysql/3306
socket=/var/lib/mysql/3306/mysql.sock
pid-file = /var/lib/mysql/3306/mysqld.pid
# 加载 auth_socket 插件，用于配置免密登录用户，用作停机服务功能
plugin-load-add = auth_socket.so

# 慢查询日志核心配置
slow_query_log = ON  # 开启慢查询日志
slow_query_log_file = /var/lib/mysql/3306/mysql-slow.log  # 3306 实例的日志文件（路径自定义，需唯一）
long_query_time = 1  # 慢查询阈值（建议设为 1-3 秒，根据业务调整）
log_queries_not_using_indexes = ON  # 记录未使用索引的查询
log_output = FILE  # 日志输出到文件（默认是 FILE，也可加 TABLE 表示同时存入 mysql.slow_log 表）

# binlog 日志开启
# 1. 开启 binlog（指定基础路径，避免默认路径混乱）
log_bin = /var/lib/mysql/3306/mysql-bin
# 2. 实例唯一标识（主从复制必须，单实例也需配置）
server_id = 6
# 3. binlog 格式（生产推荐 ROW，确保主从一致）
binlog_format = ROW
# 4. 自动清理过期 binlog（保留 7 天，8.0+ 用 binlog_expire_logs_seconds=604800）
expire_logs_days = 7
# 5. 单个 binlog 最大体积（1G，避免文件过大）
max_binlog_size = 1G
# 6. （可选）记录 binlog 时包含库名（主从复制跨库场景有用）
# binlog_do_db = test_db  # 仅记录 test_db 库（不推荐，除非明确需求）
# 忽略系统数据库
binlog_ignore_db = mysql

[mysqldump]
quick
max_allowed_packet = 500M

```



### 3307

```shell
vim ~/3307/my.cnf
```



```ini
[client]
port = 3307  # 与实例端口一致
socket = /var/lib/mysql/3307/mysql.sock  # 与 [mysqld] 中的 socket 一致

[mysqld]
port = 3307
datadir=/var/lib/mysql/3307
socket=/var/lib/mysql/3307/mysql.sock
pid-file = /var/lib/mysql/3307/mysqld.pid
# 加载 auth_socket 插件，用于配置免密登录用户，用作停机服务功能
plugin-load-add = auth_socket.so

# 慢查询日志核心配置
slow_query_log = ON  # 开启慢查询日志
slow_query_log_file = /var/lib/mysql/3307/mysql-slow.log  # 3307 实例的日志文件（路径自定义，需唯一）
long_query_time = 1  # 慢查询阈值（建议设为 1-3 秒，根据业务调整）
log_queries_not_using_indexes = ON  # 记录未使用索引的查询
log_output = FILE  # 日志输出到文件（默认是 FILE，也可加 TABLE 表示同时存入 mysql.slow_log 表）

# binlog 日志开启
# 1. 开启 binlog（指定基础路径，避免默认路径混乱）
log_bin = /var/lib/mysql/3307/mysql-bin
# 2. 实例唯一标识（主从复制必须，单实例也需配置）
server_id = 7
# 3. binlog 格式（生产推荐 ROW，确保主从一致）
binlog_format = ROW
# 4. 自动清理过期 binlog（保留 7 天，8.0+ 用 binlog_expire_logs_seconds=604800）
expire_logs_days = 7
# 5. 单个 binlog 最大体积（1G，避免文件过大）
max_binlog_size = 1G
# 6. （可选）记录 binlog 时包含库名（主从复制跨库场景有用）
# binlog_do_db = test_db  # 仅记录 test_db 库（不推荐，除非明确需求）
# 忽略系统数据库
binlog_ignore_db = mysql

[mysqldump]
quick
max_allowed_packet = 500M

```



### 3308

```shell
vim ~/3308/my.cnf
```



```ini
[client]
port = 3308  # 与实例端口一致
socket = /var/lib/mysql/3308/mysql.sock  # 与 [mysqld] 中的 socket 一致

[mysqld]
port = 3308
datadir=/var/lib/mysql/3308
socket=/var/lib/mysql/3308/mysql.sock
pid-file = /var/lib/mysql/3308/mysqld.pid
# 加载 auth_socket 插件，用于配置免密登录用户，用作停机服务功能
plugin-load-add = auth_socket.so

# 慢查询日志核心配置
slow_query_log = ON  # 开启慢查询日志
slow_query_log_file = /var/lib/mysql/3308/mysql-slow.log  # 3308 实例的日志文件（路径自定义，需唯一）
long_query_time = 1  # 慢查询阈值（建议设为 1-3 秒，根据业务调整）
log_queries_not_using_indexes = ON  # 记录未使用索引的查询
log_output = FILE  # 日志输出到文件（默认是 FILE，也可加 TABLE 表示同时存入 mysql.slow_log 表）

# binlog 日志开启
# 1. 开启 binlog（指定基础路径，避免默认路径混乱）
log_bin = /var/lib/mysql/3308/mysql-bin
# 2. 实例唯一标识（主从复制必须，单实例也需配置）
server_id = 8
# 3. binlog 格式（生产推荐 ROW，确保主从一致）
binlog_format = ROW
# 4. 自动清理过期 binlog（保留 7 天，8.0+ 用 binlog_expire_logs_seconds=604800）
expire_logs_days = 7
# 5. 单个 binlog 最大体积（1G，避免文件过大）
max_binlog_size = 1G
# 6. （可选）记录 binlog 时包含库名（主从复制跨库场景有用）
# binlog_do_db = test_db  # 仅记录 test_db 库（不推荐，除非明确需求）
# 忽略系统数据库
binlog_ignore_db = mysql

[mysqldump]
quick
max_allowed_packet = 500M

```



## AppArmor 

打开文件在相应位置添加配置

```shell
sudo vim /etc/apparmor.d/usr.sbin.mysqld
```

```shell
  # 将如下行添加到文件中
  include <local/usr.sbin.mysqld>
```

```shell
sudo vim /etc/apparmor.d/local/usr.sbin.mysqld

# 添加如下配置
  /home/mysql/3306/my.cnf r,        # 允许读取配置文件
  # 数据目录
  /var/lib/mysql/3306/ r,
  /var/lib/mysql/3306/** rwk,

  /home/mysql/3307/my.cnf r,        # 允许读取配置文件
  # 数据目录
  /var/lib/mysql/3307/ r,
  /var/lib/mysql/3307/** rwk,

  /home/mysql/3308/my.cnf r,        # 允许读取配置文件
  # 数据目录
  /var/lib/mysql/3308/ r,
  /var/lib/mysql/3308/** rwk,
```



重新加载配置

```shell
# 重新加载 AppArmor 配置
sudo apparmor_parser -r /etc/apparmor.d/usr.sbin.mysqld
```

## 初始化mysql 服务

```shell
# 使用mysql 普通用户权限即可，同时可以从执行结果中看到一个临时密码   root@localhost: #tBrdGtsR7x,
mysqld --defaults-file=/home/mysql/3306/my.cnf --initialize --datadir=/var/lib/mysql/3306
mysqld --defaults-file=/home/mysql/3307/my.cnf --initialize --datadir=/var/lib/mysql/3307
mysqld --defaults-file=/home/mysql/3308/my.cnf --initialize --datadir=/var/lib/mysql/3308
```



### 启动服务

```shell
nohup mysqld --defaults-file=/home/mysql/3306/my.cnf >/dev/null 2>&1 --user=mysql &
nohup mysqld --defaults-file=/home/mysql/3307/my.cnf >/dev/null 2>&1 --user=mysql &
nohup mysqld --defaults-file=/home/mysql/3308/my.cnf >/dev/null 2>&1 --user=mysql &

# 查看是否正常运行
ps -ef | grep mysqld
```

### 修改密码

格式：mysqladmin --defaults-file=/路径/到/my.cnf -u root -p 旧密码 password 新密码

```shell
# 需要服务正在运行
# 将密码全部修改为root
mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u root -p'#tBrdGtsR7x,' password 'root'
mysqladmin --defaults-file=/home/mysql/3307/my.cnf -u root -p'5HK&0jrTpN6l' password 'root'
mysqladmin --defaults-file=/home/mysql/3308/my.cnf -u root -p'-qWeMdr9p6as' password 'root'
```

结果示例：

```txt
mysql@iZ0jl7dlasuhl8z6q1fmppZ:/etc/apparmor.d/local$ mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u root -p'#tBrdGtsR7x,' password 'root'
mysqladmin: [Warning] Using a password on the command line interface can be insecure.
Warning: Since password will be sent to server in plain text, use ssl connection to ensure password safety.

```



### 停止服务

==后面的配置开机启动，这里只是临使用==

```shell
# 需要时才调用
mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u root -p'root' shutdown
mysqladmin --defaults-file=/home/mysql/3307/my.cnf -u root -p'root' shutdown
mysqladmin --defaults-file=/home/mysql/3308/my.cnf -u root -p'root' shutdown
```

### 本机连接

==由于一台主机实例运行了多个mysql 实例，导致使用默认的mysql 客户端连接会有异常情况，所以需要使用特定的配置连接到指定的服务器==

```shell
# 需要先安装依赖
sudo apt-get install libtinfo5
# 使用3306 的配置，连接到3306 端口对应的mysql 服务实例
mysql --defaults-file=/home/mysql/3306/my.cnf -h 127.0.0.1 -u root -p
# 使用3307 的配置，连接到3307 端口对应的mysql 服务实例
mysql --defaults-file=/home/mysql/3307/my.cnf -h 127.0.0.1 -u root -p
# 使用3308 的配置，连接到3308 端口对应的mysql 服务实例
mysql --defaults-file=/home/mysql/3308/my.cnf -h 127.0.0.1 -u root -p
```



### 本机免密登录

如果提示：mysql: error while loading shared libraries: libtinfo.so.5: cannot open shared object file: No such file or directory

使用命令安装一下：`sudo apt-get install libtinfo5`

#### 3306

```shell
# 进入mysql 控制台
mysql --defaults-file=/home/mysql/3306/my.cnf -h 127.0.0.1 -u root -p
# 需要输入mysql root 用户的密码
```

```sql
-- 执行如下SQL
-- 这里创建的用户只能是 mysql 与ubuntu 系统用户名一致
-- 同时 auth_socket 插件需要已经加载
CREATE USER 'mysql'@'localhost' IDENTIFIED WITH auth_socket;
GRANT SHUTDOWN ON *.* TO 'mysql'@'localhost';
FLUSH PRIVILEGES;
```

```shell
# 验证
whoami  # 输出应为 mysql
# 测试mysql 用户免密登录
mysql --defaults-file=/home/mysql/3306/my.cnf -u mysql
# 测试mysql 用户停止服务
mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u mysql shutdown
```

#### 3307

```shell
# 进入mysql 控制台
mysql --defaults-file=/home/mysql/3307/my.cnf -h 127.0.0.1 -u root -p
# 需要输入mysql root 用户的密码
```

```sql
-- 执行如下SQL
-- 这里创建的用户只能是 mysql 与ubuntu 系统用户名一致
-- 同时 auth_socket 插件需要已经加载
CREATE USER 'mysql'@'localhost' IDENTIFIED WITH auth_socket;
GRANT SHUTDOWN ON *.* TO 'mysql'@'localhost';
FLUSH PRIVILEGES;
```

```shell
# 验证
whoami  # 输出应为 mysql
# 测试mysql 用户免密登录
mysql --defaults-file=/home/mysql/3307/my.cnf -u mysql
# 测试mysql 用户停止服务
mysqladmin --defaults-file=/home/mysql/3307/my.cnf -u mysql shutdown
```



#### 3308

```shell
# 进入mysql 控制台
mysql --defaults-file=/home/mysql/3308/my.cnf -h 127.0.0.1 -u root -p
# 需要输入mysql root 用户的密码
```

```sql
-- 执行如下SQL
-- 这里创建的用户只能是 mysql 与ubuntu 系统用户名一致
-- 同时 auth_socket 插件需要已经加载
CREATE USER 'mysql'@'localhost' IDENTIFIED WITH auth_socket;
GRANT SHUTDOWN ON *.* TO 'mysql'@'localhost';
FLUSH PRIVILEGES;
```

```shell
# 验证
whoami  # 输出应为 mysql
# 测试mysql 用户免密登录
mysql --defaults-file=/home/mysql/3308/my.cnf -u mysql
# 测试mysql 用户停止服务
mysqladmin --defaults-file=/home/mysql/3308/my.cnf -u mysql shutdown
```

### 远程连接

==配置允许root 用户远程连接==

#### 3306

这里仅以3306 为例

```shell
# 进入mysql 客户端
mysql --defaults-file=/home/mysql/3306/my.cnf -u root -p
```

```sql
-- 执行如下SQL
SELECT user, host, plugin FROM mysql.user WHERE user = 'root';
UPDATE mysql.user SET host = '%' WHERE user = 'root';
SELECT user, host, plugin FROM mysql.user WHERE user = 'root';
FLUSH PRIVILEGES;
```

```shell
mysql> SELECT user, host, plugin FROM mysql.user WHERE user = 'root';
+------+-----------+-----------------------+
| user | host      | plugin                |
+------+-----------+-----------------------+
| root | localhost | mysql_native_password |
+------+-----------+-----------------------+
1 row in set (0.01 sec)

mysql> UPDATE mysql.user SET host = '%' WHERE user = 'root';
Query OK, 1 row affected (0.01 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> SELECT user, host, plugin FROM mysql.user WHERE user = 'root';
+------+------+-----------------------+
| user | host | plugin                |
+------+------+-----------------------+
| root | %    | mysql_native_password |
+------+------+-----------------------+
1 row in set (0.00 sec)

mysql> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.01 sec)

mysql> 

```



## 配置systemd

### 3306

```shell
sudo vim /etc/systemd/system/mysql-3306.service
```

内容：

```ini
[Unit]
# 服务描述（显示在 systemctl status 等命令中）
Description=MySQL 3306 Instance
# 启动顺序：在 network.target（网络服务）之后启动
After=network.target

[Service]
# 运行用户（必须有实例目录的权限）
User=mysql
Group=mysql
# 启动命令（指定配置文件）
ExecStart=/usr/sbin/mysqld --defaults-file=/home/mysql/3306/my.cnf
# 启动/停止超时时间（600秒=10分钟，避免因初始化慢被 systemd 误判为启动失败）
TimeoutSec=600
# 限制服务可打开的最大文件描述符数量（5000，避免高并发时文件句柄耗尽）
LimitNOFILE = 5000
# 停止命令（通过配置文件连接并停止）
# ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u root -p shutdown
# 使用免密用户停机，这个用户必须跟前面的 User 一致
ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3306/my.cnf -u mysql shutdown
# 重启命令
ExecReload=/bin/kill -HUP $MAINPID
# 重启策略：仅在服务非正常退出（如崩溃）时自动重启
Restart=on-failure
# 例外情况：当退出码为 1 时，不自动重启（通常表示配置错误，需手动修复）
RestartPreventExitStatus=1
# 私有临时目录（避免权限问题）
PrivateTmp=true

[Install]
# 开机自启的目标级别：多用户模式（系统正常运行级别）
WantedBy=multi-user.target

```

```shell
# 上面的配置好了之后，重新加载 systemd 配置
sudo systemctl daemon-reload
# 测试配置是否正确并生效了？
sudo systemctl start mysql-3306
sudo systemctl status mysql-3306
# 随开机启动
sudo systemctl enable mysql-3306
# 验证是否配置成功：随开机启动
sudo systemctl is-enabled mysql-3306
```

### 3307

```shell
sudo vim /etc/systemd/system/mysql-3307.service
```

```ini
[Unit]
# 服务描述（显示在 systemctl status 等命令中）
Description=MySQL 3307 Instance
# 启动顺序：在 network.target（网络服务）之后启动
After=network.target

[Service]
# 运行用户（必须有实例目录的权限）
User=mysql
Group=mysql
# 启动命令（指定配置文件）
ExecStart=/usr/sbin/mysqld --defaults-file=/home/mysql/3307/my.cnf
# 启动/停止超时时间（600秒=10分钟，避免因初始化慢被 systemd 误判为启动失败）
TimeoutSec=600
# 限制服务可打开的最大文件描述符数量（5000，避免高并发时文件句柄耗尽）
LimitNOFILE = 5000
# 停止命令（通过配置文件连接并停止）
# ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3307/my.cnf -u root -p shutdown
# 使用免密用户停机，这个用户必须跟前面的 User 一致
ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3307/my.cnf -u mysql shutdown
# 重启命令
ExecReload=/bin/kill -HUP $MAINPID
# 重启策略：仅在服务非正常退出（如崩溃）时自动重启
Restart=on-failure
# 例外情况：当退出码为 1 时，不自动重启（通常表示配置错误，需手动修复）
RestartPreventExitStatus=1
# 私有临时目录（避免权限问题）
PrivateTmp=true

[Install]
# 开机自启的目标级别：多用户模式（系统正常运行级别）
WantedBy=multi-user.target

```

```shell
# 上面的配置好了之后，重新加载 systemd 配置
sudo systemctl daemon-reload
# 随开机启动
sudo systemctl enable mysql-3307
# 验证是否配置成功：随开机启动
sudo systemctl is-enabled mysql-3307
```

### 3308

```shell
sudo vim /etc/systemd/system/mysql-3308.service
```

```ini
[Unit]
# 服务描述（显示在 systemctl status 等命令中）
Description=MySQL 3308 Instance
# 启动顺序：在 network.target（网络服务）之后启动
After=network.target

[Service]
# 运行用户（必须有实例目录的权限）
User=mysql
Group=mysql
# 启动命令（指定配置文件）
ExecStart=/usr/sbin/mysqld --defaults-file=/home/mysql/3308/my.cnf
# 启动/停止超时时间（600秒=10分钟，避免因初始化慢被 systemd 误判为启动失败）
TimeoutSec=600
# 限制服务可打开的最大文件描述符数量（5000，避免高并发时文件句柄耗尽）
LimitNOFILE = 5000
# 停止命令（通过配置文件连接并停止）
# ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3308/my.cnf -u root -p shutdown
# 使用免密用户停机，这个用户必须跟前面的 User 一致
ExecStop=/usr/bin/mysqladmin --defaults-file=/home/mysql/3308/my.cnf -u mysql shutdown
# 重启命令
ExecReload=/bin/kill -HUP $MAINPID
# 重启策略：仅在服务非正常退出（如崩溃）时自动重启
Restart=on-failure
# 例外情况：当退出码为 1 时，不自动重启（通常表示配置错误，需手动修复）
RestartPreventExitStatus=1
# 私有临时目录（避免权限问题）
PrivateTmp=true

[Install]
# 开机自启的目标级别：多用户模式（系统正常运行级别）
WantedBy=multi-user.target

```

```shell
# 上面的配置好了之后，重新加载 systemd 配置
sudo systemctl daemon-reload
# 随开机启动
sudo systemctl enable mysql-3308
# 验证是否配置成功：随开机启动
sudo systemctl is-enabled mysql-3308
```





