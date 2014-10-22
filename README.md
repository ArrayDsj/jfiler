JFiler
======
##简介

JFiler是一个用java实现的命令行文件传输工具。客户端/服务端通过socket进行通信；交互流程采用[Tasklet](https://github.com/manwu91/tasklet)进行管理控制。

##使用

1. 启动服务端: java -jar jfiler.jar Server -p 监听端口号 -d 接收文件夹 -k 连接密钥
2. 启动客户端,传输文件： java -jar jfiler.jar Client -H 服务端ip [端口号] -k 服务端设置的密钥 -f 要上传的文件

####使用示例:
1. 服务端: java -jar jfiler.jar Server -d . -k abc
2. 客户端: java -jar jfiler.jar Client -H localhost -k abc -f test.txt

####截图：
![server截图](/screenshot/server.png)
![client截图](/screenshot/client.png)

