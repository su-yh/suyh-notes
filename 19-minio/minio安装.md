



## 下载minio 服务器并做前置准备

若要下载其他版本，可到 [MinIO 官方下载页面](https://min.io/download) 查找适配的下载链接。

```shell
# 下载文件
wget https://dl.min.io/server/minio/release/linux-amd64/minio
# 执行权限
chmod +x minio
# 把 MinIO 二进制文件移到系统路径下，这样就能在任何地方使用它
sudo mv minio /usr/local/bin/

# 创建数据目录
sudo mkdir -p /data/minio
# 并将该目录属主修改为前登录用户
sudo chown -R $USER:$USER /data/minio
```

## 配置系统服务

### minio 的配置文件

```shell
sudo nano /etc/default/minio
```

内容如下：

```properties
# 指定数据存储目录(注意：这个目录要存在且拥有相对应的权限)
# MINIO_VOLUMES="/data/minio"

# 监听端口
# --address：是指定api的端口；--console-address：是指定控制台端口
# MINIO_OPTS="--address :9000 --console-address :9090"

# 老版本使用MINIO_ACCESS_KEY/MINIO_SECRET_KEY，新版本已不建议使用
# Access key (账号)
# MINIO_ACCESS_KEY="minioadmin"
# Secret key (密码)
# MINIO_SECRET_KEY="minioadmin"

# 新版本使用；指定默认的用户名和密码，其中用户名必须大于3个字母，密码必须大于8个字母，否则不能启动
MINIO_ROOT_USER="minioadmin"
MINIO_ROOT_PASSWORD="minioadmin"

# 区域值，标准格式是“国家-区域-编号”，
MINIO_REGION="cn-shenzhen-1"

# 域名
# MINIO_DOMAIN=minio.your_domain.com
```



### 系统服务配置文件

```shell
sudo nano /etc/systemd/system/minio.service
```

该文件内容如下：

==要注意里面的User 和Group 需要按实际情况进行修改==

```properties
[Unit]
Description=MinIO
Documentation=https://docs.min.io
Wants=network-online.target
After=network-online.target

[Service]
# 指向minio的配置文件
EnvironmentFile=-/etc/default/minio
# --console-address ":9090"   是指定web-ui 控制台的端口
ExecStart=/usr/local/bin/minio server /data/minio --console-address ":9090"
Restart=always
RestartSec=5
WorkingDirectory=/data/minio
# 这里的Linux用户和组需要按实际情况进行修改
User=minio-user
Group=minio-user
PermissionsStartOnly=true
AmbientCapabilities=CAP_NET_BIND_SERVICE

[Install]
WantedBy=multi-user.target
```



重新加载systemd 管理器配置

```shell
sudo systemctl daemon-reload
```

启动停止与开机自启

```shell
sudo systemctl start minio
sudo systemctl stop minio
sudo systemctl restart minio
sudo systemctl status minio

# 开机自启
sudo systemctl enable minio
```

## 浏览器访问

在浏览器中打开 `http://your_server_ip:9000`（`your_server_ip` 是你的服务器 IP 地址），使用之前设置的用户名和密码登录 MinIO 控制台。如果能成功登录，就表明 MinIO 已成功安装并运行。

> 用户名和密码就是前面在文件中配置的：`/etc/default/minio`
>
> ==MINIO_ROOT_USER==   ==MINIO_ROOT_PASSWORD==