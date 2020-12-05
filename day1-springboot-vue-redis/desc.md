##1.项目准备
    需求分析    模块  功能
    库表设计    数据库
    详细设计    流程图伪代码方式
    编码环节
        a.环境准备
        b.正式进入编码环节
    测试
    部署上线
    
##2.技术选型
    前端: vue + axios
    后端: springboot + mybatis + mysql + tomcat + Redis
    
##3.需求分析
    用户模块
        a.用户登录
        b.用户注册
        c .验证码实现
        d.欢迎xx用户展示
        e.安全退出
        f.员工列表展示
    员工模块
        g.员工添加
        h.员工删除
        i.员工修改
        j.员工列表加入redis缓存实现


##4.库表设计
    1.分析系统中有那些表   2.分析表与表之间关系   3.分析表中字段
    用户表
        id username realname password sex stauts regis
    员工表 id name imgPath (头像) salary age
    2.创建库表
    create table t_ user (
        id int (6) primary key auto_ increment ,
        username varchar (60) ,
        realname varchar (60) ,
        password varchar (50) ,
        sex varchar (4)，
        status varchar (4)，
        regsterTime timestamp
    );
    
    create table t_ emp (
        id int (6) primary key autoincrement, 
        name varchar (40) ,
        imgPath varchar (100)，
        salary double (10,2) ,
        age int (3)
    );
    
##5.编码环节
    a. 环境搭建
        springboot + mybatis + mysql  引入员工管理系统页面
        
        项目名： ems_vue
        项目中的包：
            src/main/java
                cn.sea.xxx
                       .utils
                       .dao
                       .service
                       ...
            src/main/resource
                application.yml(properties)     springboot核心配置文件
                application-dev.mml  测试配置
                application-prod.yml  生产配置
                cn/sea/mapper/  存放mybatis的mapper配置文件
                cn/sea/sql/  用来存放项目中的数据库文件
                static       用来存放静态资源
        项目编码：UTF-8
