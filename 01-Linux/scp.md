



```shell

scp local_file remote_username@remote_ip:remote_folder 
# 或者 
scp local_file remote_username@remote_ip:remote_file 
# 或者 
scp local_file remote_ip:remote_folder 
# 或者 
scp local_file remote_ip:remote_file 
```



```shell
# scp 命令执行时会有提示：Are you sure you want to continue connecting (yes/no/[fingerprint])?  是否可以跳过该提示

scp -o StrictHostKeyChecking=no source_file user@remote_host:destination_path

```





