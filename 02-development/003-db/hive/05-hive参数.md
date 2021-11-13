

- ## 参数
- > 查看所有参数
    - > 直接在命令行中
        > - `hive> set;`
- > 查看指定参数
    - > 直接在命令行中
        > - `hive> set hive.cli.print.header;`
- > 表头
    - > 启动时的命令行
        > - `[root@node ]# hive --hiveconf hive.cli.print.header=true`
    - > 在运行里面
        > - `hive> set hive.cli.print.header=true`

- > 自定义参数
    - > `hive -d abc=1` `hive -define abc=1` `hive --hivevar abc=1`
        > - 在hive 中引用变量: `${abc}`
        >> - `SELECT * FRO psn WHERE id=${abc}`








