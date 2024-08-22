



## adduser

> 使用adduser 更方便一些



## sudo 权限

> ubuntu 系统添加用户`hdp` 拥有`sudo` 权限

如果你要将 `hdp` 用户添加到 `sudo` 组，并赋予其执行 `sudo` 命令的权限，可以按照以下步骤操作：

1. 首先，确保你有 root 权限。
2. 使用以下命令将 `hdp` 用户添加到 `sudo` 组：

```bash
sudo usermod -aG sudo ${username}
```

这个命令将 `hdp` 用户添加到 `sudo` 组中。

1. 确认 `/etc/sudoers` 文件中包含了 `@includedir /etc/sudoers.d` 这行配置。如果没有，你需要手动添加。
2. 在 `/etc/sudoers.d` 目录下创建一个文件，比如 `hdp`，并编辑它：

```bash
sudo vim /etc/sudoers.d/${username}
```

1. 在文件中添加以下行来授予 `hdp` 用户使用 `sudo` 的权限：

```txt
${username} ALL=(ALL:ALL) ALL
```

这行的意思是允许 `hdp` 用户在任何主机上以任何用户的身份执行任何命令。你也可以根据具体需求进行更精确的配置，比如只允许特定的命令。

1. 保存文件并退出编辑器。确保保存的文件权限为 `0440`，这样只有 root 用户才有权修改它：

```bash
# suyh - 不是必须的，基本不需要操作
sudo chmod 440 /etc/sudoers.d/${username}
```

这样，`hdp` 用户就被添加到了 `sudo` 组，并具有了使用 `sudo` 命令的权限。

