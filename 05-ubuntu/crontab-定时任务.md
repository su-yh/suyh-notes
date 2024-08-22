





> 参考链接：https://blog.neilpang.com/ubuntu%E9%85%8D%E7%BD%AEcrontab-%E7%BC%96%E8%BE%91crontab%E6%96%87%E4%BB%B6-%E6%89%A7%E8%A1%8C%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1/





### 创建或者编辑

> 创建或编辑用户的配置文件
>
> ```shell
> # 最好是使用有root 权限的用户操作，为指定用户添加定时任务
> sudo crontab -e -u [username]
> 
> # 刷新
> service cron reload
> ```
>
> 编辑示例
>
> ```txt
> # 第分钟执行一次，这个只精确到分钟级别
> * * * * * cd /home/flink_cohort_batch/flink/flink-cohort-batch/flink-1.18.0 && ./cron_start.sh
> ```



### 查看

> 查看当前的定义任务列表
>
> ```shell
> crontab -l
> ```



### 生效

> 重新加载让其生效
>
> ```shell
> service cron reload
> ```

