







## 生成自己的密钥

```shell
# 交互式生成
ssh-keygen -t ed25519 -C "your_email@example.com"

# 直接指定参数，一步到位
# -f 指定路径
# -N 指定密码为空
ssh-keygen -t ed25519 -C "hadoopWorker01" -f ~/.ssh/id_ed25519 -N ""
```



## 将自己的公钥拷贝到目标机器

```shell
ssh-copy-id ${host}
要注意，公钥也要给自己一份，不然访问自己还需要输入密码
```



在 .ssh/authorized_keys  文件里面存放了允许 哪些实例进行免密登录



## 验证一下

```shell
ssh hadoop003 cat /etc/profile
```



## 结论

> 若要通过ssh 访问目标服务器，则需要将本地的公钥给到目标服务器，那么本机就可以通过ssh 免密访问目标服务器了。
>
> 本机免密访问目标服务器
>
> 文件：`authorized_keys` 允许哪些主机来免密登录，当前主机









