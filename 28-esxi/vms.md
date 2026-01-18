









## 基础系统

| 域名 | IIP           | 应用服务                             |               |
| ---- | ------------- | ------------------------------------ | ------------- |
| -    | 192.168.0.120 | Deall T620 自带的 iRADC 控制管理页面 | root/suyunfei |
| -    | 192.168.0.121 | ESXi 6.5 虚拟机系统                  | root/suyunfei |
|      |               |                                      |               |



## 虚拟机

| 域名                   | IIP           | 账 号/密码      | 应用服务                                                     |
| ---------------------- | ------------- | --------------- | ------------------------------------------------------------ |
| -                      | 192.168.0.99  |                 | ubuntu 系统，虚拟机模板，用来导出模板，提供给后续 系统的安装。 |
| dns.local.com          | 192.168.0.5   |                 | 域名服务器                                                   |
| compose.local.com      | 192.168.0.100 |                 | 组合应用服务，第三方组件：redis, rabbitmq, 文件服务（Samba） 等 |
| selectdb.mgr.local.com | 192.168.0.149 | root/K3bdp-2023 | selectdb 管理后台：Doris Manager                             |
| doris.fe.local.com     | 192.168.0.150 | admin/bdczF6v   | Doris-FE，9030 (MySQL), 8030(HTTP)                           |
| doris.be01.local.com   | 192.168.0.151 |                 | Doris-BE                                                     |
| doris.be02.local.com   | 192.168.0.152 |                 | Doris-BE                                                     |
| doris.be03.local.com   | 192.168.0.153 |                 | Doris-BE                                                     |
| services.local.com     | 192.168.0.190 |                 | 相关的java 服务部署运行                                      |
| db.master.local.com    | 192.168.0.201 |                 | Mysql, pgsql 等                                              |
| nginx.local.com        | 192.168.0.202 |                 | nginx 服务器                                                 |
|                        |               |                 |                                                              |
|                        |               |                 |                                                              |
|                        |               |                 |                                                              |
|                        |               |                 |                                                              |
|                        |               |                 |                                                              |

