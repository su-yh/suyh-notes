## 配置文件默认路径

> 默认情况下，配置文件所在路径：`$PGDATA/pg_hba.conf`
>
> 看着它像是跟数据库集簇相关联的，也就是说每个数据库集簇都有自己的相关配置。



## 允许通过远程连接到postgres

> 默认情况下只有通过本机才能连接并访问postgres
>
> 若要允许远程访问，则需要配置对应的IP，同时还需要开启对该IP 的监听(参考配置文件：`postgres.conf`)
>
> 
>
> 配置允许远程连接，需要对对应的IP 进行配置
>
> trust 表示不需要密码也可以直接登录
>
> md 似乎是指需要用密码进行登录
>
> ```txt
> # TYPE  DATABASE        USER            ADDRESS                 METHOD
> 
> 
> # IPv4 local connections:
> host    all             all             127.0.0.1/32            trust
> 
> # 这两种就是直接配置对应的IP 允许远程连接并登录
> # host    all             all             172.31.3.1/32           md5
> # host    all             all             120.76.231.41/32        md5
> # 如下配置是允许本机的所有有效IP 远程连接并登录
> host    all             all             0.0.0.0/0               md5
> 
> 
> ```
>
> 

