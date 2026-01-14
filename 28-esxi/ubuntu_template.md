







```shell
sudo apt update
sudo apt upgrade -y
sudo apt install open-vm-tools -y
```



```shell
# 1. 清除 SSH 主机密钥 (克隆后重启会自动生成新的)
sudo rm /etc/ssh/ssh_host_*

# 2. 清除 Machine ID (systemd 唯一标识)
sudo truncate -s 0 /etc/machine-id
sudo rm /var/lib/dbus/machine-id
sudo ln -s /etc/machine-id /var/lib/dbus/machine-id

# 3. 清除 Cloud-Init 缓存 (如果是 Server 版，这步很重要)
sudo cloud-init clean --logs

# 4. 清理 apt 缓存和历史记录
sudo apt autoremove -y
sudo apt clean
cat /dev/null > ~/.bash_history && history -c

# 5. 关机
sudo poweroff
```

