





## dump 文件生成

### 运行中的程序

>  将内存使用情况dump 到文件中

> 用法

```shell
jmap -dump:format=b,file=<dumpFileName> <pid>
```

> 示例

```shell
jmap -dump:format=b,file=dump.dat 823339
```

### 添加启动参数

```shell
nohup java -jar -Xms32M -Xmx32M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local user-0.0.1-SNAPSHOT.jar  > log.file  2>&1 &
```



## mat 工具

### 下载

> https://eclipse.dev/mat/downloads.php

### 解压后得到的文件列表

![image-20240203152629356](001-jmap.assets/image-20240203152629356.png)

### 使用

1. 配置JDK

   > 打开配置文件：`MemoryAnalyzer.ini`
   >
   > 在首两行添加如下配置即可，添加到文件后面不行

   ```txt
   -vm 
   D:\Java\jdk-17\bin\javaw.exe
   ```

2. 双击应用程序: `MemoryAnalyzer.exe`

3. 打开一个dump 文件

   ![img](001-jmap.assets/wf19m7285c.jpeg)

4. 继续

   ![img](001-jmap.assets/e4i43xvoxl.jpeg)

5. overview

   ![img](001-jmap.assets/nhs5ymk3hd.jpeg)

6. Histogram

   ![img](001-jmap.assets/q2kzzrimz3.jpeg)

7. 查看对象以及它的依赖

   ![img](001-jmap.assets/ikig9j92px.jpeg)

8. 查看可能内存泄漏的分析

   ![img](001-jmap.assets/e4cco5timy.jpeg)

9. 其他

