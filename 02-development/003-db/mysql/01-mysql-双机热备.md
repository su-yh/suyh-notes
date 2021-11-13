


- ## MYSQL 数据库的双机热备
    > - 准备两台安装好mysql 服务器的计算机; 
    > - 启动好mysql 服务; 
    >> - centos 6.5: `service mysql start`
    >> - centos 7: ``
    > - 两台服务的master 配置
    >> - `vim /etc/my.cnf` 文件中，在mysqld 标签下面添加两行
    >> - 其中`server-id=2` 为master 指定一个ID
    >> - `log-bin=mysql-bin` 它为二进制文件的文件名
    >>>     [mysqld]
    >>>     server-id=2
    >>>     log-bin=mysql-bin
    >> - 配置OK 之后可以使用MYSQL 客户端登录上去
    >>> - `mysql> flush tables with read lock;` # 数据库锁表，不让写数据;
    >>> - `mysql> SHOW MASTER STATUS`  查看master 的当前状态信息
    >>> - 在这里面可以查看到它的一些状态，同时在配置slave 的时候也需要用到其中的，File 以及Position 两个字段的数据
    >>>> <details>
    >>>> <summary><mark><font color=darkred>master status</font></mark></summary>
    >>>> 
    >>>>                     File: mysql-bin.000002
    >>>>                 Position: 120
    >>>>             Binlog_Do_DB: 
    >>>>         Binlog_Ignore_DB: 
    >>>>        Executed_Gtid_Set: 
    >>>>        1 row in set (0.00 sec)
    >>>> </details>
    >>> - `mysql> unlock tables;`  #从启动好后，记得要解除锁定
    > - 两台服务的slave 启用
    >> - 登录到mysql 服务，使用MYSQL 命令
    >>> <details>
    >>> <summary><mark><font color=darkred>slave 命令</font></mark></summary>
    >>> 
    >>>     mysql> CHANGE MASTER TO MASTER_HOST='10.42.167.78', MASTER_PORT=3306,
    >>>     MASTER_USER='root',MASTER_PASSWORD='root',
    >>>     MASTER_LOG_FILE='mysql-bin.000005', MASTER_LOG_POS=120;
    >>> </details>
    >>>
    >> - 其中
    >>> <details>
    >>> <summary><mark><font color=darkred>slave 命令的含义</font></mark></summary>
    >>> 
    >>> - `MASTER_HOST` `MASTER_PORT` master 服务器的IP 地址以及端口
    >>> - `MASTER_USER`  `MASTER_PASSWORD` 连接用户名 以及密码
    >>> - `MASTER_LOG_FILE`  这个是的master 服务器上面，使用`master status ` 命令查出来的，`File` 字段的当前结果
    >>>> - 它表示当前的二进制文件名
    >>> - `MASTER_LOG_POS`  这个也是`master status` 命令查出来的，它对应的是`Position` 的结果
    >>>> - 它表示当前二进制文件的具体位置。
    >>> </details>
    >> - 双机热备，则两台服务器都需要执行slave 命令，只是参数不同。
    >> - 如果没有出错，启用slave 就可以了。
    >>> - 开启与停止slave: `slave start`  `slave stop` 如果出错，则换为: `start slave`  `stop slave`即可

    - ## 对于slave 的几个重要的字段的意义
    > - 使用mysql命令`show slave status` 可以查看到相关的状态
    >> <details>
    >> <summary><mark><font color=darkred>slave 状态</font></mark></summary>
    >> 
    >>>                        Slave_IO_State: 
    >>>                           Master_Host: 10.42.167.78
    >>>                           Master_User: root
    >>>                           Master_Port: 3306
    >>>                         Connect_Retry: 60
    >>>                       Master_Log_File: mysql-bin.000005
    >>>                   Read_Master_Log_Pos: 120
    >>>                        Relay_Log_File: 10-42-170-247-relay-bin.000001
    >>>                         Relay_Log_Pos: 4
    >>>                 Relay_Master_Log_File: mysql-bin.000005
    >>>                      Slave_IO_Running: No
    >>>                     Slave_SQL_Running: No
    >>>                       Replicate_Do_DB: 
    >>>                   Replicate_Ignore_DB: 
    >>>                    Replicate_Do_Table: 
    >>>                Replicate_Ignore_Table: 
    >>>               Replicate_Wild_Do_Table: 
    >>>           Replicate_Wild_Ignore_Table: 
    >>>                            Last_Errno: 0
    >>>                            Last_Error: 
    >>>                          Skip_Counter: 0
    >>>                   Exec_Master_Log_Pos: 120
    >>>                       Relay_Log_Space: 120
    >>>                       Until_Condition: None
    >>>                        Until_Log_File: 
    >>>                         Until_Log_Pos: 0
    >>>                    Master_SSL_Allowed: No
    >>>                    Master_SSL_CA_File: 
    >>>                    Master_SSL_CA_Path: 
    >>>                       Master_SSL_Cert: 
    >>>                     Master_SSL_Cipher: 
    >>>                        Master_SSL_Key: 
    >>>                 Seconds_Behind_Master: NULL
    >>>         Master_SSL_Verify_Server_Cert: No
    >>>                         Last_IO_Errno: 0
    >>>                         Last_IO_Error: 
    >>>                        Last_SQL_Errno: 0
    >>>                        Last_SQL_Error: 
    >>>           Replicate_Ignore_Server_Ids: 
    >>>                      Master_Server_Id: 0
    >>>                           Master_UUID: 
    >>>                      Master_Info_File: /var/lib/mysql/master.info
    >>>                             SQL_Delay: 0
    >>>                   SQL_Remaining_Delay: NULL
    >>>               Slave_SQL_Running_State: 
    >>>                    Master_Retry_Count: 86400
    >>>                           Master_Bind: 
    >>>               Last_IO_Error_Timestamp: 
    >>>              Last_SQL_Error_Timestamp: 
    >>>                        Master_SSL_Crl: 
    >>>                    Master_SSL_Crlpath: 
    >>>                    Retrieved_Gtid_Set: 
    >>>                     Executed_Gtid_Set: 
    >>>                         Auto_Position: 0
    >>>         1 row in set (0.00 sec)
    >>> </details>
    >>> 
    >> - `Slave_IO_Running: No`  表示当前作为备份机的IO 线程未启用
    >> - `Slave_SQL_Running: No`   表示当前作为备份机的SQL 线程未启用
