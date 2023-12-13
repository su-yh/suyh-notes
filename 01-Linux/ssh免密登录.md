





## 生成自己的密钥

```shell
ssh-keygen -t ed25519 -C "your_email@example.com"
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











