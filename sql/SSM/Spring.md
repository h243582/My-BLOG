# 一、Spring

## **1、下载**

### 	**springframework框架参考文档  4.3.9**

> https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/spring-framework-reference/html/

### 	**Spring依赖**

```xml
<dependency>
    <!--            Spring包-->
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.3</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.3</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.2.9.RELEASE</version>
</dependency>
```

## **2、优点**

- ​		开源免费

- ​		轻量级、非入侵

- ​		控制反转（IOC）和面向切面（AOP）

  

## **3、组成**

- **开源免费**

- **轻量级、非入侵**

- **控制反转（IOC）和面向切面（AOP）**

  

  ### `BeanFacotry`和`ApplicationContext`

  `BeanFacotry`是`spring`中比较原始的`Factory`。如`XMLBeanFactory`就是一种典型的`BeanFactory`。原始的`BeanFactory`无法支持`spring`的许多插件，如AOP功能、Web应用等。 
  `ApplicationContext`接口,它由`BeanFactory`接口派生而来，`ApplicationContext`包含`BeanFactory`的所有功能，通常建议比`BeanFactory`优先

  ### `BeanFactory`和`FactoryBean`的区别

     `BeanFactory`是个Factory，也就是IOC容器或对象工厂，`FactoryBean`是个Bean。在Spring中，**所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的**。但对`FactoryBean`而言，**这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的==工厂Bean==,它的实现与设计模式中的工厂模式和修饰器模式类似** 

  

## **4、拓展**

![image-20210210140653543](https://i.loli.net/2021/03/06/R2iUxa6B3NrCLEH.png)

![image-20210210140617971](https://i.loli.net/2021/03/06/lSkPZoAiI1rQCwV.png)

![image-20210210141003347](https://i.loli.net/2021/03/06/7NZxF5Jnmgz6bGV.png)



# 二、IOC

## 1、概念

![211173830707](https://i.loli.net/2021/03/06/9QFxVfaYsKbJCRo.png)

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108181811237.png)

## 2、创建对象实现过程

### (1）写好实体类

```java
@Data
public class Hello {
    private String str;
    private String str2;
    
    public Hello(String str) {this.str = str; }
    
    public Hello(String str, String str2) {this.str = str; this.str2 = str2; }
}
```

### (2）配置xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans 
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/cache
        http://www.springframework.org/schema/cache/spring-cache.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
">


</beans>
```

### (3）实例化

```xml
<!--   使用Spring来创建对象 -->
<bean id="hello" class="com.pojo.Hello">
<!--    通过构造方法赋值-->
    <constructor-arg name="str" value="何昱飞"/>
    <constructor-arg name="str2" value="何昱飞2"/>
<!--    通过set方法给某个变量赋值-->
    <property name="str" value="Spring  property"/>
</bean>
```



​	**bean**

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108181813186.png)



 

### (4）测试类

```java
public class Demo {
    public static void main(String[] args) {
        //获取Spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("Application.xml");
        //我们的对象都是在Spring中的管理了，我们要使用，直接去里面取出来就可以了
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }
}
```

也可以这么写就不用强转了

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108181813318.png)

## 3、IOC创建对象的方式

### 	**1.下标赋值index="0"**

### 	**2.类型赋值type="int"**

### 	**3.参数名赋值name="username"**

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182114804.png)

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182140221.png)

## 4、Spring配置

### 	4.1、别名

​					两个名字都可以用

```java
<alias name="he" alias="hello"/>  <!-- 取个别名 -->
```



![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182141290.png)

​						**用空格、逗号、分号隔开都可以**

### 	4.2、Bean的配置

### 	4.3、import

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182141369.png" alt="img" style="zoom:80%;" />











# 三、依赖注入开发

## 	1、构造器注入

​	<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182142549.png" alt="img" style="zoom:80%;" />

​		**constructor-arg**

## 	2、Set方式注入

​	**依赖注入：Set注入!**

​		**。依赖: bean对象的创建依赖于容器!**
​		**。注入: bean对象中的所有属性，由容器来注入!**

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108182142127.png)

```java
<!-- 声明-->
<bean name="address" class="com.pojo.Address"/>
    
<bean id="student" class="com.pojo.Student">

    <!--    常量    -->
    <property name="name" value="何昱飞"/>
    
    <!--    对象    -->
    <property name="address" ref="address"/>
    
    <!--    数组    -->
    <property name="books" >
        <array>
            <value>熊偶记</value>
            <value>西游记</value>
        </array>
    </property>
    
    <!--    list集合    -->
    <property name="hobbys">
        <list>
            <value>听歌</value>
            <value>打游戏</value>
        </list>
    </property>
    
    <!--    map集合    -->
    <property name="cards">
        <map>
            <entry key="身份证" value="430724"/>
            <entry key="银行卡" value="4307241999"/>
        </map>
    </property>
    
    <!--    set集合    -->
    <property name="games">
        <set>
            <value>COC</value>
        </set>
    </property>
    
    <!--    String的null    -->
    <property name="wife">
        <null/>
    </property>
    <!--    property集合    -->
    <property name="info">
        <props>
            <prop key="学号">2018401304</prop>
            <prop key="性别">男</prop>
            <prop key="username">2435823336</prop>
        </props>
    </property>
</bean>
```

​				

## 	3、第三方(p标签/c标签)注入

```java
public class User {
    private  String name;
    private int age;
}
```



#### 		① p标签property

p标签用于property,必须在xml导入

```xml
 xmlns:p="http://www.springframework.org/schema/p"
```

```xml
 <!--    p命名空间注入-->
    <bean id="user" class="com.pojo.User" p:name="何昱飞" p:age="21"/>
```

#### 	②c标签constructor

c标签用于构造器(constructor),也在xml导入

```xml
xmlns:c="http://www.springframework.org/schema/c"
```

```xml
<!--   c命名空间注入-->
<bean id="user2" class="com.pojo.User" c:name="何昱飞2" c:age="21"/>
```

## 	4、bean的作用域

- ##### singleton  单例模式（Spring默认机制）不创建新对象

  ```java
   <bean id="user2" class="com.pojo.User" c:name="何昱飞2" c:age="21" scope="singleton"/>
  ```

- ##### prototype  原型模式

  每次从容器中get的时候，都会产生一个新对象!

  ```java
      <bean id="user2" class="com.pojo.User" c:name="何昱飞2" c:age="21" scope="prototype"/>
  ```

- ##### request(创建后就失效)、session(session中使用) 、application(全局使用)、websocket    这些作用域只能在web开发中使用到

# 四、Bean的自动装配

## 1、概念

### 1.1 概述

自动装配是Spring满足bean依赖一种方式
Spring会在上下文中自动寻找，并自动给bean装配属性

### 1.2 在Spring中有三种装配的方式

1. 在xml中显示的配置
2. 在java中显示配置
3. 隐式的自动装配bean==【本文】==

## 2、autowire="byName"

使用byName的时候，People类中有Cat类这个属性，那么在定义people（bean）的时候 `autowire="byName"`就就会自动找在哪里定义了Cat（bean）

注意：Cat（bean）的id要唯一 ！

​	如果没找到就空指针异常

```xml
<bean id="cat" class="com.pojo.Cat"/>
<bean id="dog" class="com.pojo.Dog"/>
<bean id="people" class="com.pojo.People" autowire="byName">
    <property name="name" value="何昱飞" />
    <!--作用就是： 下面的两句就不用写了-->
    <!--        <property name="dog" ref="dog"/>-->
    <!--        <property name="cat" ref="cat"/>-->
</bean>
```

## 3、autowire="byType"

使用byType的时候，People类中有Cat类这个属性，那么在定义people这个bean的时候 `autowire="byType"`就就会自动找在哪里定义了Cat（bean）

注意：Cat（bean）只能定义一次，不然会出问题的

==id也可省略==

```xml
<bean  class="com.pojo.Cat"/>
<bean  class="com.pojo.Dog"/>
<bean id="people" class="com.pojo.People" autowire="byType">
<property name="name" value="何昱飞" />
</bean>
```

相当于原版：

```xml
<bean id="people" class="com.pojo.People">
    <property name="name" value="何昱飞"/>
    <property name="cat" ref="cat"/>
    <property name="dog" ref="dog"/>
</bean>
```

## 4、注解自动装配

jdk1 .5支持的注解,Spring2.5就支持注解了!

要使用注解须知:
	1.导入约束: context约束
	2.配置注解的支持: 

><context:annotation-config/>    //作用是式地向 Spring 容器注册。
>
>当使用 <context:component-scan/> 扫描包路径选项后，就可以将 <context:annotation-config/> 移除了。

### （1）依赖

```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd">
        
        <context:annotation-config/>

    </beans>
```

### （2）@Autowired（使用最多、byname） 

​	@Autowired
​	直接在**属性**上使用即可!也可以在set方式上使用!
​	使用Autowired我们可以不用编写Set方法了，前提是你这个自动装配的属性在IOC(Spring)容器中存在，且符合名字byname!

就是说必须写上`<bean id="cat" class="com.pojo.Cat"/>`或者`@Component`

```java
public class People {
    @Autowired
    private Cat cat; //这是个类
    @Autowired
    private Dog dog;//这是个类
    private string name;
}
```

它是通过反射来赋值的，所以在实体类里面连set都不需要写

### （3）@Nullable

```
@Nu11ab1e  字段标记了这个注解，说明这个字段可以为null;
```

```java
    public User(@Nullable String name) {
        this.name = name;
    }
```

### （4）Qualifier

如果@Autowired自动装配的环境比较复杂，自动装配无法通过一个注解@Autowired完成的时候、我们可以使用@Qualifier(value="xxx")去配置@Autowired的使用，指定一个唯一的bean对象注入!

先写上属性：

```xml
<bean id="cat" class="com.pojo.Cat"/>
<bean id="cat2" class="com.pojo.Cat"/>
<bean id="dog" class="com.pojo.Dog"/>
<bean id="dog2" class="com.pojo.Dog"/>
<bean id="people" class="com.pojo.People">
    <property name="name" value="何昱飞" />
</bean>
```

然后用Qulifier通过value={id}指定特定的bean

```java
@Autowired
@Qulifier(value = "cat2")
private Cat cat;
@Resource(name = "dog2")
private Dog dog;
private String name;
```

Qualifier上面一定要有个Autowired，而Resource不需要有



### (3)@Resource

**首先看类型有没有对得上的，没有的话就对名字，要是类型有两个就看有没有指定name值**

```java
@Resource
private Dog dog;
```

```java
@Resource(name = "dog2")
private Dog dog;
```



### （4）@autowired和@Resource的异同

- 都是用来自动装配的，都可以放在属性字段上

- @Autowired 通过byType的方式实现，而且必须要求这个对象存在!【常用】

- @Resource 默认通过byname的方式实现，如果找不到名字，则通过byType实现!如果两个都找不到的情下，就报错!【常用】

- 执行顺序不同:@Autowired通过byType的方式实现



# 五、使用注解开发

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <context:annotation-config/>
    <!--    扫描指定的包，这个包下的注解就会生效-->
        <context:component-scan base-package="com.pojo"/>
    </beans>
```

## 0、注解生效



><context : annotation-config/>

将带有@component,@service,@Repository等注解的对象注册到spring容器中的功能。



><context: component-scan base-package="com.dao" />

**指定要扫描的包，这个包下的注解就会生效,比上面的功能更齐全(还能扫描指定包)**

**context:component-scan**除了具有前者的功能之外，它还可以在指定的package下扫描以及注册javaBean 。

也能自动将带有@component,@service,@Repository等注解的对象注册到spring容器中的功能。

**因此当使用 context:component-scan 后，就可以将前者移除。**

## 1、Spring托管

#### 	@Component组件

​	Component : 组件，放在类上，说明这个类被Spring管理了，就是bean!

```java
//Component等价于<bean id="user" class="com.pojo. User" />
@Component
public class User {
	public string name = "何昱飞";
}
```



## 2、属性如何注入

```java
@Component
public class User {
	@value("何昱飞")
	public String name;
}
```



```java
@Value("何昱飞")
public void setName(string name) {
	this.name = name;
}
```



## 3、衍生的注解

@Component有几个衍生注解，我们在web开发中，会按照mvc三层架构分层!

-  dao 【**@Repository**】

-  service 【**@service**】

-  controller 【**@controller**】

  这四个注解功能都是一样的，都是代表将某个类注册到Spring中，装配Bean

  ```java
  @Repository
  public class UserDao {
      
  }
  @Controller
  public class Contnoll {
      
  }
  ```

  

## **4、++自动装配注解**

Autowired和Resource的区别

- 都是用来自动装配的，都可以放在属性字段上
- @Autowired通过byType的方式实现。而且必须要求这个对象存在!【常用】
- @Resource默认通过byname的方式实现，如果找不到名字，则通过byType实现!如果两个都找不到的情况下，就报错!【常用】
- 执行顺序不同:@ Autowired 通过byType的方式实现

## 5、作用域

```java
@Scope("prototype")
puoiic class User {
	public String name;
}
```

**singleton** 表示在spring容器中的单例，通过spring容器获得该bean时总是返回唯一的实例
**prototype**表示每次获得bean都会生成一个新的对象
**request**表示在一次http请求内有效（只适用于web应用）
**session**表示在一个用户会话内有效（只适用于web应用）
**globalSession**表示在全局会话内有效（只适用于web应用）

## 6、小节

xml与注解:

-  xml更加万能，适用于任何场合!维护简单方便。
-  注解不是自己类使用不了，维护相对复杂!

xml与注解最佳实践:

-  xml 用来管理bean;

-  注解只负责完成属性的注入;

- 我们在使用的过程中，只需要注意一个问题:必须让注解生效，就需要开启注解的支持

  

  

















# 六、使用Java的方式配置Spring

`Component`:  这里这个注解的意思，就是说类被Spring接管了，注册到了容器中

```java
@Component
public class User{
	private String name;

	public String getName() {
		return name;
	}
	@Value("何昱飞飞java")
	public void setName(String name) {
		this.name = name;
	}
}
```



**`Configuration`**：	这个也会Spring容器托管，注册到容器中，因为他本来就是一个Component，Configuration代表这是一个配置类，就和我们之前看的beans.xml

**`Bean`**：  注册一个bean，就相当于我们之前写的一个<bean>标签**这个方法的名字，就是bean标签中的id属性**
这个方法的返回值,就相当于bean标签中的class属性

```java
@Configuration 
public class Myconfig {
	@Bean
	public User getUser(){
		return new User();
	}
}
```





#### 					使用class类不要加引号！！！

​		配置类方式：如果完全使用了配置类方式去做，我们就只能通过 AnnotationConfig 上下文来获取容器，通过配置类的class对象加载!

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108191021200.png)

这种纯Java的配置方式，在SpringBoot中随处可见!















# 七、代理模式

​		***为什么要学习代理模式?因为这就是SpringAOP的底层!【SpringAOP和SpringMVC】***

## 	1、代理模式优缺点

### 1.1.1、代理模式优点

- 可以便真实角色的操作更加纯粹!不用去关注一些公共的业务

- 公共也就就交给代理角色!实现了业务的分工!

- 公共业务发生扩展的时候，方便集中管理!

  

### 1.1.2、代理模式缺点 

- 一个真实角色就会产生一个代理角色；代码量会翻倍~ 开发效率会变低~
  
  

## 	2、动态代理

- 动态代理和静态代理角色一样
- 动态代理的代理类是动态生成的，不是我们直接写好的!
- 动态代理分为两大类：基于接口的动态代理，基于类的动态代理
  - 基于接口---JDK动态代理【我们在这里使用】
  - 基于类: cglib
  - java字节码实现: javasist

​	**动态代理模板**

```java
/**
 * 自动生成代理类
 */
public class ProxyInvocationHandler implements InvocationHandler {
    private Object target;
    public ProxyInvocationHandler() {}
    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }
    /**
     *  生成得到代理类
     * @return 代理类
     */
    public Object getProxy(){
        //System  ClassLoader系统类加载器,负责在JVM启动时加载来自java命令的class文件，以及classpath环境变最所指定的jar包和类路径
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }

    /**
     * 处理代理实例,返回结果
     * @param method 反射执行的方法，可以用method.getName()得到方法名
     * @return 实体类
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //动态代理的本质,就是使用反射机制实现!
        return method.invoke(target, objects);
    }

    public void seeHost(){
        System.out.println("中介带你看房~");
    }
    public void needMoney(){
        System.out.println("中介收钱~");
    }
}
```

# 八、AOP

## 1、什么是AOP

​	AOP (Aspect Oriented Programming)意为:面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108191022967.png)

## 2、AOP在Spring中的作用

 **提供声明式事务;允许用户自定义切面**

- 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志，安全，缓存，事务等等....
- 切面(ASPECT)：横切关注点被模块化的特殊对象。即，它是一个类。**[Log类]**
- 通知(Advice)：切面必须要完成的工作。即，它是类中的一个方法。**[Log类里面的方法]**
- 目标（Target): 被通知对象。
- 代理(Proxy)：向目标对象应用通知之后创建的对象。
- 切入点（PointCut): 切面通知执行的"地点"的定义。
- 连接点 (JointPoint): 与切入点匹配的执行点。

**SpringAOP中，通过Advice定义横切逻辑，Spring中支持5种类型的Advice:**

​	![image-20210306155750605](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108191022616.png)

**即Aop在不改变原有代码的情况下，去增加新的功能．**



## 3、使用Spring实现AOP

**要使用AOP包，必须先导入依赖包**

```xml
<dependency>
<groupId>org.aspectj</groupId>
<artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
```



**配置aop需要导入aop的约束**

>​       xmlns:aop="http://www.springframework.org/schema/aop"
>
>​       http://www.springframework.org/schema/aop
>
>​		http://www.springframework.org/schema/aop/spring-aop.xsd





### 3.1、方式一：API实现接口

​	`使用Spring的API接口【主要Spring接口实现】`



#### 3.1.1、XML文件创建对象和执行

**execution表达式内容：execution(返回值  包  方法  参数)**

`execution(* com.service.UserServiceImpl.*(..)) 表示UserServiceImpl类中的任何方法和参数和返回值`

```xml
    <!--注册bean-->
    <bean id="userService" class="com.service.UserServiceImpl"/>
    <bean id="log" class="com.log.LogData"/>
    <bean id="afterLog" class="com.log.AfterLogData"/>

    <!--配置aop需要导入aop的约束-->
    <aop:config>
        <!--切入点:expression:表达式,execution（要执行的位置!-->
        <aop:pointcut id="pointcut" expression="execution(* com.service.UserServiceImpl.*(..))"/>
    <!--执行环绕增加 -->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
```



#### 3.1.2、要执行的类

**真正要执行的类**

```java
public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("增加了");
    }
    @Override
    public void delete() {
        System.out.println("删除了");
    }
    @Override
    public void update() {
        System.out.println("修改了");
    }
    @Override
    public void select() {
        System.out.println("查询了");
    }
}
```



在真正要执行类之前  先执行这个类**before**

实现**MethodBeforeAdvice**接口

```java
public class LogData implements MethodBeforeAdvice {
    /**
     * @param method 要执行的方法
     * @param objects 参数
     * @param target 目标对象
     */
    @Override
    public void before(Method method, Object[] objects, Object target) throws Throwable {
        System.out.println("Before:    MethodBeforeAdvice: 执行了"+target.getClass().getName() + "的" + method.getName() + "被执行了,因为是它之前执行，所有没有返回值");
    }
}
```



在真正要执行类执行之后 切入要执行的类**Affter**

实现**AfterReturningAdvice接口**

```java
public class AfterLogData implements AfterReturningAdvice {
    /**
     * @param returnValue 返回值
     * @param method 执行的方法
     * @param args 参数
     * @param target 执行的对象
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After:   AfterReturningAdvice: 执行了"+target.getClass().getName()+"的"+method.getName()+"方法，返回结果是"+returnValue);
    }
}
```



**测试类**

其中**getBean**必须写接口，不能写实现类UserService 

```java
public static void main(String[] args) {
    ApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
    //动态代理代理的是接口
    UserService userService = con.getBean("userService", UserService.class);
    userService.add();
}
```



### 3.2、方式二：自定义类

​	`**自定义来实现AOP【主要是切面定义】**`

要切入在**真正执行的类**  **先**或者**后**执行这个类

```java
public class DiyPointCut {
    public void before(){
        System.out.println("-------------方法执行前-------------");
    }
    public void after(){
        System.out.println("-------------方法执行后-------------");
    }
}
```

XML文件创建对象和执行

​        **<aop:before >** 之**前**执行的方法

​        **<aop:after >** 之**后**执行的方法

```xml
<!--方式二 自定义类-->
<bean id="diy" class="com.diy.DiyPointCut"/>
<aop:config>
    <!--自定义切面，ref需要引用的类-->
    <aop:aspect ref="diy">
        <!--切入点-->
        <aop:pointcut id="point" expression="execution(* com.service.UserServiceImpl.*(..))"/>
        <!--通知-->
        <aop:before method="before" pointcut-ref="point"/>
        <aop:after method="after" pointcut-ref="point"/>
    </aop:aspect>
</aop:config>
```



### 3.3、方式三：注解

​	`**注解实现AOP【主要是切面定义】**`

​	XML文件创建对象和执行，**必须先开启注解支持** 

```xml
<!--方式三 注解-->
<bean id="annotationPointCut" class="com.diy.AnnotationPointCut"/>
<!--开启注解支持    -->
<aop:aspectj-autoproxy/>
```

要切入的类

```java
@Aspect //标注这个类是一个切面
public class AnnotationPointCut {
    @Before("execution(* com.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("-------------AnnotationPointCut方法执行前-------------");
    }
    @After("execution(* com.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("-------------AnnotationPointCut方法执行后-------------");
    }
    //在环绕增强中，我们可以给定一个参数，代表我们要获取处理切入的点;
    @Around("execution(* com.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("签名："+jp.getSignature());
        //执行方法
        Object proceed = jp.proceed();
        System.out.println("proceed: "+proceed);
    }
}
```



Before的时候**接口最先**执行**其次自定义**，**最后注解**

After的时候**注解最先**其次**自定义**，最后**接口**









# **九、Mybatis-Spring**

## **1、步骤:**

### **1.1、导入相关jar包**

- junit
- mybatis
- mysql数据库 
- spring相关的
- aop织入
- mybatis-spring 【new】

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
</dependency>

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
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.3</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.3</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.3.3</version>
</dependency>
<dependency>
<!--asp织入包-->
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
<dependency>
    <!--mybatis-spring包-->
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>
<dependency>
    <!--用于在实体类生成构造器、getter/setter、equals、hashcode、toString方法-->
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.18</version>
    <scope>provided</scope>
</dependency>
```



### **1.2、编写配置文件**

#### 		1.2.1、编写实体类

​				**User类**

#### 		1.2.2、编写核心配置文件

​				**父亲  applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="spring-dao.xml"/>
    <!--    <import resource="spring-mvc.xml"/>-->

    <!-- 使用区-->
    <bean id="userMapper" class="com.dao.UserMapperImpl">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>

</beans>
```

​				**儿子之一  spring-dao.xml  (固定代码无需修改)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--DataSource:使用Spring的数据源替换Mybatis的配置  c3p0 dbcp druid
    我们这里使川Spring提供的JDBC : org.springframework.jdbc.datasource
     -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <!--    com.mysql.cj.jdbc.Driver"/>   -->
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSl=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>
    <!--  sqlSessionFactory  -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--  绑定Mybatis配置文件      -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/dao/*.xml"/>
    </bean>
    <!--  SqlSessionTemplate就是sqlSession  -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--  只能用构造器注入sqlSession，因为它没有set方法      -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

#### 	1.2.3、编写接口

​		**UserMapper**

```java
public interface UserMapper {
    public List<User> selectUser();
}
```

​		**UserMapperImpl**

```java
public class UserMapperImpl implements UserMapper {
    //所有操作，都使川sqLSession来执行，在原来，现在都是川SqLSessionTemplate
    private SqlSessionTemplate sqlSession = null;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    @Override
    public List<User> selectUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectUser();

    }
}
```

#### 	1.2.4、测试

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
for (User user:userMapper.selectUser()){
    System.out.println(user);
}
```

## 2、SqlSessionDaoSupport抽象类

​	**简化SQLSession的实例化步骤**

### 2.1、实现类继承SqlSessionDaoSupport

```java
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{
    @Override
    public List<User> selectUser() {
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}
```

### 2.2、配置xml中

#### 	spring-dao内容

​	**只需要实例化jdbc、sqlSessionFactory**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--DataSource:使用Spring的数据源替换Mybatis的配置  c3p0 dbcp druid
    我们这里使川Spring提供的JDBC : org.springframework.jdbc.datasource
     -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <!--    com.mysql.cj.jdbc.Driver"/>   -->
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSl=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>
    <!--  sqlSessionFactory  -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--  绑定Mybatis配置文件      -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/dao/*.xml"/>
    </bean>
</beans>
```

​		使用时把**sqlSessionFactory**赋值进去，就行了

```xml
<bean id="userMapper" class="com.dao.UserMapperImpl">
    <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```



# 十、声明式事务

## 1、回顾事务

- 把一组业务当成一个业务来做;要么都成功，要么都失败! 
- 事务在项目开发中，十分的重要，涉及到数据的一致性问题，不能马虎!
- 确保完整性和一致性;

### 1.1、事务ACID原则:

​			**原子性**

​			要么都成功，要么都失败

​			**一致性**

​			数据两边必须保持一直，不能一个加了，另一个没减

​			**隔离性**

​					多个业务可能操作同一个资源，防止数据损坏

​			**持久性**

​					事务一旦提交，无论系统发生什么问题，结果都不会再被影响，被持久化的写到存储器中!

### 1.2、spring中的事务管理

#### 1.2.1、分类

- ​	声明式事务:AOP
- ​	编程式事务:需要再代码中，进行事务的管理

#### 1.2.1、作用

- ​    如果不配置事务，可能存在数据提交不一致的情况;
- ​	如果我们不在SPRING中去配置声明式事务，我们就需要在代码中手动配置事务
- ​    事务在项目的开发中十分重要，设计到数据的一致性和完整性问题，不容马虎

#### 1.2.3、使用方法

```xml
<!--  配置声明式事务  -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
    <!--        <constructor-arg ref="dataSource"/>-->
</bean>

<!--    结合AOP实现事务的织入-->
<!--    配置事务通知-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!--        给那些方法配置事务-->
    <!--REQUIRED:支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。-->
    <tx:attributes>
        <tx:method name="add" propagation="REQUIRED"/>
        <tx:method name="delete" propagation="REQUIRED"/>
        <tx:method name="update" propagation="REQUIRED"/>
        <tx:method name="query" read-only="true"/>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
<!--    配置事务切入-->
<!--    下面意思是说mapper下面的所有类都可以用txAdvice这个事务-->
<aop:config>
    <aop:pointcut id="txPointCut" expression="execution(* com.dao.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
</aop:config>
```