# 一、Jackson概念

## **1、作用**

​	它是用来前后端的交互功能，属于SpringMVC

# 二、ObjectMapper

### 2.1、作用

​	是用来后端接收的json数据转换成各种格式。也可以转换各种格式

#### 2.1.1、第一步，初始化

> ObjectMapper mapper = new ObjectMapper();

#### 2.1.2、写到文件中

```java
User user = new User("243582", "h2435823336");
//mapper.writeValue(new File("test.txt"), user); // 写到文件中
//mapper.writeValue(System.out, user); //写到控制台
```



#### **2.1.3、对象转为字符串**

```java
//对象转为字符串
String json_String = mapper.writeValueAsString(user);
System.out.println("对象转为字符串：" + json_String);
```



#### **2.1.4、json字符串转为对象**

```java
User jsonStr_Class = mapper.readValue(json_String, User.class);
System.out.println("json字符串转为对象：" + jsonStr_Class);
```

#### **2.1.5、对象转为byte数组**

```java
byte[] class_ByteArr = mapper.writeValueAsBytes(user);
System.out.println("对象转为byte数组：" + Arrays.toString(class_ByteArr));
```



#### **2.1.6、byte数组转为对象**

```java
User byteArr_Class = mapper.readValue(class_ByteArr, User.class);
System.out.println("byte数组转为对象：" + byteArr_Class);
```



### 2.2、**导入依赖**

```xml
<!--https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-mapper-asl -->
<dependency>
  <groupId>org.codehaus.jackson</groupId>
  <artifactId>jackson-mapper-asl</artifactId>
  <version>1.9.13</version>
</dependency>
```

```xml
<!-- https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-core-asl -->
<dependency>
  <groupId>org.codehaus.jackson</groupId>
  <artifactId>jackson-core-asl</artifactId>
  <version>1.9.13</version>
</dependency>
```

### **2.3、具体使用方法**

#### **2.3.1、HTML中**

​	**使用了JQuery方式**，得到的返回值放在span标签中

```html
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
<script>
    function submit(){
        var username = $("#username").val();
        var password = $("#password").val();
        var user = {
            username: username,
            password: password
        };

        $.ajax({
            url:"SubmitServlet",
            // url:"/login",
            data:{"user":JSON.stringify(user),"username":username,"password":password},
            // data:JSON.stringify(user),
            cache:false,
            type:"POST",
            dataType:"json",
            // contentType: 'application/json;charset=utf-8',
            success: function (resultUser){
                $("#span1").text(resultUser.username);
                $("#span2").text(resultUser.password);
            }
        })

    }
</script>
```

#### **2.3.2、Servlet中**

1. ​	首先测试能否到达此类，输出"成功到达后台"
2. ​	接着开始类型转换
3. ​	最后把结果对象返回到前端

```java
@WebServlet("/SubmitServlet")
public class SubmitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       request.setCharacterEncoding("UTF-8");
       response.setContentType("application/json;charset=utf-8");

        System.out.println("成功到达后台");
        //System.out.println("username: "+request.getParameter("username")+"    "+"password: "+request.getParameter("password"));
        String user =  request.getParameter("user");

        ObjectMapper mapper = new ObjectMapper();
        User userBean = mapper.readValue(user,User.class);
        System.out.println("userBean:   "+userBean);
		//把userBean对象返回到前端页面的成功回调函数
        mapper.writeValue(response.getWriter(),userBean);

    }
}
```

![9UxnMae1df8IAlR](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803132323.png)

![nAJGpEmZYl4MD2d](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803132328.png)





# 三、JSONObject(阿里巴巴)

JSONObject只是一种数据结构，可以理解为JSON格式的数据结构（[key-value]() 结构），可以使用put方法给json对象添加元素。JSONObject可以很方便的转换成字符串，也可以很方便的把其他对象转换成JSONObject对象。

## **1、依赖fastjson**

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.75</version>
</dependency>
```

## **1.2、使用**

### 1.2.1、初始化

> JSONObject jsonObject = new JSONObject();
>
> 
>
> User user = new User("243582", "h2435823336");
> User user2 = new User("243582", "h2435823336");

### **1.2.2、对象转JSON**

​	**JSON.toJSON(user)**

```
System.out.println(JSON.toJSON(user));
```

### **1.2.3、JSON转对象**

​	**JSONObject.toJSONString(user)**

```
System.out.println(JSONObject.toJSONString(user));
```



# 3.2、JSONObject(谷歌)

## **1、依赖Gson**

