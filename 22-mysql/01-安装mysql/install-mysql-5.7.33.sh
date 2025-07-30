#!/bin/bash

set -e

# 自定义MySQL端口（可根据需要修改）
MYSQL_PORT=3306

# 1. 创建工作目录（临时使用）
WORKDIR=/tmp/mysql57
mkdir -p $WORKDIR
cd $WORKDIR

echo "📦 下载 MySQL 5.7.33 .deb 包中..."

# 2. 下载 MySQL 5.7.33 所需的 .deb 包
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-common_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-client_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-community-server_5.7.33-1ubuntu18.04_amd64.deb
wget https://downloads.mysql.com/archives/get/p/23/file/mysql-server_5.7.33-1ubuntu18.04_amd64.deb

echo "✅ 下载完成，开始安装..."

# 3. 安装 .deb 包
sudo dpkg -i *.deb || sudo apt -f install -y

echo "✅ 安装完成"

# 4. 修改MySQL端口配置
echo "🔧 正在修改MySQL端口为 $MYSQL_PORT..."

# 备份原始配置文件
sudo cp /etc/mysql/mysql.conf.d/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf.bak

# 修改端口配置（将port = 3306替换为自定义端口）
sudo sed -i "s/^port = 3306/port = $MYSQL_PORT/" /etc/mysql/mysql.conf.d/mysqld.cnf

# 5. 重启服务并检查状态
echo "🔍 正在重启并检查 MySQL 服务状态..."
sudo systemctl restart mysql
sudo systemctl status mysql --no-pager

# 6. 验证端口配置
echo "🔍 验证MySQL端口配置..."
grep "port" /etc/mysql/mysql.conf.d/mysqld.cnf

# 7. 显示版本
mysql --version

echo "✅ 操作完成，MySQL已使用端口 $MYSQL_PORT 运行"

# 8. 完成之后可以删除目录
# cd ../
# rm -rf $WORKDIR

