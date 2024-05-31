













> ```shell
> # hadoop hdfs dfs 三个命令完全是一样的，就像别名一样
> 
> # 帮助命令
> hadoop fs -help rm
> 
> # 创建文件夹 /sanguo
> hadoop fs -mkdir /sanguo
> 
> ```
>
> 



## 上传

> ```shell
> # 文件上传，将本地文件 shuguo.txt 上传到hdfs /sanguo 目录  本地文件将会被删除
> hadoop fs -moveFromLocal ./shuguo.txt /sanguo
> ```
>
> ```shell
> # 文件上传，将本地文件 ./weiguo.txt 上传到hdfs /sanguo 目录  本地文件将会保留
> hadoop fs -copyFromLocal ./weiguo.txt /sanguo
> ```
>
> ```shell
> # 文件上传，等同于copyFromLocal
> hadoop fs -put ./wuguo.txt /sanguo
> ```
>
> 



