

- ## 连接oracle 数据库
    - > JDBC 使用URL格式连接的三种格式
        - > `Oracle JDBC Thin using an SID`
            > - 格式: `jdbc:oracle:thin:@host:port:SID`
            > - 示例: `jdbc:oracle:thin:@localhost:1521:orcl`
            > - 这是最简单也是用得最多的
        - > `Oracle JDBC Thin using a ServerName`
            > - 格式: `jdbc:oracle:thin:@//host:port/server_name`
            > - 示例: `jdbc:oracle:thin:@//loclahost:1521/orcl.city.com`
            > - 这种格式是Oracle 推荐的格式，因为对于集群来说，每个节点的SID 是不一样的，但是SERVICE_NAME 确可以包含所有节点。
        - > `Oracle JDBC Thin using a TNSName`
            > - 格式: `jdbc:oracle:thin:@TNSName`
            > - 示例: `jdbc:oracle:thin:@TNS_ALIAS_NAME`
            > - 要实现这种连接方式首先要建立tnsnames.ora文件，然后通过System.setProperty指明这个文件路径。再通过上面URL中的@符号指定文件中的要使用到的资源。 
            > - 网上看到的说这种格式的基本没见过




