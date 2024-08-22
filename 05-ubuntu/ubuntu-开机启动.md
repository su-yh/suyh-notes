







> 参考链接：https://www.cnblogs.com/Areas/p/13439000.html





### 查看报错日志

> ```shell
> sudo journalctl -u suyh.service
> ```
>
> 

### 写服务文件

> sudo vim /etc/systemd/system/suyh.service
>
> ```txt
> [Unit]
> # 简单的描述
> Description=suyh init script
> 
> [Service]
> # 服务是一个简单类型的服务
> Type=simple
> # 启动时执行的脚本文件
> ExecStart=/home/suyunhong/suyh.sh
> # 重启策略
> Restart=on-failure
> 
> [Install]
> # 一般是这个值
> WantedBy=multi-user.target
> 
> ```
>
> 



> 服务启动时的命令
>
> ```shell
> # 对应的服务名称为：suyh.service
> sudo systemctl status suyh
> sudo systemctl start suyh
> sudo systemctl stop suyh
> # suyh.service 文件修改之后要使用如下命令重新加载
> sudo systemctl daemon-reload
> 
> 
> ```
>
> 





