## 下载Zookeeper镜像

> ```bash
> $ docker pull zookeeper
> ```

## 启动容器并添加映射

> ```bash
> $ docker run --privileged=true -d --name zookeeper --publish 2181:2181 -d zookeeper:latest
> ```

## 查看容器

> ```bash
> $ docker ps -a
> ```

