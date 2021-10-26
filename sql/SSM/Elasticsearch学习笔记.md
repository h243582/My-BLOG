# Elasticsearch学习笔记



## 一、Index、Type、Document

### **1、Index**

`index`：索引是文档(Document)的容器，是一类文档的集合。

**索引**这个词在 ElasticSearch 会有三种意思

**1)、索引(名词)**

类比传统的关系型数据库领域来说，`索引相当于SQL中的一个数据库(Database)`。索引由其名称(**必须为全小写字符**)进行标识。

**2)、索引(动词)**

`保存一个文档到索引(名词)的过程`。这非常类似于SQL语句中的 INSERT关键词。如果该文档已存在时那就相当于数据库的UPDATE。

**3)、倒排索引**

关系型数据库通过增加一个**B+树索**引到指定的列上，以便提升数据检索速度。索引ElasticSearch 使用了一个叫做 `倒排索引` 的结构来达到相同的目的。

### ***2、Type***

`Type` 可以理解成关系数据库中Table。

之前的版本中，索引和文档中间还有个类型的概念，每个索引下可以建立多个类型，文档存储时需要指定index和type。从6.0.0开始单个索引中只能有一个类型，

7.0.0以后将将不建议使用，8.0.0 以后完全不支持。

***\*弃用该概念的原因：\****

我们虽然可以通俗的去理解Index比作 SQL 的 Database，Type比作SQL的Table。但这并不准确，因为如果在SQL中,Table 之前相互独立，同名的字段在两个表中毫无关系。

但是在ES中，同一个Index 下不同的 Type 如果有同名的字段，他们会被 Luecence 当作同一个字段 ，并且他们的定义必须相同。所以我觉得Index现在更像一个表，

而Type字段并没有多少意义。目前Type已经被Deprecated，在7.0开始，一个索引只能建一个Type为`_doc`

### ***3、Document***

`Document` Index 里面单条的记录称为Document（文档）。**等同于关系型数据库表中的行**。

我们来看下一个文档的源数据

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTA5MDYxNy8yMDE5MDkvMTA5MDYxNy0yMDE5MDkwMjE5MzQwNDQyMy0xNzU1Mjc5NTkuanBn?x-oss-process=image/format,png)

`_index` 文档所属索引名称。

`_type` 文档所属类型名。

`_id` Doc的主键。在写入的时候，可以指定该Doc的ID值，如果不指定，则系统自动生成一个唯一的UUID值。

`_version` 文档的版本信息。Elasticsearch通过使用version来保证对文档的变更能以正确的顺序执行，避免乱序造成的数据丢失。

`_seq_no` 严格递增的顺序号，每个文档一个，Shard级别严格递增，保证后写入的Doc的`_seq_no`大于先写入的Doc的_seq_no。

`primary_term` primary_term也和`_seq_no`一样是一个整数，每当Primary Shard发生重新分配时，比如重启，Primary选举等，_primary_term会递增1

`found` 查询的ID正确那么ture, 如果 Id 不正确，就查不到数据，found字段就是false。

`_source` 文档的原始JSON数据。

 

## **二、集群，节点，分片及副本**

### ***1、集群***

ElasticSearch集群实际上是一个分布式系统，它需要具备两个特性：

1）`高可用性`

a）服务可用性：允许有节点停止服务；

b）数据可用性：部分节点丢失，不会丢失数据；

2）`可扩展性`

随着请求量的不断提升，数据量的不断增长，系统可以将数据分布到其他节点，实现水平扩展；

**一个集群中可以有一个或者多个节点**；

**集群健康值**

1. `green`：所有主要分片和复制分片都可用
2. `yellow`：所有主要分片可用，但不是所有复制分片都可用
3. `red`：不是所有的主要分片都可用

```
当集群状态为 red，它仍然正常提供服务，它会在现有存活分片中执行请求，我们需要尽快修复故障分片，防止查询数据的丢失；
```

### ***2、节点(Node)***

1）**节点是什么？**

a）节点是一个ElasticSearch的实例，其本质就是一个Java进程；

b）一台机器上可以运行多个ElasticSearch实例，但是建议在生产环境中一台机器上只运行一个ElasticSearch实例；

Node 是组成集群的一个单独的服务器，用于存储数据并提供集群的搜索和索引功能。与集群一样，节点也有一个唯一名字，默认在节点启动时会生成一个uuid作为节点名，

该名字也可以手动指定。单个集群可以由任意数量的节点组成。如果只启动了一个节点，则会形成一个单节点的集群。

### ***3、分片***

```
Primary Shard(主分片）
```

ES中的shard用来解决节点的容量上限问题,，通过主分片，可以将数据分布到集群内的所有节点之上。

它们之间关系

```
一个节点对应一个ES实例；



一个节点可以有多个index（索引）;



一个index可以有多个shard（分片）；



　一个分片是一个lucene index（此处的index是lucene自己的概念，与ES的index不是一回事）；
主分片数是在索引创建时指定，后续不允许修改，除非Reindex
```

一个索引中的数据保存在多个分片中(默认为一个)，相当于水平分表。一个分片便是一个Lucene 的实例，它本身就是一个完整的搜索引擎。我们的文档被存储和索引到分片内，

但是应用程序是直接与索引而不是与分片进行交互。

### Replica Shard（副本）

副本有两个重要作用：

1、**服务高可用**：由于数据只有一份,如果一个node挂了,那存在上面的数据就都丢了,有了replicas,只要不是存储这条数据的node全挂了,数据就不会丢。因此分片副本不会与

主分片分配到同一个节点；

2、**扩展性能**：通过在所有replicas上并行搜索提高搜索性能.由于replicas上的数据是近实时的(near realtime),因此所有replicas都能提供搜索功能,通过设置合理的replicas

数量可以极高的提高搜索吞吐量

```
分片的设定
```

对于生产环境中分片的设定，需要提前做好容量规划，因为主分片数是在索引创建时预先设定的，后续无法修改。

**分片数设置过小**

导致后续无法增加节点进行水平扩展。

导致分片的数据量太大，数据在重新分配时耗时；

**分片数设置过大**

影响搜索结果的相关性打分，影响统计结果的准确性；

单个节点上过多的分片，会导致资源浪费，同时也会影响性能；



## 三， 基本语法

这四个字段是自带的，id是自动生成的

![image-20210908100446495](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109081004557.png)

### 新建索引

`地址/索引`

`PUT`  http://localhost:9200/wanghuimin

### 给索引添加类型和数据

`地址/索引/类型`

`POST`  http://localhost:9200/wanghuimin/wuhu

```json
{
    "title":"小米电视4A",
    "images":"http://image.leyou.com/12479122.jpg",
    "price":3899.00
}
```

### 修改索引的类型数据

如果数据里面本来就有这个字段名，就修改，没有这个字段名，就添加

`POST`   http://localhost:9200/articleindex/article/AXvAYz7TTd2mad3F9TOA

```json
{
    "title":"小米电视4A",
    "images":"http://image.leyou.com/12479122.jpg",
    "price":3899.00
}
```

![image-20210908102331504](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109081023582.png)

### 查询：

```json
GET  /索引库名/_search
{
    "query":{
        "查询类型":{
            "查询条件":"查询条件值"
        }
    }
}
```



这里的query代表一个查询对象，里面可以有不同的查询属性

查询类型：
例如：match_all， match，term ， range 等等
查询条件：查询条件会根据类型的不同，写法也有差异，后面详细讲解

#### 查询所有（ma

http://localhost:9200/articleindex/_search      GET

==**//记得参数一定要选择Body的raw，内容为空，不然会报错**==

![image-20210908095627532](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109080956051.png)

took：查询花费时间，单位是毫秒
time_out：是否超时
_shards：分片信息
hits：搜索结果总览对象
total：搜索到的总条数
max_score：所有结果中文档得分的最高分
hits：搜索结果的文档对象数组，每个元素是一条搜索到的文档信息
_index：索引库
_type：文档类型
_id：文档id
_score：文档得分
_source：文档的源数据

#### 根据ID查询

`地址/索引/类型/id名`

http://localhost:9200/articleindex/article/AXvAYz7TTd2mad3F9TOA

![image-20210908100141999](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109081001086.png)

#### 根据其他查询

记得把参数设为none

GET http://localhost:9200/articleindex/article2/_search?q=_id:AXvAZtTUTd2mad3F9TOC

### 模糊查询

貌似自带的属性  模糊查询不了

GET   http://localhost:9200/articleindex/article/_search?q=content:*王*

### 删除

#### 根据id删除

DELETE http://localhost:9200/articleindex/article2/AXvAaI74Td2mad3F9TOG

根据其他删除



#### 3.1.4 词条匹配(term)

term 查询被用于精确值 匹配，这些精确值可能是数字、时间、布尔或者那些未分词的字符串

GET /smallmartial/_search
{
    "query":{
        "term":{
            "price":2699.00
        }
    }
}

结果：

{
  "took": 15,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 1,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "vV5xK2oBwnpoSx5Aac1y",
        "_score": 1,
        "_source": {
          "title": "小米手机",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 2699
        }
      }
    ]
  }
}


#### 3.1.5 多词条精确匹配(terms)

terms 查询和 term 查询一样，但它允许你指定多值进行匹配。如果这个字段包含了指定值中的任何一个值，那么这个文档满足条件：

GET /smallmartial/_search
{
    "query":{
        "terms":{
            "price":[2699.00,2899.00,3899.00]
        }
    }
}

结果：

{
  "took": 14,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 3,
    "max_score": 1,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "2",
        "_score": 1,
        "_source": {
          "title": "大米手机",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 2899
        }
      },
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "vV5xK2oBwnpoSx5Aac1y",
        "_score": 1,
        "_source": {
          "title": "小米手机",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 2699
        }
      },
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "3",
        "_score": 1,
        "_source": {
          "title": "小米电视4A",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 3899
        }
      }
    ]
  }
}

### 3.2.结果过滤

默认情况下，elasticsearch在搜索的结果中，会把文档中保存在_source的所有字段都返回。

如果我们只想获取其中的部分字段，我们可以添加_source的过滤

#### 3.2.1.直接指定字段

示例：

GET /smallmartial/_search
{
  "_source": ["title","price"],
  "query": {
    "term": {
      "price": 2699
    }
  }
}

返回的结果：

{
  "took": 13,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 1,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "vV5xK2oBwnpoSx5Aac1y",
        "_score": 1,
        "_source": {
          "price": 2699,
          "title": "小米手机"
        }
      }
    ]
  }
}


#### 3.2.2.指定includes和excludes

我们也可以通过：

includes：来指定想要显示的字段
excludes：来指定不想要显示的字段
二者都是可选的。

示例：

GET /smallmartial/_search
{
  "_source": {
    "includes":["title","price"]
  },
  "query": {
    "term": {
      "price": 2699
    }
  }
}

与下面的结果将是一样的：

{
  "took": 4,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 1,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "vV5xK2oBwnpoSx5Aac1y",
        "_score": 1,
        "_source": {
          "price": 2699,
          "title": "小米手机"
        }
      }
    ]
  }
}



### 3.3 高级查询

#### 3.3.1 布尔组合（bool)

bool把各种其它查询通过must（与）、must_not（非）、should（或）的方式进行组合

GET /smallmartial/_search
{
    "query":{
        "bool":{
        	"must":     { "match": { "title": "大米" }},
        	"must_not": { "match": { "title":  "电视" }},
        	"should":   { "match": { "title": "手机" }}
        }
    }
}

结果：

{
  "took": 22,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 0.5753642,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "2",
        "_score": 0.5753642,
        "_source": {
          "title": "大米手机",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 2899
        }
      }
    ]
  }
}


#### 3.3.2 范围查询(range)

range 查询找出那些落在指定区间内的数字或者时间

GET /smallmartial/_search
{
    "query":{
        "range": {
            "price": {
                "gte":  1000.0,
                "lt":   2800.00
            }
    	}
    }
}

range查询允许以下字符：

操作符	说明
gt	大于
gte	大于等于
lt	小于
lte	小于等于
3.3.3 模糊查询(fuzzy)
我们新增一个商品：

POST /smallmartial/goods/4
{
    "title":"apple手机",
    "images":"http://image.leyou.com/12479122.jpg",
    "price":6899.00
}

fuzzy 查询是 term 查询的模糊等价。它允许用户搜索词条与实际词条的拼写出现偏差，但是偏差的编辑距离不得超过2：

GET /smallmartial/_search
{
  "query": {
    "fuzzy": {
      "title": "appla"
    }
  }
}

上面的查询，也能查询到apple手机

我们可以通过fuzziness来指定允许的编辑距离：

GET /smallmartial/_search
{
  "query": {
    "fuzzy": {
        "title": {
            "value":"appla",
            "fuzziness":1
        }
    }
  }
}

reslut

{
  "took": 37,
  "timed_out": false,
  "_shards": {
    "total": 3,
    "successful": 3,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 0.55451775,
    "hits": [
      {
        "_index": "smallmartial",
        "_type": "goods",
        "_id": "4",
        "_score": 0.55451775,
        "_source": {
          "title": "apple手机",
          "images": "http://image.leyou.com/12479122.jpg",
          "price": 6899
        }
      }
    ]
  }
}


### 3.4 过滤(filter)

条件查询中进行过滤

所有的查询都会影响到文档的评分及排名。如果我们需要在查询结果中进行过滤，并且不希望过滤条件影响评分，那么就不要把过滤条件作为查询条件来用。而是使用filter方式：

GET /smallamrtial/_search
{
    "query":{
        "bool":{
        	"must":{ "match": { "title": "小米手机" }},
        	"filter":{
                "range":{"price":{"gt":2000.00,"lt":3800.00}}
        	}
        }
    }
}

注意：filter中还可以再次进行bool组合条件过滤。

无查询条件，直接过滤

如果一次查询只有过滤，没有查询条件，不希望进行评分，我们可以使用constant_score取代只有 filter 语句的 bool 查询。在性能上是完全相同的，但对于提高查询简洁性和清晰度有很大帮助。

GET /smallamrtial/_search
{
    "query":{
        "constant_score":   {
            "filter": {
            	 "range":{"price":{"gt":2000.00,"lt":3000.00}}
            }
        }
}


### 3.5 排序

#### 3.4.1 单字段排序

sort 可以让我们按照不同的字段进行排序，并且通过order指定排序的方式

GET /smallmartial/_search
{
  "query": {
    "match": {
      "title": "小米手机"
    }
  },
  "sort": [
    {
      "price": {
        "order": "desc"
      }
    }
  ]
}


#### 3.4.2 多字段排序

假定我们想要结合使用 price和 _score（得分） 进行查询，并且匹配的结果首先按照价格排序，然后按照相关性得分排序：

GET /goods/_search
{
    "query":{
        "bool":{
        	"must":{ "match": { "title": "小米手机" }},
        	"filter":{
                "range":{"price":{"gt":200000,"lt":300000}}
        	}
        }
    },
    "sort": [
      { "price": { "order": "desc" }},
      { "_score": { "order": "desc" }}
    ]
}



# IK分词器

## IK分词器安装

包括了所有版本

https://github.com/medcl/elasticsearch-analysis-ik/releases?after=v6.4.3

##  IK分词器测试

IK提供了两个分词算法ik_smart 和 ik_max_word
其中 ik_smart 为最少切分，ik_max_word为最细粒度划分

### ik_smart 最少切分

`POST`  http://localhost:9200/_analyze

```json
{
    "analyzer":"ik_smart",
    "text":"我是你爱爸爸"

}
```

### `ik_max_word`最细粒度划分

`POST`  http://localhost:9200/_analyze

```json
{
    "analyzer":"ik_max_word",
    "text":"我是你爱爸爸"

}
```



# 四，聚合aggregations

聚合可以让我们极其方便的实现对数据的统计、分析。例如：

什么品牌的手机最受欢迎？
这些手机的平均价格、最高价格、最低价格？
这些手机每月的销售情况如何？
实现这些统计功能的比数据库的sql要方便的多，而且查询速度非常快，可以实现近实时搜索效果。

### 4.1 基本概念

Elasticsearch中的聚合，包含多种类型，最常用的两种，一个叫桶，一个叫度量：

桶（bucket）

桶的作用，是按照某种方式对数据进行分组，每一组数据在ES中称为一个桶，例如我们根据国籍对人划分，可以得到中国桶、英国桶，日本桶……或者我们按照年龄段对人进行划分：010,1020,2030,3040等。

Elasticsearch中提供的划分桶的方式有很多：

Date Histogram Aggregation：根据日期阶梯分组，例如给定阶梯为周，会自动每周分为一组
Histogram Aggregation：根据数值阶梯分组，与日期类似
Terms Aggregation：根据词条内容分组，词条内容完全匹配的为一组
Range Aggregation：数值和日期的范围分组，指定开始和结束，然后按段分组
……
综上所述，我们发现bucket aggregations 只负责对数据进行分组，并不进行计算，因此往往bucket中往往会嵌套另一种聚合：metrics aggregations即度量

度量（metrics）

分组完成以后，我们一般会对组中的数据进行聚合运算，例如求平均值、最大、最小、求和等，这些在ES中称为度量

比较常用的一些度量聚合方式：

Avg Aggregation：求平均值
Max Aggregation：求最大值
Min Aggregation：求最小值
Percentiles Aggregation：求百分比
Stats Aggregation：同时返回avg、max、min、sum、count等
Sum Aggregation：求和
Top hits Aggregation：求前几
Value Count Aggregation：求总数
……
为了测试聚合，我们先批量导入一些数据

创建索引：

PUT /cars
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  },
  "mappings": {
    "transactions": {
      "properties": {
        "color": {
          "type": "keyword"
        },
        "make": {
          "type": "keyword"
        }
      }
    }
  }
}

注意：在ES中，需要进行聚合、排序、过滤的字段其处理方式比较特殊，因此不能被分词。这里我们将color和make这两个文字类型的字段设置为keyword类型，这个类型不会被分词，将来就可以参与聚合

导入数据

POST /cars/transactions/_bulk
{ "index": {}}
{ "price" : 10000, "color" : "red", "make" : "honda", "sold" : "2014-10-28" }
{ "index": {}}
{ "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
{ "index": {}}
{ "price" : 30000, "color" : "green", "make" : "ford", "sold" : "2014-05-18" }
{ "index": {}}
{ "price" : 15000, "color" : "blue", "make" : "toyota", "sold" : "2014-07-02" }
{ "index": {}}
{ "price" : 12000, "color" : "green", "make" : "toyota", "sold" : "2014-08-19" }
{ "index": {}}
{ "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
{ "index": {}}
{ "price" : 80000, "color" : "red", "make" : "bmw", "sold" : "2014-01-01" }
{ "index": {}}
{ "price" : 25000, "color" : "blue", "make" : "ford", "sold" : "2014-02-12" }


### 4.2 聚合为桶

首先，我们按照 汽车的颜色color来划分桶

GET /cars/_search
{
    "size" : 0,
    "aggs" : { 
        "popular_colors" : { 
            "terms" : { 
              "field" : "color"
            }
        }
    }
}

size： 查询条数，这里设置为0，因为我们不关心搜索到的数据，只关心聚合结果，提高效率
aggs：声明这是一个聚合查询，是aggregations的缩写
popular_colors：给这次聚合起一个名字，任意。
terms：划分桶的方式，这里是根据词条划分
field：划分桶的字段
结果：

{
  "took": 40,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 8,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "popular_colors": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "red",
          "doc_count": 4
        },
        {
          "key": "blue",
          "doc_count": 2
        },
        {
          "key": "green",
          "doc_count": 2
        }
      ]
    }
  }
}

hits：查询结果为空，因为我们设置了size为0
aggregations：聚合的结果
popular_colors：我们定义的聚合名称
buckets：查找到的桶，每个不同的color字段值都会形成一个桶
key：这个桶对应的color字段的值
doc_count：这个桶中的文档数量
通过聚合的结果我们发现，目前红色的小车比较畅销！

### 4.3 桶内度量

前面的例子告诉我们每个桶里面的文档数量，这很有用。 但通常，我们的应用需要提供更复杂的文档度量。 例如，每种颜色汽车的平均价格是多少？

因此，我们需要告诉Elasticsearch使用哪个字段，使用何种度量方式进行运算，这些信息要嵌套在桶内，度量的运算会基于桶内的文档进行

现在，我们为刚刚的聚合结果添加 求价格平均值的度量：

GET /cars/_search
{
    "size" : 0,
    "aggs" : { 
        "popular_colors" : { 
            "terms" : { 
              "field" : "color"
            },
            "aggs":{
                "avg_price": { 
                   "avg": {
                      "field": "price" 
                   }
                }
            }
        }
    }
}

aggs：我们在上一个aggs(popular_colors)中添加新的aggs。可见度量也是一个聚合
avg_price：聚合的名称
avg：度量的类型，这里是求平均值
field：度量运算的字段
结果：


{
  "took": 35,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 8,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "popular_colors": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "red",
          "doc_count": 4,
          "avg_price": {
            "value": 32500
          }
        },
        {
          "key": "blue",
          "doc_count": 2,
          "avg_price": {
            "value": 20000
          }
        },
        {
          "key": "green",
          "doc_count": 2,
          "avg_price": {
            "value": 21000
          }
        }
      ]
    }
  }
}


可以看到每个桶中都有自己的avg_price字段，这是度量聚合的结果

### 4.4 桶内嵌套桶

刚刚的案例中，我们在桶内嵌套度量运算。事实上桶不仅可以嵌套运算， 还可以再嵌套其它桶。也就是说在每个分组中，再分更多组。

比如：我们想统计每种颜色的汽车中，分别属于哪个制造商，按照make字段再进行分桶

GET /cars/_search
{
    "size" : 0,
    "aggs" : { 
        "popular_colors" : { 
            "terms" : { 
              "field" : "color"
            },
            "aggs":{
                "avg_price": { 
                   "avg": {
                      "field": "price" 
                   }
                },
                "maker":{
                    "terms":{
                        "field":"make"
                    }
                }
            }
        }
    }
}

原来的color桶和avg计算我们不变
maker：在嵌套的aggs下新添一个桶，叫做maker
terms：桶的划分类型依然是词条
filed：这里根据make字段进行划分
部分结果：

...
{"aggregations": {
    "popular_colors": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "red",
          "doc_count": 4,
          "maker": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "honda",
                "doc_count": 3
              },
              {
                "key": "bmw",
                "doc_count": 1
              }
            ]
          },
          "avg_price": {
            "value": 32500
          }
        },
        {
          "key": "blue",
          "doc_count": 2,
          "maker": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 1
              },
              {
                "key": "toyota",
                "doc_count": 1
              }
            ]
          },
          "avg_price": {
            "value": 20000
          }
        },
      {
          "key": "green",
          "doc_count": 2,
          "maker": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 1
              },
              {
                "key": "toyota",
                "doc_count": 1
              }
            ]
          },
          "avg_price": {
            "value": 21000
          }
        }
      ]
    }
  }
}
...

我们可以看到，新的聚合maker被嵌套在原来每一个color的桶中。
每个颜色下面都根据 make字段进行了分组
我们能读取到的信息：
红色车共有4辆
红色车的平均售价是 $32，500 美元。
其中3辆是 Honda 本田制造，1辆是 BMW 宝马制造。
4.5.划分桶的其它方式
前面讲了，划分桶的方式有很多，例如：

Date Histogram Aggregation：根据日期阶梯分组，例如给定阶梯为周，会自动每周分为一组
Histogram Aggregation：根据数值阶梯分组，与日期类似
Terms Aggregation：根据词条内容分组，词条内容完全匹配的为一组
Range Aggregation：数值和日期的范围分组，指定开始和结束，然后按段分组
刚刚的案例中，我们采用的是Terms Aggregation，即根据词条划分桶。

接下来，我们再学习几个比较实用的：

4.5.1.阶梯分桶Histogram
原理：

histogram是把数值类型的字段，按照一定的阶梯大小进行分组。你需要指定一个阶梯值（interval）来划分阶梯大小。

举例：

比如你有价格字段，如果你设定interval的值为200，那么阶梯就会是这样的：

0，200，400，600，…

上面列出的是每个阶梯的key，也是区间的启点。

如果一件商品的价格是450，会落入哪个阶梯区间呢？计算公式如下：

bucket_key = Math.floor((value - offset) / interval) * interval + offset
1
value：就是当前数据的值，本例中是450

offset：起始偏移量，默认为0

interval：阶梯间隔，比如200

因此你得到的key = Math.floor((450 - 0) / 200) * 200 + 0 = 400

操作一下：

比如，我们对汽车的价格进行分组，指定间隔interval为5000：

GET /cars/_search
{
  "size":0,
  "aggs":{
    "price":{
      "histogram": {
        "field": "price",
        "interval": 5000
      }
    }
  }
}

结果：

{
  "took": 21,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 8,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "price": {
      "buckets": [
        {
          "key": 10000,
          "doc_count": 2
        },
        {
          "key": 15000,
          "doc_count": 1
        },
        {
          "key": 20000,
          "doc_count": 2
        },
        {
          "key": 25000,
          "doc_count": 1
        },
        {
          "key": 30000,
          "doc_count": 1
        },
        {
          "key": 35000,
          "doc_count": 0
        },
        {
          "key": 40000,
          "doc_count": 0
        },
        {
          "key": 45000,
          "doc_count": 0
        },
        {
          "key": 50000,
          "doc_count": 0
        },
        {
          "key": 55000,
          "doc_count": 0
        },
        {
          "key": 60000,
          "doc_count": 0
        },
        {
          "key": 65000,
          "doc_count": 0
        },
        {
          "key": 70000,
          "doc_count": 0
        },
        {
          "key": 75000,
          "doc_count": 0
        },
        {
          "key": 80000,
          "doc_count": 1
        }
      ]
    }
  }
}

你会发现，中间有大量的文档数量为0 的桶，看起来很丑。

我们可以增加一个参数min_doc_count为1，来约束最少文档数量为1，这样文档数量为0的桶会被过滤

示例：

GET /cars/_search
{
  "size":0,
  "aggs":{
    "price":{
      "histogram": {
        "field": "price",
        "interval": 5000,
        "min_doc_count": 1
      }
    }
  }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
结果：

{
  "took": 15,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 8,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "price": {
      "buckets": [
        {
          "key": 10000,
          "doc_count": 2
        },
        {
          "key": 15000,
          "doc_count": 1
        },
        {
          "key": 20000,
          "doc_count": 2
        },
        {
          "key": 25000,
          "doc_count": 1
        },
        {
          "key": 30000,
          "doc_count": 1
        },
        {
          "key": 80000,
          "doc_count": 1
        }
      ]
    }
  }
}

完美，！

如果你用kibana将结果变为柱形图，会更好看：



# 搜索微服务java开发

### 5.1 需求分析

### 5.2 代码编写

#### 5.2.1 模块搭建

（1）创建模块tensquare_search ，pom.xml引入依赖

~~~xml
 		<dependency>
            <groupId>com.tensquare</groupId>
            <artifactId>tensquare_common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-elasticsearch</artifactId>
        </dependency>
~~~

（2）application.yml

~~~yaml
server:
  port: 9007
spring:
  application:
    name: tensquare-search
  data:
    elasticsearch:
      cluster-nodes: 127.0.0.1:9300
      cluster-name: my-application


~~~


（3）创建包com.tensquare.search ，包下创建启动类

~~~java
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
    
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
~~~



#### 5.2.2 添加文章

（1）创建实体类
创建com.tensquare.search.pojo包，包下建立类

~~~java
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 文章实体类
 * Created by progr on 2019/3/12.
 */
@Document(indexName = "tensquare_article",type = "article")
public class Article implements Serializable {

    @Id
    private String id;
    //是否索引，就是看该域是否能被搜索
    //是否分词，就表示搜索的时候是整体匹配还是单词匹配
    //是否存储，就是是否在页面上显示
    @Field(index = true,analyzer="ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;//标题

    @Field(index = true,analyzer="ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;//文章正文

    private String state;//审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
~~~

（2）创建数据访问接口
创建com.tensquare.search.dao包，包下建立接口

~~~java
/**
* 文章数据访问层接口
*/
public interface ArticleSearchDao extends
	ElasticsearchRepository<Article,String> {
}
~~~


（3）创建业务逻辑类
创建com.tensquare.search.service包，包下建立类

~~~java
@Service
public class ArticleSearchService {
@Autowired
private ArticleSearchDao articleSearchDao;
  /**
  * 增加文章
  * @param article
  */
  public void add(Article article){
  	articleSearchDao.save(article);
  }
}
~~~

（4）创建控制器类
创建com.tensquare.search.controller包，包下建立类

~~~java
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK,"操作成功");
    }
 }
~~~

#### 5.2.3 文章搜索

（1）ArticleSearchRepository新增方法定义

~~~java
/**
* 检索
* @param
* @return
*/
public Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);
~~~

（2）ArticleSearchService新增方法

~~~java
public Page<Article> findByTitleLike(String keywords, int page, int size){
  PageRequest pageRequest = PageRequest.of(page‐1, size);
  return articleSearchdao.findByTitleLikeOrContentLike(keywords,keywords,pageRequest);
}
~~~

（3）ArticleSearchController方法

~~~java
 @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> articles = articleSearchService.findbyTitleLike(keywords, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(articles.getTotalElements(),articles.getContent()));
    }
~~~



# ES与MySQL数据同步

### 6.1 Logstash

#### 6.1.1什么是Logstash

Logstash是一款轻量级的日志搜集处理框架，可以方便的把分散的、多样化的日志搜集起来，并进行自定义的处理，然后传输到指定的位置，比如某个服务器或者文件。

#### 6.1.2 Logstash安装与测试

解压，进入bin目录

~~~
logstash -e 'input { stdin { } } output { stdout {} }'
~~~

控制台输入字符，随后就有日志输出

![](F:/%25E5%25AD%25A6%25E4%25B9%25A0%25E6%259C%2580%25E9%2587%258D%25E8%25A6%2581/%25E9%25A1%25B9%25E7%259B%25AE/1024%25E7%25A4%25BE%25E5%258C%25BA/%25E5%2590%258E%25E5%258F%25B0md%25E6%2596%2587%25E4%25BB%25B6/img/4.10.png)

stdin，表示输入流，指从键盘输入
stdout，表示输出流，指从显示器输出
命令行参数:
-e 执行
--config 或 -f 配置文件，后跟参数类型可以是一个字符串的配置或全路径文件名或全路径
路径(如：/etc/logstash.d/，logstash会自动读取/etc/logstash.d/目录下所有*.conf 的文
本文件，然后在自己内存里拼接成一个完整的大配置文件再去执行)

### 6.2 MySQL数据导入Elasticsearch

（1）在logstash-5.6.8安装目录下创建文件夹mysqletc （名称随意）
（2）文件夹下创建mysql.conf （名称随意） ，内容如下：

~~~
input {
  jdbc {
    # mysql jdbc connection string to our backup databse 后面的test对应mysql中的test数据库
    jdbc_connection_string => "jdbc:mysql://127.0.0.1:3306/tensquare_article?characterEncoding=UTF8"
    # the user we wish to excute our statement as
    jdbc_user => "root"
    jdbc_password => "123456"
    # the path to our downloaded jdbc driver
    jdbc_driver_library => "D:\java_exe\elasticsearch-6.2.4\logstash-6.2.1\mysqletc\mysql-connector-java-8.0.23.jar"
    # the name of the driver class for mysql
    jdbc_driver_class => "com.mysql.jdbc.Driver"
    jdbc_paging_enabled => "true"
    jdbc_page_size => "50000"
    #以下对应着要执行的sql的绝对路径。
    statement => "select id,title,content from tb_article"
    #定时字段 各字段含义（由左至右）分、时、天、月、年，全部为*默认含义为 每分钟都更新
    schedule => "* * * * *"
  }
}
output {
  elasticsearch {
    #ESIP地址与端口
    hosts => "localhost:9200"
    #ES索引名称（自己定义的）
    index => "tensquare_article"
    #自增ID编号
    document_id => "%{id}"
    document_type => "article"
  }
  stdout {
    #以JSON格式输出
    codec => json_lines
  }
}
~~~

（3）将mysql驱动包mysql-connector-java-5.1.46.jar拷贝至D:/logstash-
5.6.8/mysqletc/ 下 。D:/logstash-5.6.8是你的安装目录
（4）在bin目录运行cmd，命令行下执行

~~~
logstash.bat -f ../mysqletc/mysql.conf
~~~

观察控制台输出，每间隔1分钟就执行一次sql查询。

![image-20210908155238651](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109081553238.png)

再次刷新elasticsearch-head的数据显示，看是否也更新了数据。





