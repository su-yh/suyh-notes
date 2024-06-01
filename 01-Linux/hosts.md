







### 修改主机名和host 文件

> 打开文件 `/etc/hostname`
>
> 将当前主机名写入进去即可，然后重启，或者使用其他方式使其生效
>
> 然后使用hostname 命令查看最终的效果

```txt
hadoop001
```



> 打开文件 `/etc/hosts`
>
> 将相关的IP 与内网访问的主机名添加进去
>
> 下面的这些可以在所有的内网主机上面执行，这样在每一台主机上面都可以使用这些主机名来进行访问了。

```txt
# 前面的是IP 地址 后面跟主机名
172.31.1.1 hadoop001
172.31.1.2 hadoop002
172.31.1.3 hadoop003

```



## 直接修改主机名，同时直接生效

> ```shell
> sudo hostnamectl set-hostname "$new_hostname"
> ```
>
> 

