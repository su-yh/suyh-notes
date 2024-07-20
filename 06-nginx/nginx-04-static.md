### nginx 动静分离



### root与alias 的区别

> 例如：
>
> 有一张图片，URL是：/awaimai.com==/static/a.jpg==
>
> 它在服务器的路径是：/var/www/app==/static/a.jpg==

- 使用`root`配置

  ```txt
  # 使用root ，则最终的结果就是：root 路径(/var/www/app) + location 路径(/static)
  # 使用root 的缺点就是url 路径不能随便给，只能在 /var/www/app 下面有的才可以。
  location /static/ {
      root /var/www/app/;
  }
  ```

- 使用`alias`配置

  ```txt
  # 使用alias 则最终的结果就是完全使用 alias 的路径替代 location 的路径。
  # 使用alias 的优点就是 url 路径可以随便给，因为最终的路径由alias 来决定。
  location /static/ {
      alias /var/www/app/static/;
  }
  ```

  上面两种配置都能达到同样的效果。







```nginx
# 使用哪个用户启动nginx
#user  nobody;

# 启动nginx 的时候要启动的worker 进程数量
worker_processes  1;

# 事件驱动模块
events {
    # 每一个worker 进程可以有多少个连接
    worker_connections  1024;
}

http {
    # 将外部的配置文件引入到当前配置文件中
    include       mime.types;
    # 当没有找到匹配的mime 的类型时，使用默认的处理类型
    default_type  application/octet-stream;

    # 数据零拷贝，让数据直接传输，而不需要走nginx 进程。
    # nginx 会给操作系统发送一个信号，告诉操作系统，这些数据不需要经过我，你直接通过网络传输即可。
    sendfile        on;

    keepalive_timeout  65;

    # 虚拟主机
    # 一个nginx 可以配置多个主机，一个主机就是一个server
    # 每个主机通过不同的端口号进行区分
    server {
        # 监听端口号
        listen       80;
        # 域名或者主机名
        # 域名地址可以匹配多个，以空格分隔
        # 还可以使用 * 来进行模糊匹配: *.suyh.com   www.suyh.*
        # 还可以正则匹配
        server_name  dev.suyh.com sit.suyh.com;
        
        # http://atguigu.com/xxoo/index.html
        # location 匹配的就是域名之后的  /xxoo/index.html
        location / {
            # 代理到指定的服务器
            # 当访问 / 路径时，请求会被转到下面的代理服务器
            proxy_pass http://192.168.44.104:8080;
        }

        location /css {
            # 当访问/css 开头的路径时，到html 目录下面并添加上/css 的路径去查询静态资源文件
            root html;
            index index.html index htm;
        }

        location /img {
            # 当访问/img 开头的路径时，到html 目录下面并添加上/css 的路径去查询静态资源文件
            root html;
            index index.html index htm;
        }

        location /js {
            # 当访问/js 开头的路径时，到html 目录下面并添加上/css 的路径去查询静态资源文件
            root html;
            index index.html index htm;
        }

        # 服务器端发生错误时，错误码为： 500  502  503  504 时让前端浏览器跳转到 http://atguigu.com/50x.html
        error_page   500 502 503 504  /50x.html;
        
        # 当前端来访问 /50x.html 时，跳转到html 目录下面，并找到50x.html 
        location = /50x.html {
            root   html;
        }
    }
    
    
    # 虚拟主机
    # 一个nginx 可以配置多个主机，一个主机就是一个server
    # 每个主机通过不同的端口号进行区分
    server {
        # 监听端口号
        listen       80;
        # 域名或者主机名
        server_name  s.com;
        
        # http://atguigu.com/xxoo/index.html
        # location 匹配的就是域名之后的  /xxoo/index.html
        location / {
            # 匹配上了之后，到哪个目录下面去找对应的资源
            # 这里就是到html 目录下面去找资源
            root   /www/vod;
            index  index.html index.htm;
        }
        
        # 服务器端发生错误时，错误码为： 500  502  503  504 时让前端浏览器跳转到 http://atguigu.com/50x.html
        error_page   500 502 503 504  /50x.html;
        
        # 当前端来访问 /50x.html 时，跳转到html 目录下面，并找到50x.html 
        location = /50x.html {
            root   html;
        }
    }
}

```



### 正则表达式

```nginx
# 使用哪个用户启动nginx
#user  nobody;

# 启动nginx 的时候要启动的worker 进程数量
worker_processes  1;

# 事件驱动模块
events {
    # 每一个worker 进程可以有多少个连接
    worker_connections  1024;
}

http {
    # 将外部的配置文件引入到当前配置文件中
    include       mime.types;
    # 当没有找到匹配的mime 的类型时，使用默认的处理类型
    default_type  application/octet-stream;

    # 数据零拷贝，让数据直接传输，而不需要走nginx 进程。
    # nginx 会给操作系统发送一个信号，告诉操作系统，这些数据不需要经过我，你直接通过网络传输即可。
    sendfile        on;

    keepalive_timeout  65;

    # 虚拟主机
    # 一个nginx 可以配置多个主机，一个主机就是一个server
    # 每个主机通过不同的端口号进行区分
    server {
        # 监听端口号
        listen       80;
        # 域名或者主机名
        # 域名地址可以匹配多个，以空格分隔
        # 还可以使用 * 来进行模糊匹配: *.suyh.com   www.suyh.*
        # 还可以正则匹配
        server_name  dev.suyh.com sit.suyh.com;
        
        # http://atguigu.com/xxoo/index.html
        # location 匹配的就是域名之后的  /xxoo/index.html
        location / {
            # 代理到指定的服务器
            # 当访问 / 路径时，请求会被转到下面的代理服务器
            proxy_pass http://192.168.44.104:8080;
        }

        # ~ 开头表示为正则表达式
        location ~*/(js|img|css) {
            # 当访问/js 开头的路径时，到html 目录下面并添加上/css 的路径去查询静态资源文件
            root html;
            index index.html index htm;
        }

        # 服务器端发生错误时，错误码为： 500  502  503  504 时让前端浏览器跳转到 http://atguigu.com/50x.html
        error_page   500 502 503 504  /50x.html;
        
        # 当前端来访问 /50x.html 时，跳转到html 目录下面，并找到50x.html 
        location = /50x.html {
            root   html;
        }
    }
    
    
    # 虚拟主机
    # 一个nginx 可以配置多个主机，一个主机就是一个server
    # 每个主机通过不同的端口号进行区分
    server {
        # 监听端口号
        listen       80;
        # 域名或者主机名
        server_name  s.com;
        
        # http://atguigu.com/xxoo/index.html
        # location 匹配的就是域名之后的  /xxoo/index.html
        location / {
            # 匹配上了之后，到哪个目录下面去找对应的资源
            # 这里就是到html 目录下面去找资源
            root   /www/vod;
            index  index.html index.htm;
        }
        
        # 服务器端发生错误时，错误码为： 500  502  503  504 时让前端浏览器跳转到 http://atguigu.com/50x.html
        error_page   500 502 503 504  /50x.html;
        
        # 当前端来访问 /50x.html 时，跳转到html 目录下面，并找到50x.html 
        location = /50x.html {
            root   html;
        }
    }
}

```

