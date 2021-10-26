# 一、注解的第一个MVC

![图片](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210716085946.gif)

## 1、依赖

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
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
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>

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

## 2、web.xml (死代码，永远不变)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--注册DispatcherServlet：这个是SpringMVC的核心;请求分发器，前端控制器-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!--绑定SpringMVC的核心配置文件 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-1 -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!--   /   匹配所有请求（不包括.jsp）
               /*  匹配所有请求（包括.jsp）-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

## 3、springmvc-servlet.xml（放在resources中，永不变）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
        xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:c="http://www.springframework.org/schema/c"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/cache
        http://www.springframework.org/schema/cache/spring-cache.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd"
>

    <!--  自动扫描包，让指定包下的注解生效，由IOC容器统一管理  -->
    <context:component-scan base-package="com.heyufei.controller"/>
    <!-- 让SpringMVC不处理静态资源.css .js .html .mp3 .mp4-->
    <mvc:default-servlet-handler/>

    <!--    支持mvc注解驱动
    在Spring中一般采用@RequestMapping注解来完成映射关系
    要想使@RequestMapping注解生效
    必须注测DefaultAnnotationHandLerMapping和AnnotationMethodHandLerAdapter实例
    这两个实例分别在类级别和方法级别处理。
    而annotation-driven配置帮助我们自动完成上述两个实例的注入。
    -->
    <mvc:annotation-driven/>

    <!--  视图解析器: DispatcherServlet 给他的 NodeAndView  -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

## 2、Controller视图层(每个方法都是一个servlet)

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/heyufei")
public class HelloController {

    //localhost:8080/heyufei/hhh
    @RequestMapping("/hhh")
    public String hello(Model model){
        //封装数据
        model.addAttribute("msg","我是addAttribute");
        return "hello";//请求转发到hello.jsp去
    }
    
    @RequestMapping("/myServlet")
    public String text1(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        return "hello";
    }
}
```

### 2.1、addAttribute（等同于setAttribute()）

​        model.addAttribute("msg","我是addAttribute");

### 2.2、@RequestMapping("index")（等同于urlPatterns）

>@RequestMapping("index") === @RequestMapping(value= "index") 
>
>=== @RequestMapping(path= "index") 
>
>//value或者path可以省略，**不可以用name**

```java
@RequestMapping("/hhh")
public String hello(Model model){
    //封装数据
    model.addAttribute("msg","我是addAttribute");
    return "hello";//请求转发到hello.jsp去
}
```

### 2.3、@RequestMapping注解中的返回值

如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析，变成请求转发的地址。

## 3、目录结构

![image-20210507200919483](https://i.loli.net/2021/05/07/eFh2MZPNIoxjvBL.png)

## 4、MVC要用到的注解名

```java
@component 组件	
@service	service
@controller	控制层放servlet
@Repository	dao
```



# 二、RestFul风格

## 1、区别

### 原来的：

```java
@RequestMapping("/run")
public String test1(int a, int b, Model model) {
    int res = a + b;
    model.addAttribute("msg", "结果为" + res);
    return "hello";
}
```

原来的：http://localhost:8080/run?a=1&b=2

### 修改后：

只有自己才知道**传的是什么值**，安全

```java
@RequestMapping("/run2/{a}/{b}")
public String test2(@PathVariable int a,@PathVariable int b, Model model) {
    int res = a + b;
    model.addAttribute("msg", "结果为" + res);
    return "hello";
}
```

http://localhost:8080/run2/1/2

**但是如果接收值不匹配数值类型，会报404错**

# 三、设置请求类型

## 1、请求类型

1. **@RequestMapping任何都可以请求**

   

2. @Get   **get查询请求**

3. @Post   **post新增请求**

4. @Put  **put更新请求**

5. @Delete   **delete删除请求**

6. @Patch **patch请求**

## 2、method

method可以约束请求类型，可以用method来定义请求类型

1. @RequestMapping(value ="/run3",method = RequestMethod.GET)
2. @RequestMapping(value ="/run3",method = RequestMethod.POST)
3. @RequestMapping(value ="/run3",method = RequestMethod.PUT)
4. @RequestMapping(value ="/run3",method = RequestMethod.DELETE)
5. @RequestMapping(value ="/run3",method = RequestMethod.POST)

## 3、缩写版

**@GetMapping 是@RequestMapping(method =RequestMethod.GET)的快捷方式**

1. @RequestMapping   **任何都可以请求**
2. @GetMapping   **get查询请求**
3. @PostMapping   **post新增请求**
4. @PutMapping   **put更新请求**
5. @DeleteMapping   **delete删除请求**
6. @PatchMapping  **patch请求**





# 四、页面跳转

## 4.1、请求转发 forward

### 4.1.2、缩写版

​	**return "forward:hello"; //请求转发**

### 4.1.1、缩写版

​	**return "hello"; //请求转发**

```
    @RequestMapping("/myServlet")
    public String text2(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        System.out.println(session.getId());

//        return "hello";//请求转发
        return "forward:hello";//请求转发
    }
```

## 4.2、重定向  redirect

### 4.2.1、无缩写版

​        **return "redirect:hello"; //重定向**

```
@RequestMapping("/myServlet")
public String text1(HttpServletRequest request, HttpServletResponse response){

    return "redirect:hello"; //重定向
}
```

## 4.3、ServletAPI

通过设置ServletAPI , 不需要视图解析器 .

1. 通过HttpServletResponse进行输出

   `rsp.getWriter().println("Hello,Spring BY servlet API");`

2. 通过HttpServletResponse实现重定向

    `rsp.sendRedirect("/index.jsp");`

3. 通过HttpServletResponse实现转发

   `req.setAttribute("msg","/result/t3");`
   `req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req,rsp);`

```java
@Controller
public class ResultGo {
   @RequestMapping("/result/t1")
   public void test1(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
       rsp.getWriter().println("Hello,Spring BY servlet API");
  }

   @RequestMapping("/result/t2")
   public void test2(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
       rsp.sendRedirect("/index.jsp");
  }

   @RequestMapping("/result/t3")
   public void test3(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
       //转发
       req.setAttribute("msg","/result/t3");
       req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req,rsp);
  }
}
```





# 五、数据处理

```html
<form action="/data2">
    <input type="text" name="name" value="何昱飞">
    <input type="submit" value="提交">
</form>
```

## 5.1、提交数据

### 5.1.1、提交名和参数名一致

提交数据: http://localhost:8080/hello?name=heyufei

处理方法:

```java
@RequestMapping("/data1")
private String test1(String name){
    System.out.println(name);
    return "hello";
}
```

### 5.1.2、提交名和参数名不一致

提交数据: http://localhost:8080/hello?name=heyufei

处理方法:

```java
@RequestMapping("/data2")
private String test2(@RequestParam("name") String username){
    System.out.println(username);
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

### 5.1.3、提交对象

提交数据: http://localhost:8080/hello?id=1&name=heyufei&age=21

处理方法:

```java
@RequestMapping("/data3")
private String test3(User user){
    System.out.println(user);
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

前端传递的参数名和对象名必须一致，否则就是null。

## 5.2、数据显示到前端

### 5.2.1、通过ModelAndView

**addObject** 来表示**setAttribute**

**setViewName**来表示切换页面 

```
@RequestMapping("/return1")
public ModelAndView test1(){
    ModelAndView mv = new ModelAndView();
    mv.addObject("msg","返回了return1");
    mv.setViewName("forward:/WEB-INF/jsp/hello.jsp");
    return mv;
}
```

### 5.2.2、通过Model

**addAttribute** 来表示**setAttribute**

加一个形参**Model**

```
@RequestMapping("/data1")
private String test1(String name, Model model){
    model.addAttribute("msg","搞完了");
    return "forward:/WEB-INF/jsp/hello.jsp";
}
```

### 5.2.3、通过ModelMap

与Model一样，形参改一下名就行了

# 六、乱码问题

## 6.1、解决方法1 Get

使用Get请求，但是地址栏会显示数据内容

## 6.2、解决方法2 过滤器

在web.xml中加入

```xml
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>com.heyufei.filter.EncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

**注意：这里写/*，写/的话过滤不了jsp页面，不能解决乱码**

然后创建一个类

```java
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
```

# 七、JSON交互处理

## 7.1、JSON 和 JavaScript 对象互转

将**JS对象**转换成**JSON字符串**

>**var str = JSON.stringify(user1);** 



**JSON字符串**变回**JS对象**

>**var us = JSON.parse(str);** 

## 7.2、返回JSON数据

### 7.2.1、去掉乱码

>在这里加上这句话，否则一定会有乱码
>
>**produces = "application/json;charset=utf-8")**

```java
@RequestMapping(value = "/json",produces = "application/json;charset=utf-8")
```

但是有个更好地办法，在MVC的配置文件中加入这段话，就不用再具体定义编码了

```xml
<mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="UTF-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

### 7.2.2、@ResponseBody

>不请求到别的页面，只返回字符串，加载方法上![](https://i.loli.net/2021/06/14/SXnOk9HDiRsLt3c.png)

```
@ResponseBody
@RequestMapping(value = "/json",produces = "application/json;charset=utf-8")
public String json1() throws JsonProcessingException {
	。。。
}
```

## 7.3、返回Date日期数据

### 7.3.1、无任何格式

```java
@RequestMapping("/date1")
@ResponseBody
public String test1() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    //创建时间一个对象，java.util.Date
    Date date = new Date();
    //将我们的对象解析成为json格式
    String str = mapper.writeValueAsString(date);

    return str;
}
```

### 7.3.2、有格式

```java
@RequestMapping("/date2")
@ResponseBody
public String json4() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    //不使用时间戳的方式
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    //自定义日期格式对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH：mm:ss");
    //指定日期格式
    mapper.setDateFormat(sdf);

    Date date = new Date();
    String str = mapper.writeValueAsString(date);
    return str;
}
```

### 7.3.3、自定义工具类随时调用

```java
@RequestMapping("/date2")
@ResponseBody
public String json4() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    //不使用时间戳的方式
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    //自定义日期格式对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH：mm:ss");
    //指定日期格式
    mapper.setDateFormat(sdf);

    Date date = new Date();
    String str = mapper.writeValueAsString(date);
    return str;
}
```

# 八、拦截器

## 1、概述

SpringMVC的处理器**拦截器**`类似于`Servlet开发中的**过滤器**Filter,用于对处理器进行预处理和后处理。开发者可以自己定义一些拦截器来实现特定的功能。

## **2、过滤器与拦截器的区别**

拦截器是AOP思想的具体应用。

**过滤器**

- servlet规范中的一部分，任何java web工程都可以使用
- 在url-pattern中配置了/*之后，可以对所有要访问的资源进行拦截

**拦截器** 

- 拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
- 拦截器只会拦截访问的控制器方法， 如果访问的是jsp/html/css/image/js是不会进行拦截的

## 3、使用方法

### 3.1、SpringMVC.xml

```xml
<!--关于拦截器的配置-->
<mvc:interceptors>
    <mvc:interceptor>
        <!-- /** 包括路径及其子路径-->
        <!-- /admin/* 拦截的是/admin/add等等这种 , /admin/add/user不会被拦截-->
        <!-- /admin/** 拦截的是/admin/下的所有-->
        <mvc:mapping path="/**"/>
        <mvc:exclude-mapping path="/**/fonts/*"/>
        <mvc:exclude-mapping path="/**/*.css"/>
        <mvc:exclude-mapping path="/**/*.js"/>
        <mvc:exclude-mapping path="/**/*.png"/>
        <mvc:exclude-mapping path="/**/*.gif"/>
        <mvc:exclude-mapping path="/**/*.jpg"/>
        <mvc:exclude-mapping path="/**/*.jpeg"/>

        <!--bean配置的就是拦截器-->
        <bean class="com.ssm.interceptor.MyInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

### 3.2、自定义一个Interceptor类实现HandlerInterceptor接口

```java
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理的方法之前执行
     * @return 返回true执行下一个拦截器，false就不执行下一个拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是登陆页面则放行
        String uri = request.getRequestURI();
        String url = String.valueOf(request.getRequestURL());
        String contextPath = request.getContextPath();

        System.out.println("uri: " + uri);
        System.out.println("url: " + url);
        System.out.println("ContextPath: " + contextPath);
        System.out.println("----------------------------------------------");
        System.out.println(request.getSession().getAttribute("user"));
        if (uri.contains("login")||uri.contains("a3")||uri.contains("a2")) {
            return true;
        }
        // 如果用户已登陆也放行
        if(request.getSession().getAttribute("user") != null) {
            return true;
        }
        // 用户没有登陆跳转到登陆页面
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        return false;
    }

    // 在请求处理方法执行之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);    }

    //在dispatcherServlet处理后执行,做清理工作.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);    }
}

```

### 4、注意

> **springMVC的拦截器不能过滤jsp文件**
>
> **拦截器是会拦截静态资源的** 比如html js css image这类，虽然都是页面 但是html属于静态资源,，jsp不属于



# 九、文件上传和下载

## 1、springMVC与上传和下载

> springMVC可以很好的支持文件上传，但是SpringMVC上下文中默认没有装配MultipartResolver，因此默认情况下其不能处理文件上传工作。如果想使用Spring的文件上传功能，则需要在上下文中配置MultipartResolver。

> 前端表单要求：**表单必须为POST，enctype设置为multipart/form-data**。只有在这样的情况下，浏览器才会把用户选择的文件以二进制数据发送给服务器；
>
> **对表单中的 enctype 属性做个详细的说明：**

## 2、表单中的 enctype 

- *****`application/x-www=form-urlencoded`：默认方式，只处理表单域中的 value 属性值，采用这种编码方式的表单会将表单域中的值处理成 URL 编码方式。
- `multipart/form-data`：以**二进制流**的方式来处理表单数据，**把文件的内容也封装到请求参数中**，不会对字符编码。**传文件**
- `text/plain`：除了把空格转换为 "+" 号外，其他字符都不做编码处理，直接通过表单发送邮件。

```html
<form action="" enctype="multipart/form-data" method="post">
   <input type="file" name="file"/>
   <input type="submit">
</form>
```

一旦设置了enctype为multipart/form-data，浏览器即会采用二进制流的方式来处理表单数据，而对于文件上传的处理则涉及在服务器端解析原始的HTTP响应。成为Servlet/JSP程序员上传文件的最佳选择。

- Servlet3.0规范已经提供方法来处理文件上传，但这种上传需要在Servlet中完成。
- 而Spring MVC则提供了更简单的封装。
- Spring MVC为文件上传提供了直接的支持，这种支持是用即插即用的MultipartResolver实现的。
- Spring MVC使用Apache Commons FileUpload技术实现了一个MultipartResolver实现类：
- CommonsMultipartResolver。因此，**SpringMVC的文件上传还需要依赖Apache Commons FileUpload的组件。**

## 3、文件上传

### 3.1、导包

```xml
<!--文件上传-->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
    <dependency>
        <!--   腾讯云         -->
        <groupId>com.qcloud</groupId>
        <artifactId>cos_api</artifactId>
        <version>5.6.52</version>
    </dependency>
```

### 3.2、配置bean：multipartResolver

【**注意！！！这个bena的id必须为：multipartResolver ， 否则上传文件会报400的错误！在这里栽过坑,教训！**】

```xml
<!--文件上传配置-->
<bean id="multipartResolver"  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
   <!-- 请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1 -->
   <property name="defaultEncoding" value="utf-8"/>
   <!-- 上传文件大小上限，单位为字节（10485760=10M） -->
   <property name="maxUploadSize" value="10485760"/>
   <property name="maxInMemorySize" value="40960"/>
</bean>
```

CommonsMultipartFile 的 常用方法：

- **String getOriginalFilename()：获取上传文件的原名**
- **InputStream getInputStream()：获取文件流**
- **void transferTo(File dest)：将上传文件保存到一个目录文件中**

### 3.3、编写前端页面

```html
<form action="/upload" enctype="multipart/form-data" method="post">
 <input type="file" name="file"/>
 <input type="submit" value="upload">
</form>
```

### 3.4、编写控制层

#### 3.4.1、第一种（普通）

```java
//@RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
//批量上传CommonsMultipartFile则为数组即可
@RequestMapping("/upload")
public String fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request) throws IOException {

    //获取文件名 : file.getOriginalFilename();
    String uploadFileName = file.getOriginalFilename();
    System.out.println("上传文件名 : "+uploadFileName);
    //如果文件名为空，直接回到首页！
    if ("".equals(uploadFileName)){
        return "redirect:/app/register.jsp";
    }

    //上传路径保存设置
    String path = request.getServletContext().getRealPath("/upload");
    System.out.println("path----------------------"+path);
    //如果路径不存在，创建一个
    File realPath = new File(path);
    if (!realPath.exists()){
        realPath.mkdirs();
    }
    System.out.println("上传文件保存地址："+realPath);

    InputStream is = file.getInputStream(); //文件输入流
    OutputStream os = new FileOutputStream(new File(realPath,uploadFileName)); //文件输出流

    //读取写出
    int len=0;
    byte[] buffer = new byte[1024];
    while ((len=is.read(buffer))!=-1){
        os.write(buffer,0,len);
        os.flush();
    }
    os.close();
    is.close();
    return "redirect:/app/register.jsp";
}
```

#### 3.4.2、第二种（transferTo）

```java
//采用file.transferTo 来保存上传的文件
@RequestMapping("/upload2")
public String  fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {

    //上传路径保存设置
    String path = request.getServletContext().getRealPath("/upload");
    File realPath = new File(path);
    if (!realPath.exists()){
        realPath.mkdir();
    }
    //上传文件地址
    System.out.println("上传文件保存地址："+realPath);

    //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
    file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));

    return "redirect:/index.jsp";
}
```



## 4、文件下载

### 4.1、文件下载步骤：

1、设置 response 响应头

2、读取文件 -- InputStream

3、写出文件 -- OutputStream

4、执行操作

5、关闭流 （先开后关）

### 4.2、代码实现

```java
@RequestMapping(value = "/download")
public String downloads(HttpServletResponse response, HttpServletRequest request) throws Exception {
    //要下载的图片地址
    String path = request.getServletContext().getRealPath("/upload");
    String fileName = "面试常考编程题.md";

    //1、设置response 响应头
    response.reset(); //设置页面不缓存,清空buffer
    response.setCharacterEncoding("UTF-8"); //字符编码
    response.setContentType("multipart/form-data"); //二进制传输数据
    //设置响应头
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

    File file = new File(path, fileName);
    //2、 读取文件--输入流
    InputStream input = new FileInputStream(file);
    //3、 写出文件--输出流
    OutputStream out = response.getOutputStream();

    byte[] buff = new byte[1024];
    int index = 0;
    //4、执行 写出操作
    while ((index = input.read(buff)) != -1) {
        out.write(buff, 0, index);
        out.flush();
    }
    out.close();
    input.close();
    return null;
}
```

![image-20210717162219601](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210717162228.png)

