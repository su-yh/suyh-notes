







## 生成自己的密钥

```shell
# 交互式生成
ssh-keygen -t ed25519 -C "your_email@example.com"

# 直接指定参数，一步到位
# -f 指定路径
# -N 指定密码为空
ssh-keygen -t ed25519 -C "hadoopWorker01" -f ~/.ssh/id_ed25519 -N ""
```



## 将自己的公钥拷贝到目标机器

```shell
ssh-copy-id ${host}
要注意，公钥也要给自己一份，不然访问自己还需要输入密码

# 非交互式的方式输入密码，不过要提前安装sshpass 命令
sshpass -p "${password}" ssh-copy-id ${host}
```



在 .ssh/authorized_keys  文件里面存放了允许 哪些实例进行免密登录



## 验证一下

```shell
ssh hadoop003 cat /etc/profile
```



## 注意

> 在ubuntu 系统下，通过ssh 连接时，是不会加载 /etc/profile 文件的。所以如果将java 的环境变量配置在 /etc/profile 中，那么通过ssh 连接上去，则找不到java 命令。
>
> 但是ubuntu 系统 通过ssh 命令连接时，会加载 /etc/bash.bashrc 文件以及 ~/.bashrc 文件
>
> 所以我们可以将配置放到这两个文件中。
>
> 而在CentOS 系统中是没有 /etc/bash.bashrc 文件，与之对应的文件是： /etc/bashrc



## 结论

> 若要通过ssh 访问目标服务器，则需要将本地的公钥给到目标服务器，那么本机就可以通过ssh 免密访问目标服务器了。
>
> 本机免密访问目标服务器
>
> 文件：`authorized_keys` 允许哪些主机来免密登录，当前主机





## ubuntu 系统环境

- 在非交互式连接，ubuntu 系统可以在哪里加载 全局环境变量，比如: /etc/profile 文件

  > 在非交互式连接时，Ubuntu 系统加载的全局环境变量通常在 `/etc/environment` 文件中。这个文件中定义的环境变量会对所有用户和所有进程生效，包括非交互式的登录会话。
  >
  > 你可以编辑 `/etc/environment` 文件来添加或修改全局环境变量。但需要注意的是，这个文件中只能设置简单的键值对形式的环境变量，而不能执行复杂的 shell 命令。这样做的好处是，不会导致环境变量的意外修改或执行复杂的逻辑。
  >
  > 修改 `/etc/environment` 文件后，通常需要重新登录用户或重启系统才能使更改生效。

-  .ssh/environment  是什么文件

  > `.ssh/environment` 文件是一个用于 SSH 客户端和服务器之间传递环境变量的文件。在 SSH 连接建立时，客户端会将该文件中定义的环境变量发送给远程服务器，在服务器上执行的所有命令都可以使用这些环境变量。
  >
  > 这个文件通常存放在用户的 `.ssh` 目录下，例如 `/home/user/.ssh/environment`。需要注意的是，为了安全起见，SSH 服务器默认情况下不会自动读取这个文件。要启用对 `.ssh/environment` 文件的支持，你需要在 SSH 服务器的配置文件（`/etc/ssh/sshd_config`）中设置 `PermitUserEnvironment` 选项为 `yes`。
  >
  > 当 `PermitUserEnvironment` 选项设置为 `yes` 后，用户可以在自己的 `.ssh/environment` 文件中设置自定义的环境变量，这些变量将会在 SSH 连接时传递给远程服务器。这对于需要在远程服务器上执行的命令使用用户特定的环境变量非常有用。

- 其实最根本的原因

  > 其实最根本的原因就是ubuntu 系统在使用ssh 连接时，会执行用户`~/.bashrc` 文件，而该文件的开关代码就是：
  >
  > ```shell
  > # ~/.bashrc: executed by bash(1) for non-login shells.
  > # see /usr/share/doc/bash/examples/startup-files (in the package bash-doc)
  > # for examples
  > 
  > # If not running interactively, don't do anything
  > case $- in
  >     *i*) ;;
  >       *) return;;
  > esac
  > ```
  >
  > 所以，要解决这个问题，要么就是在它的前面执行相关的代码，要么就是将它注释掉。
  >
  > 在这里，我个人选择注释掉它。

- 其他







