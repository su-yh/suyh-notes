# MinIO RELEASE\.2025\-04\-22 Ubuntu 系统安装部署手册

**版本说明**：MinIO RELEASE\.2025‑04‑22T22‑12‑29Z

**适配系统**：Ubuntu 20\.04 / 22\.04 \(x86\_64\)

**部署模式**：单机单盘 systemd 常驻服务（非 Docker）

**适用场景**：内网测试、生产单机对象存储服务

## 一、环境前置要求

- 操作系统：Ubuntu 20\.04、22\.04 64位系统

- 端口要求：放行 9000（S3 API端口）、9001（Web控制台端口）

- 磁盘要求：建议独立数据盘挂载，推荐 XFS 文件系统，禁止存储在系统盘

- 依赖说明：MinIO 为静态编译二进制文件，无需额外安装系统依赖

- 运行用户：禁止 root 运行，需创建专属普通业务用户

### 1\.1 关闭 Swap（生产环境必做）

```bash
sudo swapoff -a
sudo sed -i '/swap/s/^/#/' /etc/fstab
```

### 1\.2 防火墙放行端口

```bash
sudo ufw allow 9000/tcp
sudo ufw allow 9001/tcp
sudo ufw reload
```

## 二、创建运行用户与目录

创建专属 MinIO 运行用户、程序目录、数据存储目录

```bash
# 创建无登录权限的 minio 用户
sudo useradd -m -s /sbin/nologin minio

# 创建程序目录、数据目录
sudo mkdir -p /opt/minio/bin
sudo mkdir -p /data/minio/data

# 授权目录归属
sudo chown -R minio:minio /opt/minio
sudo chown -R minio:minio /data/minio/data
```

**注意**：/data/minio/data 为数据存储路径，生产环境请替换为独立挂载磁盘路径

## 三、下载指定版本 MinIO 程序

本次固定安装版本：RELEASE\.2025\-04\-22T22\-12\-29Z

```bash
cd /opt/minio/bin
# 下载官方指定版本二进制包
sudo wget https://dl.min.io/server/minio/release/linux-amd64/minio.RELEASE.2025-04-22T22-12-29Z

# 重命名为 minio 可执行文件
sudo mv minio.RELEASE.2025-04-22T22-12-29Z minio

# 添加执行权限并修改归属
sudo chmod +x minio
sudo chown minio:minio minio

# 校验版本
sudo -u minio ./minio --version
```

正常输出：`minio version RELEASE.2025‑04‑22T22‑12‑29Z`

## 四、配置 MinIO 环境变量

创建专属配置文件，统一管理账号、端口、路径等核心参数

```bash
sudo tee /opt/minio/minio.env > /dev/null <<'EOF'
# 管理员账号密码（生产务必修改强密码）
MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=Minio@2025

# 服务监听端口
MINIO_ADDRESS=:9000
MINIO_CONSOLE_ADDRESS=:9001

# 数据存储目录
MINIO_VOLUMES="/data/minio/data"

# 日志级别：warn/info/debug
MINIO_LOG_LEVEL=info

# 关闭自动HTTPS跳转（内网环境）
MINIO_BROWSER_REDIRECT_URL=""

# 证书密码置空
MINIO_CERT_PASSWD=
EOF
```

配置文件权限加固（防止密码泄露）

```bash
sudo chown minio:minio /opt/minio/minio.env
sudo chmod 600 /opt/minio/minio.env
```

## 五、配置 Systemd 系统服务

创建系统常驻服务，实现开机自启、异常自动重启

```bash
sudo tee /etc/systemd/system/minio.service > /dev/null <<'EOF'
[Unit]
Description=MinIO Object Storage
Documentation=https://docs.min.io
Wants=network-online.target
After=network-online.target

[Service]
User=minio
Group=minio
EnvironmentFile=/opt/minio/minio.env
ExecStart=/opt/minio/bin/minio server $MINIO_VOLUMES
Restart=on-failure
RestartSec=5
LimitNOFILE=65536

# 安全权限限制
PrivateTmp=true
ProtectSystem=strict
ReadWritePaths=/data/minio/data
ReadWritePaths=/opt/minio

[Install]
WantedBy=multi-user.target
EOF
```

## 六、启动 MinIO 服务

```bash
# 重载系统服务配置
sudo systemctl daemon-reload

# 设置开机自启
sudo systemctl enable minio

# 启动服务
sudo systemctl start minio

# 查看服务运行状态
sudo systemctl status minio
```

### 6\.1 查看实时运行日志

```bash
journalctl -u minio -f
```

### 6\.2 访问地址

- Web 管理控制台：`http://服务器IP:9001`

- S3 API 接口地址：`http://服务器IP:9000`

## 七、安装 MinIO 客户端 mc（可选）

命令行管理工具，用于快速操作存储桶、文件、权限

```bash
cd /opt/minio/bin
sudo wget https://dl.min.io/client/mc/release/linux-amd64/mc
sudo chmod +x mc
sudo chown minio:minio mc
sudo ln -s /opt/minio/bin/mc /usr/local/bin/mc

# 配置本地服务别名
mc alias set local http://127.0.0.1:9000 minioadmin Minio@2025

# 测试连通性
mc ls local
```

## 八、常用运维命令

### 8\.1 服务启停重启

```bash
sudo systemctl stop minio
sudo systemctl restart minio
```

### 8\.2 修改管理员账号密码

编辑配置文件修改 `MINIO_ROOT_USER` 和 `MINIO_ROOT_PASSWORD`，重启服务生效

```bash
sudo vim /opt/minio/minio.env
sudo systemctl restart minio
```

### 8\.3 版本升级（同架构平滑升级）

1. 停止 MinIO 服务：`sudo systemctl stop minio`

2. 备份旧的 minio 二进制文件

3. 下载新版本替换 /opt/minio/bin/minio

4. 添加执行权限，启动服务即可

**说明**：升级仅替换程序文件，不会影响原有业务数据

## 九、HTTPS 证书配置（生产可选）

内网环境可忽略，公网部署建议配置 HTTPS

```bash
# 创建证书目录
sudo mkdir -p /opt/minio/certs
sudo chown minio:minio /opt/minio/certs
sudo chmod 700 /opt/minio/certs
```

将证书 `public.crt`、私钥 `private.key` 放入目录，重启 MinIO 自动启用 HTTPS

## 十、故障排查常见问题

- 服务启动失败：优先执行 `journalctl -u minio -f` 查看报错日志

- 权限报错：检查数据目录、程序目录归属是否为 minio:minio

- 端口占用：执行 `ss -tlnp | grep 900` 检查端口占用情况

- 配置报错：minio\.env 文件权限必须为 600，权限过大会导致服务启动失败

- 版本适配：2025\-04\-22 新版本仅支持 env 变量配置，不兼容旧版启动参数

- 写入失败：检查数据盘磁盘空间是否充足

## 十一、安全注意事项

1. 部署完成后立即修改默认账号密码，使用高强度复杂密码

2. 禁止直接公网暴露 9000/9001 端口，生产建议配置 Nginx 反向代理

3. 严格管控 minio\.env 文件权限，避免密钥泄露

4. 生产环境优先使用 XFS 文件系统，不推荐 EXT4

5. 定期备份存储桶配置与核心业务数据，做好容灾方案

> （注：部分内容可能由 AI 生成）
