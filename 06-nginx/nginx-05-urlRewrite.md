### URLrewrite



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
            # 当访问 /2.html 的时候重写为 /index.jsp?pageNum=2
            # 即: 访问 http://localhost/2.html  时会访问  http://localhost/index.jsp?pageNum=2
            # rewrite ^/2.html$    /index.jsp?pageNum=2 break;
            
            # 使用正则，将前面小括号中分组的值替换到后面的$1
            rewrite ^/([0-9]+).html$    /index.jsp?pageNum=$1  break;
            proxy_pass http://192.168.44.104:8080;
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

