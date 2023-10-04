







### wsl 安装Ubuntu 后移动到别的盘下面

```shell
# 先将系统关停
wsl --shutdown

# 进入到下载的移动脚本所在的目录
cd d:\move-wsl-master

# 运行脚本
.\move-wsl.ps1
# 在这个过程中，会让选择对应的系统，这个时候按下对应的数字即可。
# 然后还会让输入移动到哪个目录下面，输入目录即可

# 进入之后顺便把root 密码更新一下吧
passwd root
```





```shell

# 安装wsl 同时默认安装Ubuntu
# 注意使用管理员权限
wsl --install 

# 查看已安装的系统
wsl -l -v

# 卸载安装的Ubuntu 
wsl --unregister Ubuntu

# 停掉子系统
wsl --shutdown

# 以root 身份进入系统 
wsl --user root
```





### 重置wsl Ubuntu 的root 密码

```shell
# 以root 身份进入系统 
wsl --user root

# 输入命令 passwd root 修改 root 密码
```





经常运行脚本的时候都有权限问题，不让运行，不信息的脚本

```txt
PS F:\wsl\move-wsl-master> .\move-wsl.ps1
.\move-wsl.ps1 : 无法加载文件 F:\wsl\move-wsl-master\move-wsl.ps1。未对文件 F:\wsl\move-wsl-master\move-wsl.ps1 进行数
字签名。无法在当前系统上运行该脚本。有关运行脚本和设置执行策略的详细信息，请参阅 https:/go.microsoft.com/fwlink/?LinkID
=135170 中的 about_Execution_Policies。
所在位置 行:1 字符: 1
+ .\move-wsl.ps1
+ ~~~~~~~~~~~~~~
    + CategoryInfo          : SecurityError: (:) []，PSSecurityException
    + FullyQualifiedErrorId : UnauthorizedAccess
```

可以使用两个方式来处理这个问题让这些脚本运行

```shell
# 查看当前策略
Get-ExecutionPolicy
# 修改为某个策略
Set-ExecutionPolicy RemoteSigned

# 如果这样还不行，还可以执行下面这个来处理
Set-ExecutionPolicy Unrestricted

# 如果还要恢复为安全模式，可以使用下面的命令恢复
Set-ExecutionPolicy Restricted

```





### wsl 中无法启动sshd 服务的处理

```shell

# 安装SSH服务器（如果尚未安装）：在Ubuntu终端中运行以下命令：

sudo apt update
sudo apt install openssh-server


# 启动SSH服务：在Ubuntu终端中运行以下命令：

sudo service ssh start
```



### wsl 中安装指定版本 的系统

```properties
# 在安装之前可以通过命令查看，可安装的发行版
wsl --list --online

# 安装指定版本的系统
# 经常都会报错，多执行几次就好了，是网络的原因
# PS C:\Users\admin> wsl --install -d Ubuntu-22.04
# 正在安装: Ubuntu 22.04 LTS
# Error: 0x80240438           0.0%                           ]
# Error code: Wsl/InstallDistro/0x80240438
wsl --install -d Ubuntu-22.04

# 列出已安装的 Linux 发行版，下面两个命令都是一样的效果
wsl --list --verbose
wsl -v -l 

# 进入指定系统
wsl -d Ubuntu-22.04
# 进入系统的时候还可以指定登录 用户
wsl -d Ubuntu-22.04 -u suyunhong

```

