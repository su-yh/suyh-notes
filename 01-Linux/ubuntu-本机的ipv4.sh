#!/bin/bash

# 获取当前系统的主机名
hostname=$(hostname)

# 使用 nslookup 查询主机名对应的 IP 地址，并仅获取 IPv4 地址
ip_address=$(nslookup "$hostname" | grep 'Address:' | grep -v '#' | awk '/Address:/{ if ($2 !~ /:/) {print $2}}')

# 输出 IP 地址
echo "当前系统的 IPv4 地址为：$ip_address"
