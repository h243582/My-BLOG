# 一、概念

## 1、如何简化Java开发

为了降低Java开发的复杂性，Spring采用了以下4种关键策略：

1、基于POJO的轻量级和最小侵入性编程，所有东西都是bean；

2、通过IOC，依赖注入（DI）和面向接口实现松耦合；

3、基于切面（AOP）和惯例进行声明式编程；

4、通过切面和模版减少样式代码，RedisTemplate，``xxxTemplate；`

## 2、**Spring Boot的主要优点：**

- 为所有Spring开发者更快的入门
- **开箱即用**，提供各种默认配置来简化项目配置
- 内嵌式容器简化Web项目
- 没有冗余代码生成和XML配置的要求

## 3、更改 启动显示

只需一步：到项目下的 resources 目录下新建一个banner.txt 即可。

图案可以到：https://www.bootschool.net/ascii 这个网站生成，然后拷贝到文件中即可！

![image-20210722153515100](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210722153515.png)

# 二、原理研究

> ==**pom.xml**==

## 父依赖

其中它主要是依赖一个父项目，主要是管理项目的资源过滤及插件！

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.5.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

点进去，发现还有一个父依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.2.5.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

这里才是真正管理SpringBoot应用里面所有依赖版本的地方，SpringBoot的版本控制中心；

**以后我们导入依赖默认是不需要写版本；但是如果导入的包没有在依赖中管理着就需要手动配置版本了；**



## 启动器 spring-boot-starter

```xml
<dependency>
    <!-- 启动器-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <!-- web场景启动器 -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**springboot-boot-starter-xxx**：就是spring-boot的场景启动器

**spring-boot-starter-web**：帮我们导入了web模块正常运行所依赖的组件；

SpringBoot将所有的功能场景都抽取出来，做成一个个的starter （启动器），只需要在项目中引入这些starter即可，所有相关的依赖都会导入进来 ， 我们要用什么功能就导入什么样的场景启动器即可 ；我们未来也可以自己自定义 starter；



#### `@SpringBootApplication`主启动类(默认)

作用：标注在某个类上说明这个类是SpringBoot的主配置类 ， SpringBoot就应该运行这个类的main方法来启动SpringBoot应用；



 ![image-20210722155916737](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210722155916.png)

**这个类主要做了以下四件事情：**

1、推断应用的类型是普通的项目还是Web项目

2、查找并加载所有可用初始化器 ， 设置到initializers属性中

3、找出所有的应用程序监听器，设置到listeners属性中

4、推断并设置main方法的定义类，找到运行的主类

#### `@ComponentScan`自动扫描bean

这个注解在Spring中很重要, 它对应XML配置中的元素。

作用：自动扫描并加载符合条件的组件或者bean ， 将这个bean定义加载到IOC容器中

#### `@SpringBootConfiguration`表示配置类

作用：SpringBoot的配置类 ，**标注在类上** ， 表示这是一个 SpringBoot的配置类；

**进入这个注解查看**

```java
//@Configuration，说明这是一个配置类 ，配置类就是对应Spring的xml 配置文件；
@Configuration
public @interface SpringBootConfiguration {}

// 点上面 进去 得到 下面的 @Component

//Component：启动类本身也是Spring中的一个组件而已，负责启动应用！
@Component
public @interface Configuration {}
```



#### `@EnableAutoConfiguration`开启自动配置

SpringBoot可以自动帮我们配置 ；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能，这样自动配置才能生效；

**点进注解继续查看：**

```java
//@import ：Spring底层注解@import， 给容器中导入一个组件
//Registrar.class：将主启动类的所在包及包下面所有子包里面的所有组件扫描到Spring容器
@Import({Registrar.class})
//@AutoConfigurationPackage ：自动配置包
public @interface AutoConfigurationPackage {
}
```

这个分析完了，退到上一步，继续看

> `@Import({AutoConfigurationImportSelector.class})` ：**给容器导入组件** ；

AutoConfigurationImportSelector ：自动配置导入选择器，那么它会导入哪些组件的选择器呢？我们点击去这个类看源码：。。。。。。



所以，自动配置真正实现是从classpath中搜寻所有的 META-INF/spring.factories 配置文件 ，并将其中对应的 org.springframework.boot.autoconfigure. 包下的配置项，通过反射实例化为对应标注了 @Configuration的JavaConfig形式的IOC容器配置类 ， 然后将这些都汇总成为一个实例并加载到IOC容器中。



**结论：**

1. SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值
2. 将这些值作为自动配置类导入容器 ， 自动配置类就生效 ， 帮我们进行自动配置工作；
3. 整个J2EE的整体解决方案和自动配置都在springboot-autoconfigure的jar包中；
4. 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration）, 就是给容器中导入这个场景需要的所有组件 ， 并配置好这些组件 ；
5. 有了自动配置类 ， 免去了我们手动编写配置注入功能组件等的工作；





# 三、**yaml**语法学习

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210722173342.gif)

## 1、配置文件**properties**

SpringBoot使用一个全局的配置文件 ， 配置文件名称是固定的

- **application.properties**

- - 语法结构 ：key=value

- application.yml(无)

- - 语法结构 ：key：空格 value

**配置文件的作用 ：**==修改SpringBoot自动配置的默认值==，因为SpringBoot在底层都给我们自动配置好了；

比如我们可以在配置文件中修改Tomcat 默认启动的端口号！测试一下！

```
server.port=8081
```



## 2、yaml(**与xml同等级**)概述

YAML是 "YAML Ain't a Markup Language" （**YAML不是一种标记语言**）的递归缩写

在开发这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）

==**这种语言以数据**作为作为中心，而不是以标记语言为重点！==

##### 传统xml配置：

```xml
<server>
    <port>8081</port>
</server>
```

##### yaml配置：

```yaml
server：  
	prot: 8080
```

## 3、yaml语法

### （1）说明：语法要求严格！类似Python

1、==空格不能省略==

2、==以缩进来控制层级关系==，只要是左边对齐的一列数据都是同一个层级的。

3、属性和值的大小写都是十分敏感的。



**字面量：普通的值  [ 数字，布尔值，字符串  ]**

字面量直接写在后面就可以 ， ==**字符串不用加上单双引号**=== 

```yaml
k: v
```

### （2）注意：

- **" " 双引号，不会转义特殊字符** ， 特殊字符还是原字符；

  比如 ：name: "kuang \n shen"  

  输出 ：kuang  \n  shen

- **' ' 单引号，会转义特殊字符**

  比如 ：name: ‘kuang \n shen’  

  输出 ：kuang

  ​			 shen

### （3）**对象（键值对）**

#### map写法

```yaml
#对象、Map格式
student:
    name: qinjiang
    age: 3
```

#### 行内写法

 ```yaml
 student: {name: qinjiang,age: 3}
 ```



### （4）**数组（ List、set ）**

用==-==值表示数组中的一个元素,比如：

```yaml
pets:
 - cat
 - dog
 - pig
```

#### 行内写法

####  

```yaml
pets: [cat,dog,pig]
```

**修改SpringBoot的默认端口号**

配置文件中添加，端口号的参数，就可以切换端口；

```yaml
server:  
	port: 8082
```



# 四、注入配置文件

==yaml可以给实体类直接注入匹配值！==

## 1、Value注入配置文件

1、在springboot项目中的resources目录下新建一个文件 application.yml

2、编写一个实体类 Dog；

 ![image-20210723094435027](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723094435.png)

![image-20210723094448828](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723094448.png)![image-20210723094510661](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723094510.png)



## 2、yml注入配置文件

resources目录新建一个`application.yml`  **（不能改名字）**

```yml
person:
  name: qinjiang
  age: 3
  happy: false
  birth: 2000/01/01
  maps: {k1: v1,k2: v2}
  lists:
    - code
    - girl
    - music
  dog:
    name: 旺财
    age: 1
```

新建实体类

==**@ConfigurationProperties(prefix = "person")**==表示此类指向了yml文件的**person**键

```java
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
}
```

测试

```java
@Autowired
Person person;
@Test
public void contextLoads() {
    System.out.println(person);
}
```

![image-20210723143238105](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723143238.png)

## 2、加载指定配置文件

`@PropertySource` ：加载指定的配置文件；

1、在resources目录下新建**person.properties**文件

==这里面不能放中文，否则乱码==

```properties
good = mei you luan ma
```

2、然后在代码中加载他

`@PropertySource(value = "classpath:person.properties")`

```java
@Component
@PropertySource(value = "classpath:bean.properties")
public class User {
    //直接使用@value
    @Value("${user.name}") //从配置文件中取值
    private String name;
    @Value("#{9*2}")  // #{SPEL} Spring表达式
    private int age;
    @Value("男")  // 字面量
    private String sex;
}
```



`@configurationProperties`：默认从全局配置文件()中获取值；

![image-20210723144739133](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723144739.png)

## 3、配置文件占位符

```yml
person:
  name: 何昱飞${random.uuid} # 随机uuid
  age: ${random.int}  # 随机int
  happy: false
  birth: 2000/01/01
  maps: {k1: 第一,k2: 第二}
  lists:
    - code
    - girl
    - music
  dog:
    name: ${person.hello:other}_旺财
    age: 1
```

# 五、JSR303数据校验

## 1、使用方法

```xml
<dependency>
    <!-- 数据校验(比如email) -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Springboot中可以用@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。我们这里来写个注解让我们的name只能支持Email格式；

```java

@Component //注册bean
@ConfigurationProperties(prefix = "person")
@Validated  //数据校验
public class Person {

    @NotNull(message="名字不能为空")
    private String userName;

    @Max(value=120,message="年龄最大不能查过120")
    private int age;

    @Email(message="邮箱格式错误")
    private String email;
}
```



运行结果 ：default message [不是一个合法的电子邮件地址];



## 2、常见参数

### 空检查

`@Null`       验证对象是否为null
`@NotNull`    **不能为null**，但可以为empty("","")，而且被其标注的字段可以使用 @size/@Max/@Min对字段数值进行大小的控制
`@NotBlank`   **不能为null**，长度必须大于0("","")，==只用于String==，且会去掉前后空格.
`@NotEmpty`   **不能为null**，而且长度必须大于0("","")，一般用在集合类上面

### Booelan检查

`@AssertTrue`     验证 Boolean 对象是否为 true  
`@AssertFalse`    验证 Boolean 对象是否为 false  

### 长度检查

`@Size(min=, max=)` 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  
`@Length(min=, max=)` string is between min and max included.

### 日期检查

`@Past`       验证 Date 和 Calendar 对象是否在当前时间之前  
`@Future`     验证 Date 和 Calendar 对象是否在当前时间之后  
`@Pattern`    验证 String 对象是否符合正则表达式的规则



# 六、多环境切换

## 1、多配置文件

`application-test.properties` 代表**测试**环境配置

`application-dev.properties` 代表**开发**环境配置

但是Springboot并不会直接启动这些配置文件，它**默认使用==application==.properties主配置文件**；

我们需要通过一个配置来选择需要激活的环境：

```properties
# 比如在配置文件中指定使用dev环境，我们可以通过设置不同的端口号进行测试；
# 我们启动SpringBoot，就可以看到已经切换到dev下的配置了；
spring.profiles.active=dev
```

## 2、yaml的多文档块

使用yml去实现不需要创建多个配置文件，更加方便了 

```yml
server:
  port: 8081
#选择要激活那个环境块
spring:
  profiles:
    active: prod

---
server:
  port: 8083
spring:
  profiles: dev #配置环境的名称


---

server:
  port: 8084
spring:
  profiles: prod  #配置环境的名称
```

==**注意：如果yml和properties同时都配置了端口，并且没有激活其他环境 ， 默认会使用properties配置文件的！**==

## 3、配置文件加载位置

**外部加载配置文件的方式十分多，我们选择最常用的即可，在开发的资源文件中进行配置！**

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件：

官方外部配置文件说明参考文档:

1. `file:./config/` 优先级1：项目路径下的config文件夹配置文件
2. `file:./` 优先级2：项目路径下配置文件
3. `classpath:/config/` 优先级3：资源路径下的config文件夹配置文件
4. `classpath:/` 优先级4：资源路径下配置文件



# 七、自动配置原理

## 1、分析自动配置原理

   我们以**HttpEncodingAutoConfiguration（Http编码自动配置）**为例  解释自动配置原理；

```java
//表示这是一个配置类，和以前编写的配置文件一样，也可以给容器中添加组件；
@Configuration 

//启动指定类的ConfigurationProperties功能；
  //进入这个HttpProperties查看，将配置文件中对应的值和HttpProperties绑定起来；
  //并把HttpProperties加入到ioc容器中
@EnableConfigurationProperties({HttpProperties.class}) 

//Spring底层@Conditional注解
  //根据不同的条件判断，如果满足指定的条件，整个配置类里面的配置就会生效；
  //这里的意思就是判断当前应用是否是web应用，如果是，当前配置类生效
@ConditionalOnWebApplication(
    type = Type.SERVLET
)

//判断当前项目有没有这个类CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；
@ConditionalOnClass({CharacterEncodingFilter.class})

//判断配置文件中是否存在某个配置：spring.http.encoding.enabled；
  //如果不存在，判断也是成立的
  //即使我们配置文件中不配置pring.http.encoding.enabled=true，也是默认生效的；
@ConditionalOnProperty(
    prefix = "spring.http.encoding",
    value = {"enabled"},
    matchIfMissing = true
)

public class HttpEncodingAutoConfiguration {
    //他已经和SpringBoot的配置文件映射了
    private final Encoding properties;
    //只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(HttpProperties properties) {
        this.properties = properties.getEncoding();
    }
    
    //给容器中添加一个组件，这个组件的某些值需要从properties中获取
    @Bean
    @ConditionalOnMissingBean //判断容器没有这个组件？
    public CharacterEncodingFilter characterEncodingFilter() {
	
        //。。。。。。。
    }
    //。。。。。。。
}
```

**一句话总结 ：根据当前不同的条件判断，决定这个配置类是否生效！**

- 一但这个配置类生效；这个配置类就会给容器中添加各种组件；
- 这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；
- 所有在配置文件中能配置的属性都是在xxxxProperties类中封装着；
- 配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
//从配置文件中获取指定的值和bean的属性进行绑定
@ConfigurationProperties(prefix = "spring.http") 
public class HttpProperties {
    // .....
}
```

我们去配置文件里面试试前缀，看提示！

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723194129.jpeg)

## 2、精髓

1、SpringBoot启动会加载大量的自动配置类

2、我们看我们需要的功能有没有在SpringBoot默认写好的自动配置类当中；

3、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件存在在其中，我们就不需要再手动配置了）

4、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们只需要在配置文件中指定这些属性的值即可；

**xxxxAutoConfigurartion：自动配置类；**给容器中添加组件

**xxxxProperties:封装配置文件中相关属性；**

## 3、了解：@Conditional

了解完自动装配的原理后，我们来关注一个细节问题，**自动配置类必须在一定的条件下才能生效；**

**@Conditional派生注解（Spring注解版原生的@Conditional作用）**

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置配里面的所有内容才生效；

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210723195456.jpeg)

**那么多的自动配置类，必须在一定的条件下才能生效；也就是说，我们加载了这么多的配置类，但不是所有的都生效了。**

我们怎么知道哪些自动配置类生效？

**我们可以通过启用 debug=true属性；来让控制台打印自动配置报告，这样我们就可以很方便的知道哪些自动配置类生效；**

```yml
#开启springboot的调试类
debug = true
```

**Positive matches:（自动配置类启用的：正匹配）**

**Negative matches:（没有启动，没有匹配成功的自动配置类：负匹配）**

**Unconditional classes: （没有条件的类）**





# 八、自定义Starter

我们分析完毕了源码以及自动装配的过程，我们可以尝试自定义一个启动器来玩玩！

## 1、说明

启动器模块是一个空 jar 文件，仅提供辅助性依赖管理，这些依赖可能用于自动装配或者其他类库；

### **命名归约：**

#### 官方命名：

- 前缀：`spring-boot-starter-xxx`
- 比如：`spring-boot-starter-web....`

#### 自定义命名：

- `xxx-spring-boot-starter`
- 比如：`mybatis-spring-boot-starter`

## 2、编写启动器

1、在IDEA中新建一个空项目 `Spring-Boot-Study`

2、新建一个普通Maven模块：`SB-01-Starter-boot`（版本一定要相同![image-20210726111219030](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726111219.png))

```xml
<groupId>com.start</groupId>
<artifactId>SB-01-Starter-boot</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

3、新建一个Springboot模块：`SB-01-Starter-AutoConfigure`

```xml
<groupId>com.AutoConfigure</groupId>
<artifactId>SB-01-Starter-AutoConfigure</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

![](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726110853.png)

4、在`SB-01-Starter-AutoConfigure`模块的pom里面写好所有依赖‘

![image-20210726111150208](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726111150.png)

5、在`SB-01-Starter-boot`的pom中导入`SB-01-Starter-AutoConfigure`的依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.AutoConfigure</groupId>
        <artifactId>SB-01-Starter-AutoConfigure</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

6、新建一个新SpringBoot项目，里面导入`SB-01-Starter-boot`的依赖，以后任何项目都可以这么使用，替换为下面的话即可，==记得把test文件删掉==

```xml
<dependencies>
    <dependency>
        <groupId>com.start</groupId>
        <artifactId>SB-01-Starter-boot</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

# 九、整合JDBC

## 1、SpringData简介

对于数据访问层，==SQL(关系型数据库)== 和 ==NOSQL(非关系型数据库)==，Spring Boot 底层都是采用 ==**Spring Data**== 的方式进行统一处理。

Spring Boot 底层都是采用 Spring Data 的方式进行统一处理各种数据库，Spring Data 也是 Spring 中与 Spring Boot、Spring Cloud 等齐名的知名项目。

Sping Data 官网：https://spring.io/projects/spring-data

## 2、整合JDBC

### （1）创建测试项目测试数据源

#### ①依赖

放在`SB-01-Starter-AutoConfigure`中

```xml
<dependency>
    <!--JDBC-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <!--JDBC-->
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### ②配置文件application.yml

放在`SB-01-Starter-AutoConfigure`中

```yml
spring:
  datasource:
    username: root
    password: 123456
    #?serverTimezone=UTC解决时区的报错
    url: jdbc:mysql://localhost:3306/ssm-01-login?useSSl=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true
    driver-class-name: com.mysql.cj.jdbc.Driver
```

#### ③测试一下

```java
@SpringBootTest
class demo {

    //DI注入数据源
    @Autowired
    DataSource dataSource;
	//这是作用boot的：import org.junit.jupiter.api.Test;
    @Test
    public void contextLoads() throws SQLException {
        //看一下默认数据源
        System.out.println("默认数据源  "+dataSource.getClass());
        //获得连接
        Connection connection =   dataSource.getConnection();
        System.out.println("获得连接: "+connection);
        //关闭连接
        connection.close();
    }
}
```

==**HikariDataSource 号称 Java WEB 当前速度最快的数据源，相比于传统的 C3P0 、DBCP、Tomcat jdbc 等连接池更加优秀；**==

**可以使用 spring.datasource.type 指定自定义的数据源类型，值为 要使用的连接池实现的完全限定名。**



## 3、JDBCTemplate(原生)

### （1）概念

1. 有了==数据源(com.zaxxer.hikari.HikariDataSource)==，然后可以拿到==数据库连接(java.sql.Connection)==，有了连接，就可以使用原生的 JDBC 语句来操作数据库；
2. 即使不使用MyBatis等，Spring 本身也对原生的JDBC 做了轻量级的封装，即JdbcTemplate。
3. 所有 CRUD 方法都在 JdbcTemplate 中。
4. Spring Boot 默认已配置好 JdbcTemplate 放在了容器中，程序员只需自己注入即可使用
5. JdbcTemplate 的自动配置是依赖 org.springframework.boot.autoconfigure.jdbc 包下的 JdbcTemplateConfiguration 类

### （2）**JdbcTemplate主要提供以下几类方法：**

- execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
- update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
- query方法及queryForXXX方法：用于执行查询相关语句；
- call方法：用于执行存储过程、函数相关语句。

## 测试

编写一个Controller，注入 jdbcTemplate，编写测试方法进行访问测试；

```java
@RestController
public class myController {
    /**
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，用于简化 JDBC操作
     * 还能避免一些常见的错误,使用起来也不用再自己来关闭数据库连接
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    //查询employee表中所有数据
    //List 中的1个 Map 对应数据库的 1行数据
    //Map 中的 key 对应数据库的字段名，value 对应数据库的字段值
    @RequestMapping("/list")
    public List<Map<String, Object>> userList(){
        String sql = "select * from `ssm-01-login`.danzi";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
    //新增一个用户
    @GetMapping("/add")
    public String addUser(){
        //插入语句，注意时间问题
        String sql = "insert into employee(last_name, email,gender,department,birth)" +
                " values ('狂神说','24736743@qq.com',1,101,'"+ new Date().toLocaleString() +"')";
        jdbcTemplate.update(sql);
        //查询
        return "addOk";
    }

    //修改用户信息
    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id){
        //插入语句
        String sql = "update employee set last_name=?,email=? where id="+id;
        //数据
        Object[] objects = new Object[2];
        objects[0] = "秦疆";
        objects[1] = "24736743@sina.com";
        jdbcTemplate.update(sql,objects);
        //查询
        return "updateOk";
    }

    //删除用户
    @GetMapping("/delete/{id}")
    public String delUser(@PathVariable("id") int id){
        //插入语句
        String sql = "delete from employee where id=?";
        jdbcTemplate.update(sql,id);
        //查询
        return "deleteOk";
    }
    
}
```

# 十、集成Druid

## 1、Druid简介

**为了提高操作数据库性能**，必须使用==**数据库连接池**==。

Druid 是阿里巴巴开源平台上一个数据库连接池实现，结合了 C3P0、DBCP 等 DB 池的优点，同时加入了日志监控。

Druid 可以很好的监控 DB 池连接和 SQL 的执行情况，天生就是针对监控而生的 DB 连接池。

Druid已经在阿里巴巴部署了超过600个应用，经过一年多生产环境大规模部署的严苛考验。

Spring Boot 2.0 以上默认使用 Hikari 数据源，可以说 **Hikari 与 Driud 都是当前最优秀的数据源**，

## 2、配置参数

**com.alibaba.druid.pool.DruidDataSource 基本配置参数如下：**<img src="https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7LYLicOHVGnwu7ibGvbwXibYeubiciawTdz0tg1EKDjZ1xaIgjRW9CZ4Apr4hvNz3iaQVQIKS3sXy629Lgg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1" alt="图片" style="zoom: 50%;" /><img src="https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7LYLicOHVGnwu7ibGvbwXibYeubiciawTdz0tg1EKDjZ1xaIgjRW9CZ4Apr4hvNz3iaQVQIKS3sXy629Lgg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1" alt="图片" style="zoom: 50%;" /><img src="https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7LYLicOHVGnwu7ibGvbwXibYeuaVD6mK3LJrtZ4B6fRKCLDgYicAVGzTUTzWdCNB5lF4tLpbcCT0uq1EA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1" alt="图片" style="zoom: 50%;" />

## 3、配置数据源

### （1）依赖

```xml
<dependency>
    <!--Druid连接池-->
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
```

### （2）切换数据源

Spring Boot 2.0 以上默认使用 com.zaxxer.hikari.HikariDataSource 数据源，

但可以 通过 spring.datasource.type 指定数据源。

​    `type: com.alibaba.druid.pool.DruidDataSource `# 自定义数据源

```yml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource # 自定义数据源
```

可加选项

```yml
#Spring Boot 默认是不注入这些属性值的，需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
    #则导入 log4j 依赖即可，Maven 地址：https://mvnrepository.com/artifact/log4j/log4j
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

### （3）导入log4j

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>2.14.1</version>
</dependency>
```



# 十一、整合MyBatis

## 1、整合MyBatis

### （1）整合测试

#### ①依赖

```xml
<dependency>
    <!--mybatis-->
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>RELEASE</version>
</dependency>
```

#### ②配置数据库连接

```yml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource # 自定义数据源
```

#### ③写好mapper.class和mapper.xml

`@Mapper` 表示本类是一个 MyBatis 的 Mapper
`@Repository`  `@repository`跟`@Service`,`@Compent`,`@Controller`这4种注解是没什么本质区别,都是声明作用,取不同的名字只是为了更好区分各自的功能

![image-20210726181743354](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726181743.png)

#### ④**maven配置资源过滤问题**

 ==放在当前项目==的pom 的 <build>标签中

```xml
<resources>
    <resource>
        <directory>src/main/java</directory>
        <includes>
            <include>**/*.xml</include>
        </includes>
        <filtering>true</filtering>
    </resource>
</resources>
```

#### ⑤在Controller测试

![image-20210726193300074](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726193300.png)





# 十二、Web开发探究

![图片](https://mmbiz.qpic.cn/mmbiz_gif/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicAHqMeOptckiacohSnX6DTIYSic2Uic7GLWuezVDk3bYqJa4vQwPwrLJXQ/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1)

## 1、Web开发探究

### （1）简介

SpringBoot的东西用起来非常简单，因为SpringBoot最大的特点就是自动装配。

**使用SpringBoot的步骤：**

1. 创建一个SpringBoot模块，SpringBoot就会默认将我们的需要的模块自动配置好
2. 手动在配置文件中配置部分配置项目就可以运行起来了
3. 专注编写业务代码，不需要考虑以前那样一大堆的配置了。

**要熟悉掌握开发，自动配置的原理就一定要搞明白！**

比如SpringBoot到底帮我们配置了什么？我们能不能修改？我们能修改哪些配置？我们能不能扩展？

- 向容器中自动配置组件 ：*** Autoconfiguration
- 自动配置类，封装配置文件的内容：***Properties

没事就找找类，看看自动装配原理！



## 2、静态资源处理

### （1）静态资源映射规则1

项目中有许多的静态资源，比如css，js等文件，这个SpringBoot怎么处理呢？

如果我们是一个web应用，我们的main下会有一个webapp，我们以前都是将所有的页面导在这里面的，对吧！但是我们现在的pom呢，打包方式是为jar的方式，那么这种方式SpringBoot能不能来给我们写页面呢？当然是可以的，但是SpringBoot对于静态资源放置的位置，是有规定的！

**我们先来聊聊这个静态资源映射规则：**

SpringBoot中，SpringMVC的web配置都在 WebMvcAutoConfiguration 这个配置类里面；

我们可以去看看 WebMvcAutoConfigurationAdapter 中有很多配置方法；

有一个方法：addResourceHandlers 添加资源处理

```java

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        // 已禁用默认资源处理
        logger.debug("Default resource handling disabled");
        return;
    }
    // 缓存控制
    Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
    CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
    // webjars 配置
    if (!registry.hasMappingForPattern("/webjars/**")) {
        customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                                             .addResourceLocations("classpath:/META-INF/resources/webjars/")
                                             .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
    // 静态资源配置
    String staticPathPattern = this.mvcProperties.getStaticPathPattern();
    if (!registry.hasMappingForPattern(staticPathPattern)) {
        customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                                             .addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
                                             .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
    }
}
```

#### 什么是webjars 呢？

以jar包的方式引入我们的静态资源 ， 我们以前要导入一个静态资源文件，直接导入即可。

**使用SpringBoot需要使用Webjars**

要使用jQuery，只要要引入jQuery依赖即可！

```xml
<dependency>
	<!--JQuary-->
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.6.0</version>
</dependency>
```

导入完毕，查看webjars目录结构，并访问Jquery.js文件！

![image-20210726200956010](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726200956.png)

![image-20210726201020294](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210726201020.png)

### （2）静态资源映射规则2

使用自己的静态资源导入方法：

我们去找staticPathPattern发现第二种映射规则 ：/** , 访问当前的项目任意资源，它会去找 resourceProperties 这个类，我们可以点进去看一下分析：

```java
// 进入方法
public String[] getStaticLocations() {
    return this.staticLocations;
}
// 找到对应的值
private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
// 找到路径
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { 
    "classpath:/META-INF/resources/",
  "classpath:/resources/", 
    "classpath:/static/", 
    "classpath:/public/" 
};
```

ResourceProperties 可以设置和我们静态资源有关的参数；这里面指向了它会去寻找资源的文件夹，即上面数组的内容。

所以得出结论，**以下四个目录存放的静态资源可以被我们识别**：

```java
"classpath:/META-INF/resources/"
"classpath:/resources/"
"classpath:/static/"
"classpath:/public/"
```

==**可以在resources根目录下新建对应的文件夹，都可以存放静态文件**==

比如我们访问 http://localhost:8080/1.js , 他就会去这些文件夹中寻找对应的静态资源文件；

### （3）自定义静态资源路径

可以自己通过配置文件来指定一下，哪些文件夹是需要我们放静态资源文件的，==在application.properties中配置==；

```properties
spring.resources.static-locations=classpath:/coding/,classpath:/web/
```

![image-20210727122159470](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727122159.png)

![image-20210727122335518](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727122335.png)

![image-20210727122356385](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727122356.png)

## 3、首页处理

全局搜索`welcomePageHandlerMapping`![image-20210727133958747](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727133959.png)



访问  http://localhost:8080/ ，就会找**所有静态资源文件夹**下的 index.html

## 4、**关于网站图标说明**：

Spring Boot在配置的静态内容位置中查找 favicon.ico。如果存在这样的文件，它将自动用作应用程序的favicon。

1. 关闭SpringBoot默认图标

   ```properties
   #关闭默认图标
   spring.mvc.favicon.enabled=false
   ```

   

2. 自己放一个图标在静态资源目录下，我放在 public 目录下

3. 清除浏览器缓存！刷新网页，发现图标已经变成自己的了！



# 十三、Thymeleaf

## 1、模板引擎

jsp支持非常强大的功能，包括能写Java代码，但是SpringBoot这个项目首先是以jar的方式，不是==war(嵌入式的Tomcat)==，==**所以他现在默认是不支持jsp**==。



**SpringBoot推荐你可以来使用模板引擎：**

模板引擎，我们其实大家听到很多，其实**jsp就是一个模板引擎**，还有用的比较多的freemarker，包括**SpringBoot推荐Thymeleaf**，模板引擎有非常多，但再多的模板引擎，他们的思想都是一样的，什么样一个思想呢我们来看一下这张图：

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727205638.png)





Thymeleaf模板引擎 是一个高级语言的模板引擎，他的语法更简单。功能更强大。



### （1）引入依赖

```xml
<!--thymeleaf-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### （2）使用

#### 找到自动配置类：ThymeleafProperties

![image-20210727205628493](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727205628.png)

只需要把html页面放在`templates`下，thymeleaf就可以帮我们自动渲染了。

==使用thymeleaf什么都不需要配置，只需要将他放在指定的文件夹下即可！==

### （3）测试

#### ①编写一个TestController

```java
@RequestMapping("/t1")
public String test1(){
    //classpath:/templates/test.html
    return "test";
}
```

#### ②编写一个页面test.html 放在 templates 目录下

 ![image-20210727214117232](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727214117.png)

## 2、如何传数据

#### ① 请求中加入数据

```java
@RequestMapping("/t1")
public String test1(Model model){
    //存入数据
    model.addAttribute("msg","Hello,Thymeleaf");
    //classpath:/templates/test.html
    return "test";
}
```

#### ② html导入依赖

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

![image-20210727215631092](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727215631.png)

#### ③ 写前端页面

```html
<div th:text="${msg}">111</div>  <!--原有的内容111直接消失-->
```

## 3、语法

### （1）常用th属性

-   `th:text：`文本替换；

  ```html
  <p th:text="${thText}"></p>
  <div th:text="${session.ww}"></div>  <!--session-->
  ```

    

- `th:utext：`支持html的文本替换。

  ```html
  <p th:utext="${thUText}"></p>
  ```

-   `th:value：`属性==**赋值**==

  ```html
  <input type="text" th:value="${thValue}" />
  ```

-   `th:each：`==**遍历循环**==元素

  ①遍历整个div和p，不推荐

- ```html
  <div th:each="message : ${thEach}"> <!-- 遍历整个div-p，不推荐-->
    <p th:text="${message}"></p>
  </div>
  ```

  ![image-20210727223446550](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727223446.png)

  ```html
  <div> <!--只遍历p，推荐使用-->
    <p th:each="i : ${thEach}" th:text="${i}"></p>
  </div>
  ```

  **②只遍历p，推荐使用**![image-20210727223529690](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210727223529.png)

  **③只遍历p，行内写法**

  ```html
  <p th:each="i: ${thEach}">[[${i}]]</p>
  ```

  

-  `th:if：`==**判断条件**==，类似的还有th:unless，th:switch，th:case

  ```html
  <p th:text="${thIf}" th:if="${not #strings.isEmpty(thIf)}"></p>
  ```

-   `th:insert：`代码块引入，类似的还有th:replace，th:include，常用于公共代码块提取的场景

-   `th:fragment：`定义代码块，方便被th:insert引用

-   `th:object：`声明变量，一般和*{}一起配合使用，达到偷懒的效果。

-  ` th:attr：`设置==**标签属性**==，多个属性可以用逗号分隔

### （2）标准表达式语法

- `${...}` 变量表达式，Variable Expressions
- `@{...}` 链接表达式，Link URL Expressions
- `#{...}` 消息表达式，Message Expressions
- `~{...}` 代码块表达式，Fragment Expressions
- `*{...}` 选择变量表达式，Selection Variable Expressions



# 十四、MVC自动配置原理

## 1、官网阅读

### **ContentNegotiatingViewResolver 内容协商视图解析器** 

自动配置了ViewResolver，就是我们之前学习的SpringMVC的视图解析器；

即**根据方法的返回值取得视图对象**（View），然后由视图对象决定如何渲染（转发，重定向）。

找到 `WebMvcAutoConfiguration` , 然后搜索`ContentNegotiatingViewResolver`方法





# 十五、页面国际化

## 1、准备工作

先在IDEA中统一设置properties的编码问题！

![image-20210728093557722](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728093557.png)

### （1）配置文件编写

1、我们在resources资源文件下新建一个i18n目录，存放国际化配置文件

2、建立一个login.properties文件，还有一个login_zh_CN.properties；发现IDEA自动识别了我们要做国际化操作；文件夹变了！

![image-20210728103655905](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728103656.png)

3、我们可以在这上面去新建一个文件；

![image-20210728103740921](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728103741.png)

弹出如下页面：我们再添加一个英文的；

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728103821.png" alt="image-20210728103821731" style="zoom:80%;" />

**4、接下来，我们就来编写配置，我们可以看到idea下面有另外一个视图；**

这个视图我们点击 + 号就可以直接添加属性了；我们新建一个login.tip，可以看到边上有三个文件框可以输入，添加一下首页的内容！

![image-20210728103919786](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728103920.png)

![image-20210728104149644](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728104149.png)

默认login![image-20210728104029683](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728104029.png)中文![image-20210728104029683](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728104029.png)英文![image-20210728104054741](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728104054.png)



==我们真实 的情况是放在了i18n目录下，所以我们要去配置这个messages的路径；==

```properties
spring.messages.basename=i18n.login
```





# 十六、Swagger

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728104317.gif)

## 1、Swagger简介

**前后端分离**

- 前端 -> 前端控制层、视图层
- 后端 -> 后端控制层、服务层、数据访问层
- 前后端通过API进行交互
- 前后端相对独立且松耦合

**产生的问题**

- 前后端集成，前端或者后端无法做到“及时协商，尽早解决”，最终导致问题集中爆发

**解决方案**

- 首先定义schema [ 计划的提纲 ]，并实时跟踪最新的API，降低集成风险

**Swagger**

- 号称世界上最流行的API框架
- Restful Api 文档在线自动生成器 => **API 文档 与API 定义同步更新**
- 直接运行，在线测试API
- 支持多种语言 （如：Java，PHP等）
- 官网：https://swagger.io/



## 2、SpringBoot集成Swagger

**SpringBoot集成Swagger** => **springfox**，两个jar包

- **Springfox-swagger2**
- swagger-springmvc

### （1）**使用Swagger**

要求：jdk 1.8 + 否则swagger2无法运行

步骤：

1、新建一个SpringBoot-web项目

2、添加Maven依赖

```xml
<dependency>
    <!--swagger-->
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <!--swagger-->
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

==别用3.0.0，页面不显示，可能是不兼容1.8==

3、编写HelloController，测试确保运行成功！

4、要使用Swagger，我们需要编写一个配置类-SwaggerConfig来配置 Swagger

```java
@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig { 
}
```

5、访问测试 ：http://localhost:8080/swagger-ui.html ，可以看到swagger的界面；

### 5、访问测试 ：http://localhost:8080/swagger-ui.html ，可以看到swagger的界面；

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728134724.png)

## 3、配置Swagger

### （1）.apiInfo()配置说明信息

```java
@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig {
    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
        //return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo());
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("何昱飞", "https://www.baidu.com", "2435823336@qq.com");
        return new ApiInfo(
                "何昱飞的Swagger", // 标题
                "干他娘的", // 描述
                "v1.0", // 版本
                "组织链接", // 组织链接
                contact, // 联系人信息
                "Apache 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
```
![image-20210728154804506](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728154804.png)

### （2）RequestHandlerSelectors的方法（接口扫描过滤）

`any()` // 扫描所有包，项目中的所有interface和pojo都会被扫描到	<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728151704.png" alt="image-20210728151704035" style="zoom: 33%;" />

`none()` // 不扫描接口<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728151440.png" alt="image-20210728151440190" style="zoom: 50%;" />

// 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)
`withMethodAnnotation(GetMapping.class)`只扫描指定请求

​		==如果你是RequestMapping，是扫描不到的==

`withClassAnnotation(Controller.class)`只扫描有指定注解的类中的接口![image-20210728152112421](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728152112.png)

`basePackage("包路径")` // 根据包路径扫描接口![image-20210728154201645](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728154201.png)



##### **只显示指定请求下的子请求**：.paths(PathSelectors.ant(/xx/**))

![image-20210728170608670](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728170608.png)

![image-20210728170838717](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728170838.png)



```java
@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig {

    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
		//return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()// 通过.select()方法配置扫描接口
            	//RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.jdbc.sb03jdbc.controller"))
                .build();
    }
    //配置文档信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("何昱飞", "https://www.baidu.com", "2435823336@qq.com");
        return new ApiInfo(
                "何昱飞的Swagger", // 标题
                "干他娘的", // 描述
                "v1.0", // 版本
                "组织链接", // 组织链接
                contact, // 联系人信息
                "Apache 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
```

#### 2、重启项目，访问测试 http://localhost:8080/swagger-ui.html  看下效果；

![image-20210728150958051](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728151004.png)

## 4、配置Swagger开关

### （1）enable()方法配置是否启用swagger

```java
.enable(true) //配置是否启用Swagger，如果是false，在浏览器将无法访问
```

### （2）动态配置是否启用swagger

**在在方法中再加上下面这两句话，还有形参，然后把.enable(true)，改成.enable(b)**

```java
@Bean
public Docket docket(Environment environment) {
   // 设置要显示swagger的环境
   Profiles of = Profiles.of("dev", "test");
   // 判断当前是否处于该环境
   // 通过 enable() 接收此参数判断是否要显示
   boolean b = environment.acceptsProfiles(of);
   
   。。。。。
}
```

然后在properties文件写入

```properties
spring.profiles.active= dev
```



### 5、配置API分组.groupName("hello")

1、如果没有配置分组，默认是default。通过groupName()方法即可配置分组：

默认![image-20210728172622011](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728172622.png)

写上如下方法：![image-20210728173111547](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728173111.png)



效果：![image-20210728173031404](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728173031.png)



3、如何配置多个分组？配置多个分组只需要配置多个docket即可：

```java
@Bean
public Docket docket1(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
}
@Bean
public Docket docket2(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
}
@Bean
public Docket docket3(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group3");
}
```

## 6、实体配置

1、新建一个实体类

Swagger的所有注解定义在`io.swagger.annotations`包下

`@ApiModel`为**类**添加注释

`@ApiModelProperty`为**类属性**添加注释

```java
@ApiModel("用户实体")
public class UserS {
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;
}
```

2、只要这个实体在**请求接口**的返回值上（即使是泛型），都能映射到实体项中：

```java
@GetMapping("/getUser")
public UserS getUser(){
    return new UserS();
}
```

3、swagger显示<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728191503.png" alt="image-20210728191503574" style="zoom: 80%;" />

## 7、常用注解

Swagger的所有**注解**在`io.swagger.annotations`包下

| Swagger注解                                                  | 简单说明                                             |
| ------------------------------------------------------------ | ---------------------------------------------------- |
| @Api(tags = "我是模块类")                                    | **模块类**                                           |
| @ApiOperation("我是接口方法")                                | **接口方法**                                         |
| @ApiModel("我是pojo类")                                      | **pojo类**                                           |
| @ApiModelProperty(value = "我是属性类方法或属性" ,hidden = true) | **类方法**或**属性**，hidden设置为true可以隐藏该属性 |
| @ApiParam("我是参数、方法和字段")                            | **参数、方法和字段**                                 |

给请求的接口配置一些注释

```java
@ApiOperation("何昱飞的接口")
@RestController
@RequestMapping("/hyf")
public class SwaggerController {
    @GetMapping(value = "/hello")
    public String hello(@ApiParam("这是username") String username,@ApiParam("这是password") String password){
        return username;
    }
}
```

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728200831.png" alt="image-20210728200830837" style="zoom: 67%;" />



==**正式环境要记得关闭Swagger，一来安全二来节省运行内存**==



## 8、swagger皮肤

### （1）使用bootstrap的皮肤

#### ①依赖

```xml
<dependency>
   <!-- 引入swagger皮肤包 /doc.html-->
   <groupId>com.github.xiaoymin</groupId>
   <artifactId>swagger-bootstrap-ui</artifactId>
   <version>1.9.1</version>
</dependency>
```

#### ② **访问**

**http://localhost:8080/doc.html**

### （2）使用Layui的皮肤（英语）

#### ①依赖

```xml
<!-- 引入swagger皮肤包 /docs.html-->
<dependency>
   <groupId>com.github.caspar-chen</groupId>
   <artifactId>swagger-ui-layer</artifactId>
   <version>1.1.3</version>
</dependency>
```

#### ② **访问**

**http://localhost:8080/docs.html**



### （3）使用mg-ui的皮肤

#### ①依赖

```xml
<dependency>
    <!-- 引入swagger皮肤包 /document.html-->
   <groupId>com.zyplayer</groupId>
   <artifactId>swagger-mg-ui</artifactId>
   <version>1.0.6</version>
</dependency>
```

#### ② **访问**

 **http://localhost:8080/document.html**



# 十七、异步 定时 邮件任务

## 1、异步任务

### （1）写一个service类

```java
@Service
public class AsyncService {

   public void hello(){
       try {
           Thread.sleep(3000);
      } catch (InterruptedException e) {
           e.printStackTrace();
      }
       System.out.println("业务进行中....");
  }
}
```

### （2）写一个Controller类

```java
@Controller
public class AsyncController {
    @Autowired
    AsyncService asyncService;
    
    @GetMapping("/async")
    public String hello(){
        asyncService.hello();
        return "test";
    }
}
```

### （3）测试

访问http://localhost:8080/async进行测试，3秒后切换页面，这是同步等待的情况。



### （4）给service中的某方法加@Async注解；

SpringBoot就会自己开一个线程池，进行调用！![image-20210728203206464](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108191353029.png)



但是要让这个注解生效，我们还需要在**主程序上添加一个注解`@EnableAsync`** ，==开启异步注解功能==；![image-20210728203328439](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728203328.png)



## 2、定时任务

项目开发中经常需要执行一些定时任务，比如需要在每天凌晨的时候，分析一次前一天的日志信息，Spring为我们提供了==**异步执行任务调度**==的方式，提供了两个接口。

- `TaskExecutor`接口
- `TaskScheduler`接口

两个注解：

- `@EnableScheduling`
- `@Scheduled`

### （1）**cron表达式：**

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728204011.webp)

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728204013.webp)

### （2）使用

#### ①创建一个ScheduledService

```java
@Service
public class ScheduledService {
   
   //秒   分   时     日   月   周几
   //0 * * * * MON-FRI
   //注意cron表达式的用法；
   @Scheduled(cron = "0 * * * * 0-7")
   public void hello(){
       System.out.println("hello.....");
  }
}
```

2、主程序上增加@EnableScheduling 开启定时任务功能![image-20210728204824038](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210728204824.png)

3、常用的表达式

```properties
（1）0/2 * * * * ?   表示每2秒 执行任务
（1）0 0/2 * * * ?   表示每2分钟 执行任务
（1）0 0 2 1 * ?   表示在每月的1日的凌晨2点调整任务
（2）0 15 10 ? * MON-FRI   表示周一到周五每天上午10:15执行作业
（3）0 15 10 ? 6L 2002-2006   表示2002-2006年的每个月的最后一个星期五上午10:15执行作
（4）0 0 10,14,16 * * ?   每天上午10点，下午2点，4点
（5）0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
（6）0 0 12 ? * WED   表示每个星期三中午12点
（7）0 0 12 * * ?   每天中午12点触发
（8）0 15 10 ? * *   每天上午10:15触发
（9）0 15 10 * * ?     每天上午10:15触发
（10）0 15 10 * * ?   每天上午10:15触发
（11）0 15 10 * * ? 2005   2005年的每天上午10:15触发
（12）0 * 14 * * ?     在每天下午2点到下午2:59期间的每1分钟触发
（13）0 0/5 14 * * ?   在每天下午2点到下午2:55期间的每5分钟触发
（14）0 0/5 14,18 * * ?     在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
（15）0 0-5 14 * * ?   在每天下午2点到下午2:05期间的每1分钟触发
（16）0 10,44 14 ? 3 WED   每年三月的星期三的下午2:10和2:44触发
（17）0 15 10 ? * MON-FRI   周一至周五的上午10:15触发
（18）0 15 10 15 * ?   每月15日上午10:15触发
（19）0 15 10 L * ?   每月最后一日的上午10:15触发
（20）0 15 10 ? * 6L   每月的最后一个星期五上午10:15触发
（21）0 15 10 ? * 6L 2002-2005   2002年至2005年的每月的最后一个星期五上午10:15触发
（22）0 15 10 ? * 6#3   每月的第三个星期五上午10:15触发
```

## 3、邮件任务

### （1）引入pom依赖

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### （2）找实体类

在Spring.factories中找到这个类，点进去，看看它引入的哪些类是实体类，最后找到这么个类![image-20210729091245073](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729091245.png)

### （3）配置文件写入

```properties
spring.mail.username=2435823336@qq.com
spring.mail.password=thiofahhwoayeabe
spring.mail.host=smtp.qq.com
# qq需要配置ssl
spring.mail.properties.mail.smtp.ssl.enable=true
```

### （4）获取授权码

​	在QQ邮箱中的设置->账户->开启pop3和smtp服务



![image-20210729091543923](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729091544.png)

### （5）发邮件

```java
@SpringBootTest
public class DemoEmail {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        //邮件设置1：一个简单的邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("通知-明天来狂神这听课");
        message.setText("今晚7:30开会");

        message.setTo("2435823336@qq.com");
        message.setFrom("2435823336@qq.com");
        mailSender.send(message);
    }

    @Test
    public void contextLoads2() throws MessagingException {
        //邮件设置2：一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("通知-明天来狂神这听课");
        helper.setText("<b style='color:red'>今天 7:30来开会</b>",true);
        //发送附件
        helper.addAttachment("1.jpg",new File("C://Users//24358//Pictures/明日方舟.jpg"));
        helper.addAttachment("2.jpg",new File("C://Users//24358//Pictures/明日方舟.jpg"));
        helper.setTo("2435823336@qq.com");
        helper.setFrom("2435823336@qq.com");
        mailSender.send(mimeMessage);
    }
}
```

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729091455.png" alt="image-20210729091455768" style="zoom: 67%; border: 1px solid red;" /><img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729091437.png" alt="image-20210729091437755" style="zoom: 50%; border: 1px solid red;" />

# 十八、富文本编辑器

## 1、基础工程搭建

### （1）数据库设计

#### article：文章表

| 字段    |          | 备注         |
| ------- | -------- | ------------ |
| id      | int      | 文章的唯一ID |
| author  | varchar  | 作者         |
| title   | varchar  | 标题         |
| content | longtext | 文章的内容   |

建表

```sql
create table `article` (
`id` int(10) not null auto_increment comment 'int文章的唯一id',
`author` varchar(50) not null comment '作者',
`title` varchar(100) not null comment '标题',
`content` longtext not null comment '文章的内容',
primary key (`id`)
) engine=innodb default charset=utf8
```

### （2）项目搭建

#### ① 建一个Boot项目配置

```yml
spring:
  datasource:
    username: root
    password: 123456
    #?serverTimezone=UTC解决时区的报错
    url: jdbc:mysql://localhost:3306/?useSSl=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource # 自定义数据源Druid
      #Spring Boot 默认是不注入这些属性值的，需要自己绑定
      #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
```

==**pom.xml中加入这个xml过滤，不然xml文件不显示**==

```xml
<build>
    <resources>
       <resource>
           <directory>src/main/java</directory>
           <includes>
               <include>**/*.xml</include>
           </includes>
           <filtering>true</filtering>
       </resource>
    </resources>
<build>
```

#### ② 实体类

```java
//文章类
public class Article implements Serializable {
    private int id; //文章的唯一ID
    private String author; //作者名
    private String title; //标题
    private String content; //文章的内容
}
```

#### ③ Mapper接口

```java
@Mapper
@Repository
public interface ArticleMapper {
   //查询所有的文章
   List<Article> queryArticles();

   //新增一个文章
   int addArticle(Article article);

   //根据文章id查询文章
   Article getArticleById(int id);

   //根据文章id删除文章
   int deleteArticleById(int id);

}
```

#### ④ Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.kuang.mapper.ArticleMapper">

   <select id="queryArticles" resultType="Article">
      select * from article
   </select>
   
   <select id="getArticleById" resultType="Article">
      select * from article where id = #{id}
   </select>
   
   <insert id="addArticle" parameterType="Article">
      insert into article (author,title,content) values (#{author},#{title},#{content});
   </insert>
   
   <delete id="deleteArticleById" parameterType="int">
      delete from article where id = #{id}
   </delete>
   
</mapper>
```

#### ⑤ properties加入映射

```properties
mybatis.mapper-locations=classpath:com/jdbc/sb03jdbc/dao/*.xml
mybatis.type-aliases-package= com/jdbc/sb03jdbc/pojo
```

#### ⑥ controller测试 

```java
@RestController
public class ArticleController {
    @Autowired
    ArticleMapper articleMapper;

    @GetMapping("/queryArticle")
    public List<Article> hello(){
        return articleMapper.queryArticles();
    }
}
```

## 2、文章编辑整合（重点）

在官网下载它：https://pandao.github.io/editor.md/ ， 得到它的压缩包！

将整个解压的文件倒入我们的项目，将一些无用的测试和案例删掉即可！

...







# 十九、Dubbo和Zookeeper集成

## 1、分布式理论

### **什么是分布式系统？**

分布式系统是由一组通过网络进行通信、为了完成共同的任务而协调工作的计算机节点组成的系统。分布式系统的出现是为了用廉价的、普通的机器完成单个计算机无法完成的计算、存储任务。其目的是**利用更多的机器，处理更多的数据**。

==**分布式系统（distributed system）是建立在网络之上的软件系统。**==



### Dubbo文档

随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，急需**一个治理系统**确保架构有条不紊的演进。

在Dubbo的官网文档有这样一张图

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729143810.png)

### 单一应用架构

当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键。

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729143835.png)

适用于小型网站，小型管理系统，将所有功能都部署到一个功能里，简单易用。

**缺点：**

1、性能扩展比较难

2、协同开发问题

3、不利于升级维护

### 垂直应用架构

当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729145006.webp)

通过切分业务来实现各个模块独立部署，降低了维护和部署的难度，团队各司其职更易管理，性能扩展也更方便，更有针对性。

缺点：公用模块无法重复利用，开发性的浪费

### 分布式服务架构

当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的**分布式服务框架(==RPC==)**是关键。

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729145004.png)

### 流动计算架构

当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于**提高机器利用率的资源调度和治理中心**(SOA)[ Service Oriented Architecture]是关键。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7JJjARRqcZibY4ZPv60renshxoCosFhoMzIcbBzjCt6ia9Gr7atHlwNHhL0po4YhyE8WkHXnnpN8Ddg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

## 2、RPC

RPC【Remote Procedure Call】是指**远程过程调用**，是一种**进程间通信方式**，他是一种技术的思想，而不是规范。它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数

**RPC基本原理**

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729152114.png)

**步骤解析：**

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729152454.png)

RPC两个核心模块：通讯，序列化。

## 3、测试环境搭建

### Dubbo

Apache Dubbo 是一款高性能、轻量级的开源**Java RPC框架**，它提供了三大核心能力：**面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。**

dubbo官网 http://dubbo.apache.org/zh-cn/index.html

1.了解Dubbo的特性

2.查看官方文档

#### **dubbo基本概念**

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729155004.webp" alt="图片" style="zoom: 50%;" />

**服务提供者**（Provider）：暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。

**服务消费者**（Consumer）：调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。

**注册中心**（Registry）：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者

**监控中心**（Monitor）：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心

#### **调用关系说明**

1. 服务容器负责启动，加载，运行服务提供者。
2. 服务提供者在启动时，向注册中心注册自己提供的服务。
3. 服务消费者在启动时，向注册中心订阅自己所需的服务。
4. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。



### Dubbo环境搭建

点进dubbo官方文档，推荐我们使用Zookeeper 注册中心

什么是zookeeper呢？可以查看官方文档

### Window下安装zookeeper

1、下载zookeeper ：地址， 我们下载3.4.14 ， 最新版！解压zookeeper

2、运行/bin/zkServer.cmd ，初次运行会报错，没有zoo.cfg配置文件；

可能遇到问题：闪退 !

解决方案：编辑zkServer.cmd文件末尾添加pause 。这样运行出错就不会退出，会提示错误信息，方便找到原因。











































