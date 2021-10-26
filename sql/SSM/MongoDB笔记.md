# 一、MongoDB简介

### 1.1 概念

MongoDB是==为大数据而生==的

吐槽和评论两项功能存在以下特点：
（1）**数据量大**
（2）**写入操作频繁**
（3）**价值较低**
对于这样的数据，我们更适合使用MongoDB来实现数据的存储

## 1.2 什么是MongoDB

MongoDB 是一个跨平台的，面向文档的数据库，是当前 NoSQL 数据库产品中最热门
的一种。它介于关系数据库和非关系数据库之间，是非关系数据库当中功能最丰富，最像关系数据库的产品。
它支持的数据结构非常松散，是类似 JSON 的 BSON 格式，因此可以存
储比较复杂的数据类型。
MongoDB 的官方网站地址是：http://www.mongodb.org/

## 1.3 `MongoDB`特点

`MongoDB` 最大的特点是他==支持的查询语言非常强大==，其语法有点**类似于面向对象**
**的查询语言**，几乎可以实现类似关系数据库单表查询的绝大部分功能，而且还支持对数据建立索引。它是一个面向集合的,模式自由的文档型数据库。

具体特点总结如下：
（1）面向集合存储，易于存储对象类型的数据
（2）模式自由
（3）支持动态查询
（4）支持完全索引，包含内部对象
（5）支持复制和故障恢复
（6）使用高效的二进制数据存储，包括大型对象（如视频等）
（7）自动处理碎片，以支持云计算层次的扩展性
（8）支持 **大部分开发语言**
（9） 文件存储格式为 BSON（一种 JSON 的扩展）


### 1.4 MongoDB体系结构

向用户的，用户使用 MongoDB 开发应用程序使用的就是逻辑结构。
（1）MongoDB 的文档（document），相当于关系数据库中的一行记录。
（2）多个文档组成一个集合（collection），相当于关系数据库的表。
（3）多个集合（collection），逻辑上组织在一起，就是数据库（database）。
（4）一个 MongoDB 实例支持多个数据库（database）。

下表是MongoDB与MySQL数据库逻辑结构概念的对比

![image-20210906151343412](C:/Users/24358/AppData/Roaming/Typora/typora-user-images/image-20210906151343412.png)

# 二、数据类型

基本数据类型

### **null**：

用于表示空值或者不存在的字段，{“x”:null}

### **布尔型**：

布尔类型有两个值true和false，{“x”:true}

### **数值**：

shell默认使用64为浮点型数值。{“x”:3.14}或{“x”:3}。

对于整型值，可以使用`NumberInt`（4字节符号整数）或`NumberLong`（8字节符号整数），{“x”:NumberInt(“3”)}{“x”:NumberLong(“3”)}

### **字符串**：

UTF-8字符串都可以表示为字符串类型的数据，{“x”:“呵呵”}

### **日期**：

日期被存储为自新纪元依赖经过的**毫秒数**，**不存储时区**，{“x”:new Date()}

### **正则表达式**：

查询时，使用正则表达式作为限定条件，语法与JavaScript的正则表达式相
同，{“x”:/[abc]/}

### **数组**：

数据列表或数据集可以表示为数组，{“x”： [“a“，“b”,”c”]}

### **内嵌文档**：

文档可以嵌套其他文档，被嵌套的文档作为值来处理，{“x”:{“y”:3 }}

### **对象Id**：

对象id是一个12字节的字符串，是文档的唯一标识，{“x”: objectId() }

### **二进制数据**：

二进制数据是一个任意字节的字符串。它不能直接在shell中使用。如果要
将非utf-字符保存到数据库中，二进制数据是唯一的方式。
代码：查询和文档中可以包括任何JavaScript代码，{“x”:function(){/…/}}

# 三、下载MongoDB

### 2.1 MongoDB安装与启动

下载
下载地址：https://www.mongodb.com/try/download/community
选择版本，系统类型，文件格式后，点击Download

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533819.png)

安装
安装步骤，我的已经安装完成了，文字描述一下

1. 点击next

2. 选择custom

3. 选择安装目录

   图中有以下两种安装类型：

   Complete。此类型将安装所有程序功能，需占用较多的磁盘空间，建议大多数用户使用。

   Custom。此类型允许用户自行选择要安装的程序功能及安装位置，建议高级用户使用。

   ![1607593559975_MongoDB安装4.png](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533800.png)

4. 配置名称、数据存放文件夹、日志存放文件

   ![1607593636105_MongoDB安装5.png](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533990.png)

5. 点击next ，取消选择Install MongoDB Compass

   MongoDB Compass是MongoDB数据库的GUI管理系统，默认会选择安装，但是安装速度非常慢。


   这里取消勾选“Install MongoDB Compass”复选框，单击【Next】按钮进入准备安装MongoDB数据库的界面，如图7所示。

   ![1607593670574_MongoDB安装6.png](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533244.png)

6. 点击install安装

7. 安装完成后需要重启

   

   

配置
在data目录下添加db文件夹

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533956.png)

配置环境变量

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533026.png)

配置存储路径
mongod --dbpath 自己的存储路径

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533316.png)

输入mongod确认安装成功

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533118.png)

浏览器也可以测试：http://127.0.0.1:27017

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109061533342.png)

# 四、 常用命令

## 1 选择和创建数据库

选择和创建数据库的语法格式：

~~~
use 数据库名称
~~~

如果数据库不存在则自动创建
以下语句创建spit数据库

~~~
use spitdb
~~~

#### 2.2.2 插入与查询文档

插入文档的语法格式：

~~~
db.集合名称.insert(数据);
~~~

我们这里可以插入以下测试数据：

~~~
db.spit.insert({content:"听说1024课程很给力呀",userid:"1011",nickname:"小雅",visits:NumberInt(902)})
~~~

查询集合的语法格式：

~~~
db.集合名称.find()
~~~

如果我们要查询spit集合的所有文档，我们输入以下命令

~~~
db.spit.find()
~~~

这里你会发现每条文档会有一个叫_id的字段，这个相当于我们原来关系数据库中表的主
键，当你在插入文档记录时没有指定该字段，MongoDB会自动创建，其类型是ObjectID
类型。如果我们在插入文档记录时指定该字段也可以，其类型可以是ObjectID类型，也
可以是MongoDB支持的任意类型。
输入以下测试语句:

~~~java
db.spit.insert({_id:"1",content:"我还是没有想明白到底为啥出错",userid:"1012",nickname:"小明",visits:NumberInt(2020)});
db.spit.insert({_id:"2",content:"加班到半夜",userid:"1013",nickname:"凯撒",visits:NumberInt(1023)});
db.spit.insert({_id:"3",content:"手机流量超了咋办？",userid:"1013",nickname:"凯撒",visits:NumberInt(111)});
db.spit.insert({_id:"4",content:"坚持就是胜利",userid:"1014",nickname:"诺诺",visits:NumberInt(1223)});
~~~



如果我想按一定条件来查询，比如我想查询userid为1013的记录，怎么办？很简单！只
要在find()中添加参数即可，参数也是json格式，如下：

~~~java
db.spit.find({userid:'1013'})
~~~

如果你只需要返回符合条件的第一条数据，我们可以使用findOne命令来实现

~~~
db.spit.findOne({userid:'1013'})
~~~

如果你想返回**指定条数的记录**，可以在find方法后调用limit来返回结果，例如：

~~~java
db.spit.find().limit(3)
~~~



#### 2.2.3 修改与删除文档

修改文档的语法结构：

~~~java
db.集合名称.update(条件,修改后的数据)
~~~

如果我们想修改_id为1的记录，浏览量为1000，输入以下语句：

~~~
db.spit.update({"_id":"1"},{"visits":NumberInt(1000)})
~~~

执行后，我们会发现，这条文档除了visits字段其它字段都不见了，为了解决这个问题，
我们需要使用修改器==**$set**==来实现，命令如下：

~~~
db.spit.update({"_id":"1"},{$set:{"visits":NumberInt(12131)}})
~~~

这样就OK啦。
删除文档的语法结构：

~~~
db.集合名称.remove(条件)
~~~

以下语句可以将数据全部删除，请慎用

~~~
db.spit.remove({})
~~~

如果删除visits=1000的记录，输入以下语句

~~~
db.spit.remove({visits:1000})
~~~

#### 2.2.4 统计条数

统计记录条件使用count()方法。以下语句统计spit集合的记录数

~~~
db.spit.count()
~~~

如果按条件统计 ，例如：统计userid为1013的记录条数

==**加不加引号都可以**==

~~~
db.spit.count({userid:"1013"})
db.spit.count({"userid":"1013"}) 
~~~

#### 2.2.5 模糊查询

MongoDB的模糊查询是通过正则表达式的方式实现的。格式为：

~~~
/模糊查询字符串/
~~~

例如，我要查询吐槽内容包含“流量”的所有文档，代码如下：

~~~
db.spit.find({content:/流量/})
~~~

`/^加班/`如果要查询吐槽内容中**以“加班”开头**的，代码如下：

~~~
db.spit.find({content:/^加班/})
~~~

#### 2.2.6 大于 小于 不等于

<, <=, >, >= 这个操作符也是很常用的，格式如下:

~~~java
db.集合名称.find({ "field" : { $gt: value }}) // 大于: field > value
db.集合名称.find({ "field" : { $lt: value }}) // 小于: field < value
db.集合名称.find({ "field" : { $gte: value }}) // 大于等于: field >= value
db.集合名称.find({ "field" : { $lte: value }}) // 小于等于: field <= value
db.集合名称.find({ "field" : { $ne: value }}) // 不等于: field != value
~~~

示例：查询吐槽浏览量大于1000的记录

~~~java
db.spit.find({visits:{$gt:1000}})
~~~

#### 2.2.7 包含与不包含

`$in`**包含**
示例：查询吐槽集合中userid字段包含1013和1014的文档

~~~java
db.spit.find({userid:{$in:["1013","1014"]}})
~~~

`**$nin`**不包含**
示例：查询吐槽集合中userid字段不包含**1013和1014的文档

~~~java
db.spit.find({userid:{$nin:["1013","1014"]}})
~~~

#### 2.2.8 条件连接

我们如果需要查询同时满足两个以上条件，需要使用$and操作符将条件进行关联。（相
当于SQL的and）
格式为：

~~~java
$and:[ { },{ },{ } ]
~~~

示例：查询吐槽集合中visits大于等于1000 并且小于2000的文档

~~~java
db.spit.find({$and:[ {visits:{$gte:1000}} ,{visits:{$lt:2000} }]})
db.spit.find({ $and:[{"userid":"1013"},{"visits":1023}] })
~~~

如果两个以上条件之间是或者的关系，我们使用 操作符进行关联，与前面and的使用
方式相同
格式为：

~~~
$or:[ { },{ },{ } ]
~~~

示例：查询吐槽集合中userid为1013，或者浏览量小于2000的文档记录

~~~java
db.spit.find({$or:[ {userid:"1013"} ,{visits:{$lt:2000} }]})
~~~

#### 2.2.9 列值增长`$inc`

如果我们想实现对某列值在原有值的基础上进行增加或减少，可以使用$inc运算符来实现

~~~java
db.spit.update({_id:"2"},{$inc:{visits:NumberInt(1)}} )
~~~

## 3 Java操作MongoDB

### 3.1 mongodb-driver

mongodb-driver是mongo官方推出的java连接mongoDB的驱动包，相当于JDBC驱动。
我们通过一个入门的案例来了解mongodb-driver的基本使用

#### 3.1.1 查询全部记录

（1）创建工程 mongoDemo, 引入依赖

~~~xml
<dependencies>
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver</artifactId>
            <version>3.6.3</version>
        </dependency>
    </dependencies>
~~~

（2）创建测试类

~~~java
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by progr on 2019/3/10.
 */
public class TestConn {
    public static void main(String[] args) {

        MongoClient client = new MongoClient("127.0.0.1");

        MongoDatabase spitdb = client.getDatabase("spit");
        //得到集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        FindIterable<Document> documents = spit.find();

        for (Document d:documents) {
            System.out.println("内容:"+d.getString("content"));
            System.out.println("用户id"+d.getString("userid"));
            System.out.println("浏览量："+d.getInteger("visits"));

        }
        //关闭连接
        client.close();

    }
}
~~~



#### 3.1.2 条件查询

BasicDBObject对象：表示一个具体的记录，BasicDBObject实现了DBObject，是key￾value的数据结构，用起来和HashMap是基本一致的。
（1）查询userid为1013的记录

​        `BasicDBObject bson = new BasicDBObject("userid", "1013");`

​        `FindIterable<Document> documents = spit.find(bson);`



（2）查询浏览量大于1000的记录

`BasicDBObject bson=new BasicDBObject("visits",new BasicDBObject("$gt",1000) );`

(3) 查询浏览量大于200小于300的记录

```java
BasicDBObject b1 = new BasicDBObject("visits", new BasicDBObject("$gt", 200));
BasicDBObject b2 = new BasicDBObject("visits", new BasicDBObject("$lt", 3000));
BasicDBObject[] basicDBObjects = new BasicDBObject[2];
basicDBObjects[0] = b1;
basicDBObjects[1] = b2;
BasicDBObject aa = new BasicDBObject("$and", basicDBObjects);
FindIterable<Document> documents = spit.find(aa);
```



#### 3.1.3 插入数据

```java
Document document = new Document();
document.put("content","我要吐槽");
document.put("userid","1006");
document.put("visits",500);

spit.insertOne(document);
```



# 五、连接SpringBoot

### 5.1 表结构分析

![](../../%25E5%25AD%25A6%25E4%25B9%25A0%25E6%259C%2580%25E9%2587%258D%25E8%25A6%2581/%25E9%25A1%25B9%25E7%259B%25AE/1024%25E7%25A4%25BE%25E5%258C%25BA/%25E5%2590%258E%25E5%258F%25B0md%25E6%2596%2587%25E4%25BB%25B6/img/3.5.png)

### 

#### 

#### 5.2.1 新增评论

（1）修改tensquare_article工程的pom.xml

~~~xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
~~~

（2）修改application.yml ,在spring节点下新增配置

~~~yaml
data:
    mongodb:
      host: 127.0.0.1
      database: spit
~~~



（3）创建实体类

~~~java
/**
* 文章评论（mongoDB）
* @author Administrator
*
*/
public class Comment implements Serializable{
  @Id
  private String _id;
  private String articleid;
  private String content;
  private String userid;
  private String parentid;
  private Date publishdate;
  //getter and setter....
}
~~~

（4）创建数据访问接口

~~~java
/**
* 评论Dao
* @author Administrator
*
*/
public interface CommentDao extends MongoRepository<Comment, String> {
}
~~~

（5）创建业务逻辑类

~~~java
@Service
public class CommentService {
  @Autowired
  private CommentDao commentDao;
  @Autowired
  private IdWorker idWorker;

  public void add(Comment comment){
    comment.setId( idWorker.nextId()+"" );
    commentDao.save(comment);
  }
}
~~~



（6）创建控制器类

~~~java
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;
    
  @RequestMapping(method= RequestMethod.POST)
  public Result save(@RequestBody Comment comment){
    commentService.add(comment);
    return new Result(true, StatusCode.OK, "提交成功 ");
  }
}
~~~



