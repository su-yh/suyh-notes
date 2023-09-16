









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

