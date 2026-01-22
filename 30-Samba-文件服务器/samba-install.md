



## 操作系统

***<u>以==ubuntu==为例</u>***



## 安装

```shell
sudo apt update
sudo apt install samba samba-common-bin -y

```

## 启动

```shell
# 检查 Samba 服务状态
sudo systemctl status smbd
```



## 共享目录

```shell
# 创建共享目录
# 比如你的用户名是 ubuntu，就执行：
sudo mkdir -p /opt/samba/share
# 2. 设置目录所有者为 nobody:nogroup（匹配匿名用户）
sudo chown -R nobody:nogroup /opt/samba/share
# 设置目录的系统所有者和权限
sudo chmod -R 777 /opt/samba/share
```

## 配置

==/etc/samba/smb.conf==

```conf
# 找到 [golbal] 段，在里面添加
[golbal]
   # 匿名用户映射（关键：将匿名访问映射到 nobody 用户）
   guest account = nobody

# 在文件末尾添加
# 共享名称，这个名称直接影响客户端那边拼接地址
[SambaShare]               
   # 共享目录的绝对路径
   path = /opt/samba/share  
   # 共享可用
   available = yes       
   # 允许在网络中被发现
   browseable = yes      
   # 允许写入（如果设为 no 则只读）
   writable = yes        
   # 允许匿名访问（核心修改：yes 开启匿名）
   guest ok = yes         
   # 忽略权限检查（确保匿名用户能写入）
   force user = nobody
   force group = nogroup
   # 创建文件时的权限（777 所有人可读写执行）
   create mask = 0777    
   # 创建目录时的权限（777 所有人可读写执行）
   directory mask = 0777 
   # 移除 valid users 限制
   # valid users = ubuntu
```

检查是否有语法错误

```shell
testparm
# 重启
sudo systemctl restart smbd nmbd
```

`testparm` 命令的输出示例：

```txt
suyunhong:share$ testparm
Load smb config files from /etc/samba/smb.conf
Loaded services file OK.
Weak crypto is allowed by GnuTLS (e.g. NTLM as a compatibility fallback)

Server role: ROLE_STANDALONE

Press enter to see a dump of your service definitions
```

## Windows 客户端

直接在地址栏中输入：\\\\192.168.8.143\SambaShare

其中  `SambaShare`  就是前面配置文件中添加的段

## ubuntu 客户端（不推荐）

需要安装客户端

```shell
sudo apt install smbclient -y
```

在命令行中输入：

```shell
smb://192.168.8.143/SambaShare
```



## ubuntu 系统挂载（推荐）

### 需要安装

```shell
sudo apt install cifs-utils -y
```

### 编辑配置文件（`/etc/fstab`）

这个文件也是swap 配置文件

```conf
# 在文件末尾添加，这是允许任何人访问的配置
//192.168.8.143/SambaShare /opt/samba/share cifs guest,uid=0,gid=0,file_mode=0777,dir_mode=0777,nounix,noserverino 0 0
```

```txt
关键修改说明：
uid=0,gid=0：
    uid=0 对应系统 root 用户，gid=0 对应 root 组；
    结合 file_mode=0777,dir_mode=0777，能确保所有用户（包括匿名用户） 都能读写该挂载目录；
    如果你想保留普通用户权限，也可以用 uid=1000,gid=1000（通用普通用户 UID/GID）+ file_mode=0777,dir_mode=0777，效果一致。
保留 guest：
    必须保留 guest 参数，因为服务器端是匿名共享，客户端挂载时无需用户名密码。
```

### 重新挂载

```shell
sudo systemctl daemon-reload
```

### 验证配置

```shell
sudo mount -a
```

### 验证挂载是否成功

```shell
df -h | grep SambaShare
```

