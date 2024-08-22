

## 参考文档

> https://git-scm.com/book/zh/v2/Git-%E5%B7%A5%E5%85%B7-%E5%AD%90%E6%A8%A1%E5%9D%97





## 常见问题

### 清理子模块

> 有时候添加了子模块但是觉得没处理好，可以通过下面的步骤来删除子模块，然后重新添加
>
> ```shell
> git submodule deinit <submodule name>
> 
> git rm <submodule folder name>
> 
> git submodule add <address to remote git repo> <new folder name>
> ```
>
> 

> 有时候添加了子模块，但是感觉目录不好，或者其他什么情况，删除了要重新拉取时，会提示已经存在，可以使用 --force 强制处理。
>
> ```shell
> git  submodule add --force git@192.168.8.134:aiteer/flyway-cds-sql.git
> ```
>
> 由于 .gitmodules 文件中的 URL 是人们首先尝试克隆/拉取的地方，因此请尽可能确保你使用的 URL 大家都能访问。 例如，若你要使用的推送 URL 与他人的拉取 URL 不同，那么请使用他人能访问到的 URL。 你也可以根据自己的需要，通过在本地执行 `git config submodule.DbConnector.url <私有URL>` 来覆盖这个选项的值。 如果可行的话，一个相对路径会很有帮助。

### 克隆仓库时包含子模块一起

> 如果给 `git clone` 命令传递 `--recurse-submodules` 选项，它就会自动初始化并更新仓库中的每一个子模块， 包括可能存在的嵌套子模块。
>
> ```shell
> git clone --recurse-submodules git@192.168.8.134:aiteer/flink-data-mock.git
> ```
>
> 如果你已经克隆了项目但忘记了 `--recurse-submodules`，那么可以运行 `git submodule update --init` 将 `git submodule init` 和 `git submodule update` 合并成一步。如果还要初始化、抓取并检出任何嵌套的子模块， 请使用简明的 `git submodule update --init --recursive`。

### 子模块的分支

> 配置分支
>
> ```shell
> git config -f .gitmodules submodule.DbConnector.branch stable
> ```



### 拉取代码时包含子模块代码

> 如果你想自动化此过程，那么可以为 `git pull` 命令添加 `--recurse-submodules` 选项（从 Git 2.14 开始）。 这会让 Git 在拉取后运行 `git submodule update`，将子模块置为正确的状态。 此外，如果你想让 Git 总是以 `--recurse-submodules` 拉取，可以将配置选项 `submodule.recurse` 设置为 `true` （从 Git 2.15 开始可用于 `git pull`）。此选项会让 Git 为所有支持 `--recurse-submodules` 的命令使用该选项（除 `clone` 以外）。
>
> 



## 完整命令

> 参考文档：https://git-scm.com/docs/git-submodule/zh_HANS-CN
>
> ```shell
> add [-b <分支>] [-f|--force] [--name <名称>] [--reference <仓库>] [--depth <深度>] [--] <仓库> [<路径>]
> ```
>
> 例：
>
> ```shell
> git submodule add -b main --name flyway-cds-sql git@192.168.8.134:aiteer/flyway-cds-sql.git src/main/resources/sqls
> ```
>
> ```txt
> [submodule "flyway-cds-sql"]
> 	path = src/main/resources/db/migrate/sqls
> 	url = git@192.168.8.134:aiteer/flyway-cds-sql.git
> 	branch = main
> ```
>
> 

## 初始化

> ```shell
> # 在项目的一个地方使用下面的命令就会将一个仓库人生为子模块添加到项目中
> git submodule add https://github.com/chaconinc/DbConnector
> ```

> 想要指定一个目录(`customDir`)，而不是使用项目目录，可以直接在命令结尾添加一个不同的路径即可。
>
> ```shell
> git submodule add https://github.com/chaconinc/DbConnector  customDir
> ```

## .gitmodules 文件

> 它是配置子模块的文件





