#!/bin/bash

set -e

# 自定义MySQL端口
MYSQL_PORT=3306

# 1. 创建工作目录（临时使用）
WORKDIR=/tmp/mysql57
mkdir -p $WORKDIR
cd $WORKDIR

echo "📦 下载 MySQL 5.7.33 .deb 包中..."

# 2. 下载适用于当前系统的5.7.33包（注意：如果是Ubuntu 18.04以上，建议找对应版本的包）
# 以下链接为Ubuntu 18.04版本，若系统不同，需替换为对应版本的5.7.33包
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-common_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-server_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-server_5.7.33-1ubuntu18.04_amd64.deb

echo "✅ 下载完成，开始安装..."

# 3. 先安装依赖，避免依赖缺失导致apt自动升级
# 添加 Ubuntu 官方主源（确保包含 main 组件）
echo "deb http://archive.ubuntu.com/ubuntu focal main universe" | sudo tee -a /etc/apt/sources.list
sudo apt update
sudo apt install -y libaio1 libmecab2  # MySQL 5.7的核心依赖

# 4. 强制安装本地5.7.33包（--force-depends忽略依赖检查，避免被apt替换）
sudo dpkg -i --force-depends *.deb

# 5. 若仍有依赖问题，手动修复（仅安装缺失依赖，不升级MySQL）
sudo apt -f install -y --no-install-recommends

echo "✅ 安装完成"

# 6. 锁定MySQL版本，防止后续apt upgrade升级到8.0
sudo apt-mark hold mysql-common mysql-client mysql-server mysql-community-client mysql-community-server

# 7. 修改MySQL端口配置
echo "🔧 正在修改MySQL端口为 $MYSQL_PORT..."
sudo cp /etc/mysql/mysql.conf.d/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf.bak
sudo sed -i "s/^port = 3306/port = $MYSQL_PORT/" /etc/mysql/mysql.conf.d/mysqld.cnf

# 8. 重启服务并检查状态
echo "🔍 正在重启并检查 MySQL 服务状态..."
sudo systemctl restart mysql
sudo systemctl status mysql --no-pager

# 9. 验证版本和端口
echo "🔍 验证MySQL版本..."
mysql --version

echo "🔍 验证MySQL端口配置..."
grep "port" /etc/mysql/mysql.conf.d/mysqld.cnf

# 10. 清理临时文件
# cd ../
# rm -rf $WORKDIR

echo "✅ 操作完成，MySQL 5.7.33已使用端口 $MYSQL_PORT 运行"
