







## 安装 

```shell
# ubuntu 安装 
sudo apt update
sudo apt install dnsmasq
```



## 配置文件

`/etc/dnsmasq.conf`

```properties
# vim /etc/dnsmasq.conf
resolv-file=/etc/resolv.dnsmasq.conf

# Add domains which you want to force to an IP address here.
# The example below send any host in double-click.net to a local
# web-server.
# 将百度的访问强制定向到 127.0.0.1
# address=/baidu.com/127.0.0.1

# If you want dnsmasq to listen for DHCP and DNS requests only on
# specified interfaces (and the loopback) give the name of the
# interface (eg eth0) here.
# Repeat the line for more than one interface.
#interface=
# Or you can specify which interface _not_ to listen on
#except-interface=
# Or which to listen on by address (remember to include 127.0.0.1 if
# you use this.)
# 让dns 只监听该ip，在局域网中一般是局域网的IP，比如：192.168.8.99
listen-address=120.76.141.74

# If you don't want dnsmasq to read /etc/hosts, uncomment the
# following line.
#no-hosts
# or if you want it to read another file, as well as /etc/hosts, use
# this.
#addn-hosts=/etc/banner_add_hosts
# 域名对应关系
addn-hosts=/etc/dnsmasq.hosts

# For debugging purposes, log each DNS query as it passes through
# dnsmasq.
# 将日志开启
log-queries

# Include another lot of configuration options.
#conf-file=/etc/dnsmasq.more.conf
conf-dir=/etc/dnsmasq.d

# Include all the files in a directory except those ending in .bak
conf-dir=/etc/dnsmasq.d,.bak

# Include all files in a directory which end in .conf
conf-dir=/etc/dnsmasq.d/,*.conf

```



`/etc/dnsmasq.hosts`

需要手动创建

dnsmasq 内部解析所需要的ip 和域名，也就是用户所需要自定义的域名和ip的对应关系配置

```properties
# vim /etc/dnsmasq.hosts
# 内部解析地址关系
192.168.8.143  test-suyh.com
```



`/etc/resolv.dnsmasq.conf`

需要手动创建

dnsmasq 的上游DNS 服务器地址

```properties
# vim /etc/resolv.dnsmasq.conf
# 当dnsmasq 在本地找不到解析记录的时候，去上游查找
```





## ubuntu 系统默认占用53 端口

1. #### **停止并禁用 `systemd-resolved` 服务**

   ```shell
   sudo systemctl stop systemd-resolved
   sudo systemctl disable systemd-resolved
   ```

2. #### **删除 `/etc/resolv.conf` 符号链接**

   ```shell
   sudo rm /etc/resolv.conf
   ```

   

## 启动dnsmasq 服务

```shell

sudo systemctl start dnsmasq
sudo systemctl stop dnsmasq
sudo systemctl restart dnsmasq

sudo systemctl status dnsmasq
```



## 测试dnsmasq 服务

使用命令：`nslookup`

```shell
root@iZwz9gm8i0kjitdnawruodZ:~# nslookup test-suyh.com
Server:		127.0.0.1
Address:	127.0.0.1#53

Name:	test-suyh.com
Address: 192.168.8.112
** server can't find test-suyh.com: REFUSED
```

