

# JSP简介

## 1、三大指令

### （1）page指令

page指令用户定义JSP页面中的全局属性

```jsp
<%@ page attr1=”value1” atrr2=”value2”.......%>
```

### （2）include指令

```jsp
<%@include file=”other.jsp” %>
```

### （3）taglib指令

taglib指令允许用户使用标签库自定义新的标签，

```jsp
<% taglib uri="taglibURI" prefix="tabPrefix"%>
```



## 2、四大作用域

application所有的应用，session当前会话，page当前页，request当前请求

## 3、九大内置对象

### 输入输出

- out：向客户端、浏览器输出数据
- request：封装了来自客户端、浏览器的各种信息
- response：封装了服务器的响应信息

### 通信控制

- pageContext：提供了对jsp页面所有对象以及命名空间的访问。
- session：用来保存会话信息。也就是说，可以实现在同一用户的不同请求之间共享数
- application：代表了当前应用程序的上下文。可以在不同的用户之间共享信息。

### Servlet对象

- page：指向了当前jsp程序本身。
- config：封装了应用程序的配置信息。

### 错误处理

- Exception：封装了jsp程序执行过程中发生的异常和错误信息。









# jsp

## 1、导包

```html
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

## 2、if

### 低级版

```jsp
<%int day = 1;%>
<% if (day == 1 || day == 7) { %>
      <p>今天是周末</p>
<% } else { %>
      <p>今天不是周末</p>
  <% } %>
```

### 高级版c:if 

```jsp
<c:if test="${salary > 2000}">
   <p>我的工资为: <c:out value="${salary}"/><p>
</c:if>
```

| 属性  | 描述                   | 是否必要 | 默认值 |
| :---- | :--------------------- | :------- | :----- |
| test  | 条件                   | 是       | 无     |
| var   | 用于存储条件结果的变量 | 否       | 无     |
| scope | var属性的作用域        | 否       | page   |

## 3、out

### 低级版

```jsp
<%= 我是你爸爸 %>
```



### 高级版c:out

```jsp
<c:out value="${salary}"/>
<c:out value="&lt要显示的数据对象（使用转义字符）&gt" escapeXml="false" default="默认值"/>

```



| **属性**  | **描述**            | **是否必要** | **默认值**   |
| :-------- | :------------------ | :----------- | :----------- |
| value     | 要输出的内容        | 是           | 无           |
| default   | 输出的默认值        | 否           | 主体中的内容 |
| escapeXml | 是否忽略XML特殊字符 | 否           | true         |

## 4、set设置值

```jsp
<c:set var="salary" scope="session" value="${2000*2}"/>
<c:set var="salary" scope="session" value="5000"/>

```

| **属性** | **描述**               | **是否必要** | **默认值** |
| :------- | :--------------------- | :----------- | :--------- |
| value    | 要存储的值             | 否           | 主体的内容 |
| target   | 要修改的属性所属的对象 | 否           | 无         |
| property | 要修改的属性           | 否           | 无         |
| var      | 存储信息的变量         | 否           | 无         |
| scope    | var属性的作用域        | 否           | Page       |

## 5、switch

```jsp
<c:choose>
    <c:when test="${salary <= 0}">
        太惨了。
    </c:when>
    <c:when test="${salary > 1000}">
        不错的薪水，还能生活。
    </c:when>
    <c:otherwise>
        什么都没有。
    </c:otherwise>
</c:choose>
```

## 6、import

```jsp
<c:import var="data" url="http://www.runoob.com"/>
```



## 7、forEach和forTokens

![image-20210721210947343](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210721210947.png)

### forEach

```jsp
<c:forEach var="i" begin="1" end="5">
   Item <c:out value="${i}"/><p>
</c:forEach>
```

![image-20210721210523523](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210721210523.png)

![image-20210721210659665](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210721210659.png)

### forTokens

```jsp
<c:forTokens items="google,runoob,taobao" delims="," var="name">
   <c:out value="${name}"/><p>
</c:forTokens>
```

![image-20210721210537780](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210721210537.png)

## 8、日期formatDate

```jsp
<c:set var="now" value="<%=new java.util.Date()%>" />

<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>
<fmt:formatDate type="both"  value="${now}" />

```

![image-20210722085036670](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210722085043.png)



![](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108231945081.png)

