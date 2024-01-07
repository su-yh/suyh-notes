

```shell
# 配置git 密码缓存默认15 分钟
git config --global credential.helper cache

# 自定义时间 这个还没验证，空格和- 是一个还是两个
git config credential.helper 'cache --timeout=3600'
```









