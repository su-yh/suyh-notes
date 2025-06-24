

```shell
# 配置git 密码缓存默认15 分钟
git config --global credential.helper cache

# 自定义时间 这个还没验证，空格和- 是一个还是两个
git config credential.helper 'cache --timeout=3600'
```





## 合并提交

在合并分支时，将源分支的多次提交处理到目标分支，并处理成一次提交记录。

```shell
# 1. 切换到目标分支
git checkout main
# 2. 执行squash 合并
git merge --squash main-suyh
# 3. 提交合并
git commit -m "合并分支提交"
```











