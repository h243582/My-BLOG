

# 前端导包

### JQuary

```html
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/.js"></script>
```

### Vue

```html
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
```

### BootStrop4

```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-olOxEXxDwd20BlATUibkEnjPN3sVq2YWmYOnsMYutq7X8YcUdD6y/1I+f+ZOq/47" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-NU/T4JKmgovMiPaK2GP9Y+TVBQxiaiYFJB6igFtfExinKlzVruIK6XtKqxCGXwCG" crossorigin="anonymous"></script>
```

### ==layui==

```html
<link rel="stylesheet" href="//unpkg.com/layui@2.6.8/dist/css/layui.css">
<script src="//unpkg.com/layui@2.6.8/dist/layui.js"></script>

```

### Element

```html
<!-- 引入样式 -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
```



# Maven本身

## Maven的继承

```xml
<modules>
    <module>mybatis-01-first</module>
    <module>mybatis-02-</module>
    <module>mybatis-04</module>
    <module>mybatis-05</module>
</modules>
```

## install运行测试

为了能够运行测试，Maven需要在项目中引入测试框架的依赖。

```xml
<build>
	<plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.1</version>
            <configuration>
                <skipTests>true</skipTests>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 找不到xml文件

**核心配置文件中注册mappers：在pom.xml中插入这段代码，用于识别出Mapper**

```xml
<!--maven由于他的约定大于配置，我们之后可以能遇到我们写的配置文件，无法被导出或者生效的问题，解决方-->
<!--在build中配置resources，来防止我们资源导出失败的问题-->
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



# SpringBoot所有

```xml

```



# A、

## AOP

```xml
<dependency>
<groupId>org.aspectj</groupId>
<artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.2.9.RELEASE</version>
</dependency>
```

# B、

```xml

```

# C、

```xml

```

# D、

```xml

```

# E

## ehcache

```xml
<!-- https://mvnrepository.com/artifact/org.ehcache/ehcache -->
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.9.2</version>
</dependency>
```



# F、

## fileupload文件上传

```xml
<dependency>
    <!--文件上传-->
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

# G、

```xml

```

# H、

```xml

```

# I、

```xml

```

# J、

## junit

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>
```

## jstl

```
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```

# K、

```xml

```

# L

## log4j

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

## lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
    <scope>provided</scope>
</dependency>
```

# M

## mysql

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
```

## mybatis

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
```

## mybatis-Ehcache

```xml
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.1</version>
</dependency>
```

## mybatis-Spring

```
<dependency>
    <!--mybatis-spring包-->
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>
```

# N、

```xml

```

# O、

```xml

```

# P、

```xml

```

# Q、

```xml

```

# R、

```xml

```

# SSSSS

## servlet

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>

<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2.1-b03</version>
</dependency>
```



## Spring 

1. ```xml
   <dependency>
       <!--            Spring包-->
       <groupId>org.springframework</groupId>
       <artifactId>spring-webmvc</artifactId>
       <version>5.3.5</version>
   </dependency>
   
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-jdbc</artifactId>
       <version>5.3.5</version>
   </dependency>
   
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-aop</artifactId>
       <version>5.3.5</version>
   </dependency>
   <dependency>
   	<groupId>org.aspectj</groupId>
   	<artifactId>aspectjweaver</artifactId>
      	   <version>1.9.4</version>
   </dependency>
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-core</artifactId>
       <version>5.3.5</version>
   </dependency>
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-beans</artifactId>
       <version>5.3.5</version>
   </dependency>
   ```

## 数据效验

```xml
<dependency>
    <!-- 数据校验(比如email) -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

# T、

## 腾讯云

```xml
    <dependency>
        <!--   腾讯云         -->
        <groupId>com.qcloud</groupId>
        <artifactId>cos_api</artifactId>
        <version>5.6.52</version>
    </dependency>
```

# U、

```xml

```

# V、

```xml

```

# W、

## 文件上传

```xml
        <dependency>
            <!--文件上传-->
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.4</version>
        </dependency>
```

# X、

```xml

```

# Y、

```xml

```

# Z、

```xml

```

























