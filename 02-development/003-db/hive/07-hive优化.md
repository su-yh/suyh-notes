

- ## 并行模式
    - > 设置参数
        > - `set hive.exec.parallel=true`
    - > 一次SQL 计算中允许并行执行的JOB 个数的最大值
        > - `hive.exec.parallel.thread.number`
        > - 默认值是8

- ## 严格模式
    - > 设置参数

- ## hive 排序
    - > Sort By 对于单个reduce 的数据进行排序
        > - 对每个Reduce 中的数据进行排序
    - > Distribute By 分区排序，经常和Sort By 结合使用
    - > Order by 谨慎使用，以后再也不用了。
    - > Cluster By - 相当于Sort by + Distribute By 
        > - 但是它不能通过 asc、desc 的方式 来指定排序规则
        > - 这个也不用


- ## Hive Join
    - > 设置参数，自动map join
        > - `hive.auto.onvert.join=true`

- ## Map-Side
    - > map 端的聚合











