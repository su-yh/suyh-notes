#!/bin/bash

# suyh - 下面的这些脚本是ai 给的，还没有经过验证，先保留在这里。

set -e

# 新MySQL实例配置
MYSQL_PORT=3307
MYSQL_SERVICE_NAME="mysql57"  # 独立服务名，避免冲突
MYSQL_BASE_DIR="/opt/mysql57"  # 独立安装目录
MYSQL_DATA_DIR="/var/lib/mysql57"  # 独立数据目录
MYSQL_CONF="/etc/mysql/mysql.conf.d/mysqld_$MYSQL_PORT.cnf"  # 独立配置文件

# 1. 检查端口是否被占用
if netstat -tulpn | grep -q ":$MYSQL_PORT "; then
    echo "❌ 错误：端口 $MYSQL_PORT 已被占用，请更换端口后重试"
    exit 1
fi

# 2. 创建工作目录
WORKDIR=/tmp/mysql57
mkdir -p $WORKDIR
cd $WORKDIR

echo "📦 下载 MySQL 5.7.33 .deb 包中..."

# 3. 下载 MySQL 5.7.33 所需的 .deb 包
# wget https://downloads.mysql.com/archives/get/p/23/file/mysql-common_5.7.33-1ubuntu18.04_amd64.deb
# wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-client_5.7.33-1ubuntu18.04_amd64.deb
# wget https://downloads.mysql.com/archives/get/p/23/file/mysql-client_5.7.33-1ubuntu18.04_amd64.deb
# wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-server_5.7.33-1ubuntu18.04_amd64.deb
# wget https://downloads.mysql.com/archives/get/p/23/file/mysql-server_5.7.33-1ubuntu18.04_amd64.deb

echo "✅ 下载完成，开始安装..."

echo "deb http://archive.ubuntu.com/ubuntu focal main universe" | sudo tee -a /etc/apt/sources.list
sudo apt update
sudo apt install -y libaio1 libmecab2  # MySQL 5.7的核心依赖

# 4. 强制安装本地5.7.33包（--force-depends忽略依赖检查，避免被apt替换）
sudo dpkg -i --force-depends *.deb

# 5. 停止默认服务（如果已启动），避免冲突
sudo systemctl stop mysql || true

# 6. 创建独立的数据目录和配置
echo "🔧 配置独立MySQL实例..."

# 创建数据目录并授权
sudo mkdir -p $MYSQL_DATA_DIR
sudo chown -R mysql:mysql $MYSQL_DATA_DIR

# 创建独立配置文件
sudo cp /etc/mysql/mysql.conf.d/mysqld.cnf $MYSQL_CONF
sudo sed -i "s/^port = 3306/port = $MYSQL_PORT/" $MYSQL_CONF
sudo sed -i "s|^datadir = /var/lib/mysql|datadir = $MYSQL_DATA_DIR|" $MYSQL_CONF
sudo sed -i "s|^socket = /var/run/mysqld/mysqld.sock|socket = /var/run/mysqld/mysqld_$MYSQL_PORT.sock|" $MYSQL_CONF
sudo sed -i "s|^log_error = /var/log/mysql/error.log|log_error = /var/log/mysql/error_$MYSQL_PORT.log|" $MYSQL_CONF

# 初始化数据目录
sudo mysqld --defaults-file=$MYSQL_CONF --initialize --user=mysql

# 7. 创建systemd服务配置
echo "🔧 创建独立服务 $MYSQL_SERVICE_NAME..."

sudo tee /etc/systemd/system/$MYSQL_SERVICE_NAME.service <<EOF
[Unit]
Description=MySQL 5.7.33 database server (port $MYSQL_PORT)
After=network.target

[Service]
User=mysql
Group=mysql
ExecStart=/usr/sbin/mysqld --defaults-file=$MYSQL_CONF
Restart=on-failure
RuntimeDirectory=mysqld
RuntimeDirectoryMode=755

[Install]
WantedBy=multi-user.target
EOF

# 8. 重新加载服务配置并启动新实例
sudo systemctl daemon-reload
sudo systemctl start $MYSQL_SERVICE_NAME
sudo systemctl enable $MYSQL_SERVICE_NAME  # 设置开机启动

# 9. 检查服务状态
echo "🔍 检查 $MYSQL_SERVICE_NAME 服务状态..."
sudo systemctl status $MYSQL_SERVICE_NAME --no-pager

# 10. 显示临时密码（首次登录需要）
echo "🔑 新MySQL实例临时密码（请记录）："
sudo grep 'temporary password' /var/log/mysql/error_$MYSQL_PORT.log

# 11. 显示版本（需指定端口）
echo "🔍 新MySQL实例版本信息："
mysql --version

echo "✅ 操作完成！新MySQL实例已通过服务 $MYSQL_SERVICE_NAME 在端口 $MYSQL_PORT 运行"
echo "📌 连接方式：mysql -u root -p --port=$MYSQL_PORT --socket=/var/run/mysqld/mysqld_$MYSQL_PORT.sock"
