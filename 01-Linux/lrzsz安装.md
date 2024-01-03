









```shell
tar -zxvf lrzsz-0.12.20.tar.gz
cd lrzsz-0.12.20
mkdir /usr/local/lrzsz
./configure --prefix=/usr/local/lrzsz
make install 

# 生成全局命令软链接
ln -s /usr/local/lrzsz/bin/lrz /usr/bin/rz
ln -s /usr/local/lrzsz/bin/lsz /usr/bin/sz
```

