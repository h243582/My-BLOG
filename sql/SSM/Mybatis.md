# 一、简介

## **1、什么是Mybatis**

![DuUtTXCoedFMZBa](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802091806.png)

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210716085749.gif)

- - MvBatis是一款优秀的持久层框架
  - 它支持定制化SQL、存储过程以及高级映射。
  - MyBatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集。
  - MyBatis 可以使用简单的XML或注解来配置和映射原生类型、接口和Java的POJO (Plain Old Javaobjects，普通老式Java对象）为数据库中的记录。
  - MyBatis本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation迁移到了google code，并且改名为MyBatis。
  - 2013年11月迁移到Github。

如何获得

maven仓库：

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
```

GitHub：https://github.com/mybatis/mybatis-3/releases

中文文档：https://mybatis.org/mybatis-3/zh/index.html

## **2、持久化**

- ​	持久化就是将程序的数据在**持久状态**和**瞬时状态**转化的过程
- ​	内存:断电即失
- ​	数据库(jdbc)，io文件 持久化。

## **3、持久层**

- - Dao层，Service层，Controller层....
  - 完成持久化工作的代码块
  - 层界限十分明显。

## **4、为什么需要Mybatis?·**

- - 方便
  - 传统的JDBC代码太复杂了。简化。框架。
  - 帮程序员帮数据存入数据库中
  - 不用Mybatis也可以。更容易上手。
  - 优点:

- 简单易学。灵活
- **sql和代码的分离**，提高了可维护性。
- 提供映射标签，支持对象与数据库的orm字段关系映射。提供对象关系映射标签，支持对象关系组建维护
- 提供xml标签，支持编写动态sql

- 最重要的是：使用的人多













# 二、第一个Mybatis程序

思路:搭建环境-->导入Mybatis-->编写代码-->测试!

```sql
CREATE TABLE `user`(
	`id` bigint not null primary key,
	`name` varchar(30) default null,
	`pwd` varchar(30)  default NULL
	
)engine=innodb default CHARSET=utf8;
```



## **1、导入依赖**

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.18</version>
</dependency>
```



## **2、创建一个模块**

### **2.1、编写mybatis的核心配置文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration核心配置文件-->
<configuration>
    <!---->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSl=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
<!--每一个Mapper.xml都需要在这里注册-->
    <mappers>
        <mapper resource="com/dao/UserMapper.xml"/>
    </mappers>
</configuration>
```



### **2.2、编写mybatis的工具类**

```java
//sqlSessionFactory --> sqlSession
public class MybatisUtils {
    private  static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用Mybatis第1获取sqlSessionFactory对象
            String resources = "mybatis-config.xml";
            InputStream inputStream  = Resources.getResourceAsStream(resources);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 既然有了sqlSessionFactory，顾名思义，我们就可以从中获得 SqLSession 的实例了。
    // sqlSession完全包含了面向数据库执行SQL命令所需的所有方法。
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```



### **2.3、编写代码**

#### **2.3.1、实体类**

```java
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pwd"})
@Data
public class User {
    private int id;
    private String name;
    private String pwd;
}
```



#### **2.3.2、DAO接口**

```java
public interface UserDao {
    List<User> getUserList();
}
```



#### **2.3.3、执行sql代码**

**接口实现类由原来的UserDaolmpl转变为一个Mapper配置文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace=绑定一个对应的Dao/Mapper接口-->
<mapper namespace="com.dao.UserDao">
    <select id="getUserList" resultType="com.pojo.User">
        select * from mybatis.user;
    </select>
</mapper>
```



#### **2.3.4、测试**

```java
@Test
public void test(){
    //获得SqlSession对象
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    
    //方式一：getMapper
    UserDao mapper = sqlSession.getMapper(UserDao.class);
    List<User> userList = mapper.getUserList();
    
    //方式二：规定太死了
    //List<User> userList = sqlSession.selectList("com.dao.UserDao.getUserList");
    
    for(User u : userList){
        System.out.println(u);
    }
    
    
    sqlSession.close();
}
```



### **2.4、注意点**

1. #### **MapperRegistry是什么?**


org.apache.ibatis.binding.BindingException: Type interface com.kuang.dao.UserDao is not known to thMapperRegistry.

翻译：MapperRegistry是什么?

**核心配置文件中注册mappers：在pom.xml中插入这段代码，用于识别出Mapper**

```xml
<!--maven由于他的约定大于配置，我们之后可以能遇到我们写的配置文件，无法被导出或者生效的问题，解决方-->
<!--在bui7d中配置resources，来防止我们资源导出失败的问题-->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```



1. #### **配置文件没有注册**

2. #### **绑定接口错误**

3. #### **方法名不对**

4. #### **返回类型不对**

5. #### **三个类注意点**


​	**SqlSessionFactoryBuilder**

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

​	**SqlSessionFactory**

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

​	**SqlSession**

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要











# 三、CRUD

## **1、namespace**

==namespace中的包名要和Dao/mapper接口的包名一致！==

## **2、select**

选择，查询语句

**id：**就是对应的namespace中的方法名

**resultType：** Sql语句执行的返回值

**parameterType：**参数类型

```java
public interface UserMapper {
    //根据id查询用户
    User getUserById(int id);
}
```

```xml
<mapper namespace="com.dao.UserMapper">
    <select id="getUserById" parameterType="int" resultType="com.pojo.User">
        select * from mybatis.user where id = #{id};
    </select>
</mapper> 
```

```java
@Test
public void getUserId(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    System.out.println(mapper.getUserById(1));
}
```



## **3、Insert**

**//增删改查需要提交事务**

**sqlSession.commit();**

```java
public interface UserMapper {
    //插入用户
    void addUser(User user);
}
```

```xml
<mapper namespace="com.dao.UserMapper">
    <insert id="addUser" parameterType="com.pojo.User">
        insert into mybatis.user(id, name, pwd) values(#{id},#{name},#{pwd});
    </insert>
</mapper> 
```

```java
//增删改查需要提交事务
@Test
public  void addUser(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    mapper.addUser(new User(5,"Yoshinaga","2543582"));
    //增删改查需要提交事务
    sqlSession.commit();
    test();
}
```

## **4、update**

**//增删改查需要提交事务**

**sqlSession.commit();**

```java
public interface UserMapper {
    //修改用户
    void updateUser(User user);
}
```

```xml
<mapper namespace="com.dao.UserMapper">
        <update id="updateUser" parameterType="com.pojo.User">
        update mybatis.user set name=#{name},pwd=#{pwd}  where id=#{id};
    </update>
</mapper> 
```

```java
//增删改查需要提交事务
@Test
public void updateUser(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
    userMapper.updateUser(new User(1,"hah","1231564646"));
    sqlSession.commit();
    test();
}
```



## **5、delete**

**//增删改查需要提交事务**

**sqlSession.commit();**

```java
public interface UserMapper {
    //删除用户
    void deleteUser(int id);
}
```

```xml
<mapper namespace="com.dao.UserMapper">
    <delete id="deleteUser" parameterType="int">
        delete from user where id=#{id}
    </delete>
</mapper>
```

```java
//增删改查需要提交事务
@Test
public void deleteUser(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    userMapper.deleteUser(2);
    sqlSession.commit();
    test();
}
```



## **6、错误分析**

- - 标签不要匹配错
  - resource绑定mapper，需要使用路径!
  - 程序配置文件必须符合规范!
  - NullPointerException，没有注册到资源!
  - 输出的xml文件中存在中文乱码问题!
  - maven资源没有导出问题!

## **7、万能map**

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们应当考虑使用Map！

```java
User getUserById2(Map<String,Object> map);
```

```xml
<select id="getUserById2" parameterType="map" resultType="com.pojo.User">
    select * from mybatis.user where id = #{id} and name=#{name};
</select>
```

```java
@Test
public void getUserId2(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    HashMap<String, Object> map = new HashMap<>();
    map.put("id",10);
    map.put("name","4234");
    System.out.println(mapper.getUserById2(map));
}
```

- - Map传递参数，直接在sql中取出key即可!
  - 对象传递参数，直接在sql中取对象的属性即可!
  - 只有一个基本类型参数的情况下，可以直接在sql中取到!
  - 多个参数用Map，或者注解!

## **8、模糊查询**

Java代码执行的时候，传递通配符% %

```java
List<User> userList = mapper.getuserLike("%李%");
```



在sql拼接中使用通配符!

```xml
select * from mybatis.user where name like #{value};

select * from mybatis.user where name like CONCAT('%',#{name},'%')
```













# 四、配置解析

## **1、核心配置文件**

mybatis-config.xml

MyBatis的配置文件包含了会深深影响MyBatis行为的设置和属性信息。

![jqdUk3WMv5X6Y7h](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101157.png)

### **2、环境配置（environments）**

注意一些关键点:

- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。
- 事务管理器的配置（比如：type="JDBC"）。
- 数据源的配置（比如：type="POOLED"）。

MyBatis可以配置成适应多种环境

不过要记住:尽管可以配置多个环境，但每个SqlSessionFactory实例只能选择一种环境。

学会使用配置多套运行环境!

Mybatis默认的事务管理器就是JDBC，连接池: POOLED

### **3、属性（properties）**

我们可以通过properties属性来实现引用配置文件

这些属性都是可外部配置且可动态替换的，既可以在典型的Java属性文件中配置，亦可通过properties元素的子元素来传递。【db.properties】

#### **3.1、编写一个配置文件**

```xml
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSl=false&amp;useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true
username=root
password=123456
```



#### **3.2、在核心配置文件中映入**

**（写在configuration里面的最上面）**

```xml
<!--引入外部配置文件-->
<properties resource="db.properties">
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</properties>
```



- 在xml中所有的标签可以规定其顺序，如果顺序错了，就会提示下面的错误：

The content of element type "configuration" must match "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)".

所以configuration里面**前后**顺序是：

> properties > settings > typeAliases > typeHandlers > objectFactory >objectWrapperFactory > reflectorFactory > plugins > environments>databaseIdProvider > mappers

- 可以直接引入外部文件
- 可以在其中增加一些属性配置
- 如果两个文件有同一个字段，优先使用外部配置文件的!

### **4、类型别名（typeAliases）**

#### **4.1、类**

**类型别名是为Java类型设置一个短的名字。**

**存在的意义仅在于用来减少类完全限定名的冗余。**

```xml
<typeAliases>
    <typeAlias type="com.pojo.User" alias="User"/>
</typeAliases>
```

#### **4.2、包**

**也可以指定一个包名，MyBatis 会在包名下面搜索需要的Java Bean，**

**比如:扫描实体类的包，它的默认别名就为这个类的类名，建议首字母小写!** 

```xml
<typeAliases>
    <package name="com.pojo"/>
</typeAliases>
```



#### **4.3、注意**

在实体类比较少的时候，使用第一种方式

如果实体类十分多，建议使用第二种，但是不能自定义名字

第一种可以DIY别名，第二种则不行，如果非要改，需要在实体上增加注解

```xml
@Alias("hello")
```



### **5、设置（settings）**

![qhVfnT1M8BNireW](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101236.png)

### **6、其他**

- typeHandlers(类型处理器)
- objectFactory(对象工厂)
- plugins插件

- - mybatis-generator-coreo 
  - mybatis-plus
  - 通用mapper

### **7、映射器**

**MapperRegistry:注册绑定我们的Mapper文件**

#### 方式一：xml

```xml
<!--每一个Mapper.xml都需要在这里注册-->
<mappers>
    <mapper resource="com/dao/UserMapper.xml"/>
</mappers>
```



#### 方式二：class

```xml
<mappers>    
    <mapper class="com.dao.UserMapper"/>
</mappers>
```

方式二注意点：

接口和他的Mapper配置文件必须同名!

接口和他的Mapper配置文件必须在同一个包下!

#### 方式三：使用扫描包进行注入绑定

```xml
<mappers> 
    <package name="com.dao"/>
</mappers>
```



方式三注意点和方式二注意点一样

### **8、生命周期和作用域**![AkgpPOIuEB65wlt](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101258.png)

生命周期，和作用域，是至关重要的，因为错误的使用会导致非常严重的**并发问题**。

`sqlSessionFactory`:

- ​	一旦创建了SqlSessionFactory，就不再需要它了
- ​	局部变量

`sqlSessionFactory`:

- ​	说白了就是可以想象为: 数据库连接池
- ​	**SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。**
- ​	因此 SqlSessionFactory的最佳作用域是应用作用域。
- ​	最简单的就是使用**单例模式**或者静态单例模式。

`SqlSession`

- ​	连接到连接池的一个请求!
- ​	SqlSession的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
- ​	用完之后需要赶紧关闭，否则资源被占用![image-20210307124351598](https://i.loli.net/2021/03/07/pWc4vLE9szAMmVP.png)

这里面的每一个Mapper，就代表一个具体的业务!



# 五、解决属性名和字段名不一致

- 数据库中的字段:    ![image-20210307132342011](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101341.png)

- java实体类中的字段:<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101344.png" alt="image-20210309125638581" style="zoom:80%;" />

  结果：未找到password的值![image-20210311165257407](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101346.png)

**解决方案**

### 1、起别名

查询中的字段起别名，最蠢的方法![image-20210311165503259](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101358.png)

### 2、resultMap

**结果集映射,哪里不一样转哪里**

| 数据库中的字段 | 实体类中的字段 |
| :------------- | -------------- |
| id             | id             |
| name           | name           |
| pwd            | password       |

![J5Mk4KZ6G3qmWbt](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101537.png)

### 3、自动驼峰命名

![vgQ2AnSNd1OxciV](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101558.png)

```xml
<settings>
    <!--标准日志工厂-->
    <setting name="logImpl" value="STDOUT_LOGGING"/>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```



# 六、日志

## 6.1、日志工厂

如果一个数据库操作，出现了异常，我们需要排错。日志就是最好的助手!

**曾今：sout；debug**

**现在：日志工厂**

- SLF4j
- LOG4J **[掌握]**
- LOG4j2
- JDK_LOGGING
- COMMONS_LOGGING
- STDOUT_LOGGING**[掌握]**
- NO_LOGGING

`在Mybatis中具体使用那个一日志实现，在设置中设定!`



### 6.1.1、STDOUT_LOGGING

标准日志

```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```



### 6.1.2、LOG4J

- LOG4J是Apache的一个开源项目，通过使用LOG4J，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件
- 我们也可以控制每一条日志的输出格式;
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程。
- 通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。

#### **1、导包**

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```



#### **2、log4j.properties**

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG, console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.conversionPattern=[%c]-%m%n
#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/HeYuFei.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
#已志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```



#### **3、配置log4j为日志的实现**

​	**写在连接数据库的那个配置文件中**

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

#### **4、简单使用**

1.在要使用Log4j的类中，导入包 

​	`import org.apache.log4j.Logger;`

2.日志对象，参数为当前类的class

```java
static Logger logger = Logger.getLogger((UserMapperTest.class));
```

# 七、分页

## **1、概念**

​	减少数据的处理量

## **2、Limit分页**

### **2.1、源码**

```sql
select * from user limit 2,3;
```



### **2.2、使用步骤、**

使用Mybatis实现分页，

- 核心SQL1.接口

  ```java
  //分页
  List<User> getUserByLimit(Map<String,Integer> map);
  ```

- Mapper.xml

  ```xml
  <mapper namespace="com.dao.UserMapper">
  
      <resultMap id="UserMapResultMap" type="User">
          <!-- column数据库中的字段，property实体类中的属性   -->
          <result column="pwd" property="password"/>
      </resultMap>
      
      <select id="getUserByLimit" parameterType="map" resultType="user" resultMap="UserMapResultMap">
          select * from user limit #{startIndex},#{pageSize}
      </select>
  </mapper>
  
  ```

- 测试

  

## 3、RowBounds分页(java)

### **3.1、源码**

从2开始，每页显示3个

```sql
select * from user limit 2,3;
```



### **3.2、使用步骤、**

使用Mybatis实现分页，**不再使用SQL实现分页**

- 核心SQL1.接口

  ```java
  //分页
  List<User> getUserByRowBounds();
  ```

- Mapper.xml

  ```xml
  <mapper namespace="com.dao.UserMapper">
      
  	<select id="getUserByRowBounds" resultMap="UserMapResultMap">
          select * from user;
      </select>
  </mapper>
  ```

- 测试

  ```java
  SqlSession sqlSession = MybatisUtils.getSqlSession();
  //通过java代码的层面的分页
  //RowBounds从2开始，每页显示3个
  RowBounds rowBounds = new RowBounds(2, 3);
  
  List<User> userList = sqlSession.selectList("com.dao.UserMapper.getUserByBowBounds",null,rowBounds);
  for(User user:userList){
  	System.out.println(user);
  }
  ```

## **3、插件分页**

![srXVPnhuFW3HmB1](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101818.png)

> https://pagehelper.github.io/

了解即可，万一以后公司的架构师，说要使用，你需要知道它是什么东西! 





# 八、使用注解开发

## **1、面向接口编程**

在真正的开发中，面向接口编程更广泛
**根本原因∶**

​	**`解耦`，可拓展，提高复用，分层开发中，上层不用管具体的实现，大家都遵守共同的标准，使得开发变得容易，规范性更好**



**关于接口的理解:**
	**`定义`(规范，约束）与`实现`(名实分离的原则)的分离。**



**接口应有两类:**

1. 是对于个体的抽象，它可对应为一个抽象体(`abstract class`);
2. 是对于个体某一方面的抽象，即形成一个抽象面(`interface`) ;一个体有可能有多个抽象面。抽象体与抽象面是有区别的。

## **2、使用注解开发**

### **2.1、注解在接口上的实现**

```java
@Select("select * from user")
List<User> getUsers();

//方法存在多个参数，所有的参数必须加上@Param注解
@Select("select * from user where id = #{id2}")
User getUserById(@Param("id2") int id123);
```

​	方法存在**多个参数**，所有的参数必须**加上@Param注解**，因为sql中`#{id}`必须对应`Param("id")`的名(**名字相同**)，而 `d123`中变量名是不对应的

### **2.2、核心配置文件绑定接口**

```xml
<mappers>
    <mapper class="dao.UserMapper" />
</mappers>
```

### **2.3、测试使用**

```java
SqlSession sqlSession = MybatisUtils.getSqlSession();
UserMapper mapper = sqlSession.getMapper(UserMapper.class);
List<User> users = mapper.getUsers();
for (User user:users){
    System.out.println(user);
}

sqlSession.close();
```



## **3、CRUD** 

接口中：

```java
//方法存在多个参数，所有的参数必须加上@Param注解
@Select("select * from user where id = #{id2}")
User getUserById(@Param("id2") int id123);

@Insert("insert into user(name,pwd) values(#{name},#{password});")
int addUser(@Param("name") String name,@Param("password") String password);

@Update("update user set name=#{name},pwd=#{password} where id=#{id}")
//    int updateUserById(User user);
int updateUserById(@Param("name") String name, @Param("password") String pwd ,@Param("id") Integer id);

@Delete("delete from user where id=#{id}")
int deleteUserById(Integer id);
```



测试中：

```java
SqlSession sqlSession = MybatisUtils.getSqlSession();
UserMapper mapper = sqlSession.getMapper(UserMapper.class);

int i = mapper.addUser("何昱123123飞","243");

sqlSession.commit();
sqlSession.close();
```

**千万别忘了commit提交**



## **4、关于@Param()注解**

基本类型的参数或者String类型，需要加上

引用类型不需要加
如果只有一个基本类型的话，可以忽略，但是建议大家都加上!

我们在SQL中引用的就是我们这里的@Param()中设定的属性名!





# 九、------执行过程

- 本质: 反射机制实现

- 底层: 动态代理!

  ![2cLvTksOIPwzRnb](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101907.png)

Mybatis详细执行流程

![ore3jGA4XtRUTun](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802101928.png)

# 十、多对一处理

​		多个学生，对应一个老师<br>		对于学生这边而言，关联   ..多个学生，关联一个老师【多对一】<br>		对于老师而言,  集合，一个老师，有很多学生【一对多】

​		**sql多对一查询方式**

​				**·子查询**

​				**·联表查询**

## 	1、环境搭建

### 		1.1、实体类

```java
public class Student {
    private Integer id;
    private  String name;
    // 学生需要关联一个老师
    private Teacher teacher;
}

public class Teacher {
    private Integer id;
    private String name;
}
```

### 		1.2、SQL创建表

```sql
create  table `teacher`(
`id` int not null primary key,
`name` varchar(30) default null
)engine=innodb default CHARSET=utf8;


insert into teacher(`id`,`name`) values (1, '秦老师');

create table `student` (
	`id` int not null,
	`name` varchar ( 30 ) default null,
	`tid` int ( 10 ) default null,
	primary key ( id ),
	key `fktid` ( tid ),
	constraint `fktid` foreign key ( `tid` ) references `teacher` ( `id` ) 
) engine = innodb default charset = utf8


insert into `student`(`id`,`name`,`tid`)values ('1','小明','1');
insert into `student`(`id`,`name`,`tid`)values ('2','小红','1');
insert into `student`(`id`,`name`,`tid`)values ('3','小张','1');
insert into `student`(`id`,`name`,`tid`)values ('4','小李','1');
insert into `student`(`id`,`name`,`tid`)values ('5','小王','1');
```

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102029.png" alt="ldB7mGszSUYPXeZ" style="zoom:80%;" />

1. ​		导入lombok

2. ​		新建实体类Teacher，Student

3. ​		建立Mapper接口

4. ​		在核心配置文件中绑定注册我们的Mapper接口或者文件!【方式很多，随心选】

5. ​		测试查询是否能够成功!
   

   ## 2、按照 嵌套查询 方式

   `思路：`

   ​	**查询所有的学生信息**
   ​	**根据查询出来的学生的tid，寻找对应的老师！ 子查询**

```xml
<mapper namespace="com.dao.StudentMapper">
    <select id="getStudent" resultMap="StudentTeacher">
        select * from student
    </select>

    <resultMap id="StudentTeacher" type="Student">
        <!--property是实体类的属性，column是数据库的属性-->
        <result property="id" column="id" />
        <result property="name" column="name" />
        <!--复杂的属性·我们需要单独处理  对象:association   集合:collection  -->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>

    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id = #{id}
    </select>
</mapper>
```

## 	3、按照 连表查询 方式

>**一定要起别名**
>
>student.id  sid     **或者**  student.id  as  sid

```xml
<mapper namespace="com.dao.StudentMapper">
<!--    按照结果嵌套处理-->
    <select id="getStudent2" resultMap="StudentTeacher2">
        select student.id sid,student.name sname,teacher.name tname from student,teacher where student.tid=teacher.id
    </select>

    <resultMap id="StudentTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>
</mapper>
```









# 十一、一对多处理

​		比如一个老师有多个学生

​		对于老师而言，就是一对多关系！

## 	1、环境搭建

### 		1.1、实体类

```java
public class Student {
    private Integer id;
    private  String name;
    private Integer Tid;
}
public class Teacher {
    private Integer id;
    private String name;
    //一个老师有多个学生
    private List<Student> students;
}
```

![TergJkE5alyM247](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102057.png)

## 2、按照 嵌套查询 方式

```xml
<select id="getTeacher3" resultMap="TeacherStudent3">
    select * from teacher where id = #{tid}
</select>

<resultMap id="TeacherStudent3" type="Teacher">
    <--此处javaType可以不写-->
    <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTid" column="id"/>
</resultMap>

<select id="getStudentByTid" resultType="Student">
    select * from student where tid = #{tid}
</select>
```

## 3、按照 连表查询 方式

```xml
<select id="getTeacher2" resultMap="TeacherStudent">
        select student.id sid,student.name sname,teacher.name tname,teacher.id tid
        from student,teacher
        where student.tid=teacher.id and teacher.id = #{tid}
    </select>
    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <!--复杂的属性，我们需要单独处理 对象:association  集合:collection
            javaType="" 指定属性的类型!
            ofType="" 指定集合中的泛型类型-->
        <collection property="students" javaType="List" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```

### 对象包含属性是对象

![image-20210709174130165](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210709174130.png)<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210709174211.png" alt="image-20210709174210828"  />

```xml
<mapper namespace="com.ssm.dao.DanZiMapper">
    <select id="queryAll" resultMap="myDanZi">
        select danzi.id,danzi.name,danzi.content,danzi.bxDate,danzi.money,dept.deptName from `ssm-01-login`.danzi join `ssm-01-login`.dept  on danzi.deptId = dept.id
    </select>
    <resultMap id="myDanZi" type="com.ssm.pojo.DanZi">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="content" column="content"/>
        <result property="bxDate" column="bxDate"/>
        <result property="money" column="money"/>
        <!--复杂的属性·我们需要单独处理  对象:association   集合:collection  -->
        <association property="dept" resultMap="myDept" javaType="com.ssm.pojo.Dept"/>
    </resultMap>
    <resultMap id="myDept" type="com.ssm.pojo.Dept">
        <result property="id" column="id"/>
        <result property="deptName" column="deptName"/>
    </resultMap>
</mapper>
```



## 4、小节

​	![Lc3VnZ1WIpuhONH](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102149.png)





# 十二、动态SQL

**所谓的动态SQL，本质还是SQL语句，只是我们可以在SQL层面，去执行一个逻辑代码**

​	动态SQL就是指 根据不同的条件 生成不同的SQL语句

​	利用动态SQL这一特性可以彻底摆脱这种痛苦。

>动态 SQL元素和JSTL或基于类似 XML的文本处理器相似。在MyBatis 之前的版本中，有很多元素需要花时间了解。MyBatis3 大大精简了元素种类，现在只需学习原来一半的元素便可。MyBatis 采用功能强大的基于OGNL的表达式来淘汰其它大部分元素。
>
>-  if
>- choose (when，otherwise)
>- trim (where，set)
>- foreach

## 1、搭建环境

## 	1.1、实体类

```java
@Data
public class Blog {
    private Integer id;
    private String author;
    private Date createTime;
    private Integer views;
}
```

## 	1.2、SQL表创建

```sql
create table blog (
	`id` varchar (50) not null comment '博客id' primary key,
	`title` varchar (100) not null comment '博客标题',
	`author` varchar (30) not null comment '博客作者',
	`create_time` datetime not null comment '创建时间',
	`views` int (30) not null comment '浏览量'
) engine = innodb default charset = utf8
```

## 2、IF

**里面加注释会报错**

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where 1=1
        <if test="title!=null">
            and title = #{title}
        </if>
        <if test="author!=null">
            and author = #{author}
        </if>
</select>
```

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where 1=1
        <if test="title!=null">
            and title = #{title}
        </if>
        <if test="author!=null">
            and author = #{author}
        </if>
</select>
```

```java
SqlSession sqlSession = MybatisUtils.getSqlSession();
BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
Map<String,Object> map = new HashMap<>();
//        map.put("title","何昱飞的说书4");
map.put("author","何昱飞222");
List<Blog> blogs = mapper.queryBlogIF(map);
for (Blog b : blogs){
    System.out.println(b);
}

sqlSession.close();
```

## 3、choose(when,where)

**(相当于switch)，因为它只会执行从上到下得到第一个分支，自动break，所以and不用写上去**

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <choose>
            <when test="title!=null">
                title = #{title}
            </when>
            <when test="author!=null">
                author = #{author}
            </when>
            <otherwise>
                views = #{views}
            </otherwise>
        </choose>
    </where>
</select>
```



## 4、trim(where,set)

**可以自定义**

### 4.1、where

​	**只有第一个and不用加**

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog
    <where>
        <if test="title!=null">
            title = #{title}
        </if>
        <if test="author!=null">
           and author = #{author}
        </if>
    </where>
</select>
```

### 4.2、set(用于update)

用于update，自动去除逗号

**必须要有一个以上的if成立 (即必须修改一个以上的字段)**

```xml
<update id="updateBlog" parameterType="map">
    update blog
    <set>
        <if test="title!=null">
            title = #{title},
        </if>
        <if test="author!=null">
            author = #{author}
        </if>
    </set>
    where id = #{id}
</update>
```

测试类

```java
SqlSession sqlSession = MybatisUtils.getSqlSession();
BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
Map<String,Object> map = new HashMap<>();
map.put("id","3626ba5fa1");
//        map.put("title","改之后的主体");
map.put("author","改之后的作者2");
//        map.put("views","8888");
mapper.updateBlog(map);

sqlSession.commit();
sqlSession.close();
```

### 4.3、trim



## 5、SQL片段(sql)

**把一段代码打包，然后就可以多次使用了**

#### 1.使用SQL标签抽取公共的部分

```xml
<sql id="myUpdate">
    update blog
    <set>
        <if test="title!=null">
            title = #{title},
        </if>
        <if test="author!=null">
            author = #{author}
        </if>
    </set>
    where id = #{id}
</sql>
```

#### 2.在需要使用的地方使用include标签引用

```xml
<update id="updateBlog" parameterType="map">
    <include refid="myUpdate"/>
</update>
```

## 6、Foreach

- collection="ids"      从map集合拿出ids对应的值(集合)
- item="myid"      从集合中取出的值，对应下面的#{myid}，放在其中
- open="and  ("       转换成SQL的开始值，千万别忘记空格！这里的and可以不写
- close=")"       转换成SQL的结束值
- separator="or"       转换成SQL的item间的分隔符

```xml
<!-- 我们现在传递一个万能的map .这map中可以存在一个集合!-->
<select id="queryBlogForeach" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <foreach collection="ids" item="myid" open="and (" close=")" separator="or">
            id = #{myid}
        </foreach>
    </where>
</select>
<!--select * from blog WHERE ( id = ? or id = ? )-->
```

测试类

```java
@Test
public void queryBlogForeach(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    
    Map<String,Object> map = new HashMap<>();
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    map.put("ids",list);

    List<Blog> blogs = mapper.queryBlogForeach(map);
    for (Blog b: blogs) {
        System.out.println(b);
    }
    sqlSession.close();
}
```

# 十三、缓存

## 1、简介

>查询：连接数据库，耗资源
>			一次查询的结果，给他暂存在一个可以直接取到的地方      -->内存：缓存
>
>我们再次查询相同数据的时候，直接走缓存，就不用走数据库了

### 	1.1、什么是缓存[Cache ]?

​			。存在内存中的临时数据。
​			。从缓存中查询，从而提高查询效率，解决了**高并发系统**的**性能**问题。

​			。高并发、高可用、高性能

​			。**读写分离，主从复制**可以解决一些并发性的问题

### 	1.2、为什么使用缓存?

​			。**减少和数据库的交互次数**，减少系统开销，提高系统效率。

### 	1.3、什么样的数据能使用缓存?

​			**经常查询并且不经常改变的数据**。【可以使用缓存】



## 2、Mybatis缓存

- MyBatis包含一个非常强大的查询缓存特性，它可以非常方便地**定制和配置缓存**。缓存可以极大的提升查询效率。
- MyBatis系统中默认定义了两级缓存:**一级缓存**和**二级缓存**
- 默认情况下，只有一级缓存开启。(SqlSession级别的缓存，也称为本地缓存)。二级缓存需要手动开启和配置，他是基于namespace级别的缓存。
- 为了提高扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存

## 3、一级缓存(低作用域)

### 3.1、概念

一级缓存也叫**本地缓存**: **一个sqlSession内部**
与数据库同一次会话期间查询到的数据会放在本地缓存中。
以后如果需要获取相同的数据，直接从缓存中拿，不必再去查询数据库;

### 3.2、缓存失效的情况:

1、查询不同的东西

2、如果**sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存**，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。**二级缓存**也是。

![LFkwYuqeKoimcP3](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102248.png)

3.查询不同的Mapper.xml

4.手动清理缓存!

![kGhFHqEWXB9sNS4](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102346.png)

## 4、二级缓存 (高作用域)

### 4.1、概念

- 二级缓存也叫**全局缓存**，基于**namespace**级别的缓存，一个名称空间(接口)，对应一个二级缓存; 一个**SqlSession**里面的所有**UserMapper**
- 如果某个SqlSession去执行相同 mapper下sql，只要执行commit提交，就会清空该mapper下的二级缓存区域的数据。

### 4.2、工作机制

**会话关闭了，一级缓存中的数据被保存到二级缓存中;**

### 4.3、开启方法

#### 	4.3.1、开启全局缓存

​		对在**此配置文件下的所有cache** 进行全局性开/关设置，默认是**true**

​		但是写上最好。可读性

```xml
<settings>
    <setting name="cacheEnabled" value="true"/>
</settings>
```

#### 	4.3.2、然后mapper中设置

![ZdTv5rKcMlbW43j](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102415.png)



- **eviction**清除策略：创建了**FIFO**缓存

  - LRU:  最近最少使用: 移除最长时间不被使用的对象。【默认LRU】
  - FTFO:  先进先出:按对象进入缓存的顶序来移除它们。
  - SCET:  软引用:基于垃圾回收器状态和软引用规则移除对象。
  - WEAK:  弱引用:更积极地基于垃圾收集器状态和弱引用规则移除对象。

- **flushInterval**刷新间隔：每隔**60秒**刷新,

- **size**引用数目：最多可以存储结果对象或列表的**512**个引用【默认1024】

- **readOnly**只读：返回的对象被认为是**只读**。

  - 如果设置为只读，性能会快很多，但是不安全

  `因此对它们进行可能会在不同线程中的调用者产生冲突。`


![KjSBDT3ymHMkQOr](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802102741.png)





#### 4.4、其他使用

##### 4.4.1、给某个增删查改设置不使用缓存

```xml
useCache="true"
```

## 5、ehcache

#### 5.1、导入依赖

```xml
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.1</version>
</dependency>
```

#### 5.2、使用

![ztONUuSlA7dWqgm](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802104013.png)





##### 5.3、文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
        diskStore:为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下;
        user.home -精户主昌录
        user.dir-好户当前工作卢录
        java.io.tmpdir - 默认临时文件路径
    -->
    <diskStore path="./tmpdir/Tmp_EhCache"/>
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259288"
            memoryStoreEvictionPolicy="LRU"/>
    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>

    <!--
        defaultCache:默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
    -->
    <!--
        name:级存名称。
        maxELementsInMemory :缓存最大数旨maxELementsOnDisk:硬盘最人锾存个凝。
        eternal:对象是否永久有效,一但设置了. timeout将不起作用。overfLowToDisk:是否保存到磁盘，当系经当翅时
        timeToIdeSeconds:没置对象在失效前的允许闲置时阿（单位;秒)。仅“ieternalzfalse对象不是永久有效时使用，可选属性，默认值是，也就是!
        timeToLiveseconds : 设置对象在失效前允许存话时网（总位:秒》.最大时问介了创建时阿和失效时间之问。仅当ieternaLzfalse对象不是永久有效时
        dishPersistent:是否级在虚拟机重启劓数杯l whether the disk store persists between restarts of the virtuol Nachine.The defoulb
        dishSpoolBufferSizeNB:这个参数没FDishStore(磁盘缓存）的缓存区大小。默认是3eNB。每个Cache都应该有白己的一个缓冲区。
        diskExpiryThreadIntervalSeconds:磁盘失效线程运行时间间隔，默认是120秒。
        memoryStoreEvictionPoLicy:当达到maxELementsInNlemory限制时。，Ehcache将会根据指定的策略去清理内存。默认策略处LRU（最近最少使用).
        clearonFLush:内存数最最天时是否清除。
        memoryStoreEvictionPolicy:可选策略有:LRU（最近最少使用，默认策略）、FIFO《先进先出)、LFU(最少访问次数)。
        FIFO，first in first out，这个是大家最熟的,先进先出。
        LFU， less Frequently Used，就是上面例了中使用的策略。直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元索有一个hit属性，hith
        LRU，Least Recently Used，最近最少使川的，缓存的元素有一个时伺戳，当级存容量满了，而又需要腾出地方米级存新的元素的时候，那么现有级存
    -->

</ehcache>
```

