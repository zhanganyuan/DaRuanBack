# DaRuanBack
这个项目是武汉大学计算机学院大软课程第三小组的服务器端代码。
# 1. 如何在自己的自己上跑起来？！
以下先主要介绍一下如何修改代码。以适应自己的机器，数据库。
## 数据库的格式
如果你不想在代码里面把对数据库的操作里面的字符串一个个改的话，那么就请你先建一个和我一样的数据库。当然数据库的名字随你便。表名和属性名就建议你修改咯。
具体的数据库文件我会在之后上传。你只需要通过诸如Navicat之类的数据库管理软件导入就行。顺便说一句，这里我用的是mysql数据库。
## jdbc驱动和数据库连接程序
* 你要在自己的项目里导入jdbc依赖包。
* 在Utils包中的DBUtil类，里面有个连接数据库的操作。
如果你的数据库名字是你自己喜欢的名字。
那么请你修改下面这条代码```private static final String URL = "jdbc:mysql://127.0.0.1:3306/数据库名?useSSL=false";```
中的```数据库名```为自己数据库的名称。
请修改```private static final String USER = "username";```中的```username```为你自己mysql数据库的用户名。```private static final String PASSWORD = "password";```
同时上面```password```为自己的密码。

## 修改ip
如果你只是在本地运行（同时在一台机子上运行`Server`和`Client`），那么你可以忽略这一步。如果你要在不同机器上通信。那么请修改`Clientthread`类中的`run`方法。其中有一句
`Socket socket = new Socket("127.0.0.1", 8888);`修改`127.0.0.1`位自己本机的ip。并且保证你的两台机子能够ping通。

## 运行
* 先运行```Server```，会看到如下界面：
balabala......
* 在运行`Client`，会看到如下界面：
balabala.....

# 如何生成`request`请求。
> 之后会更新`request`的包装，简要说明如何生成对数据库造作的请求类。
