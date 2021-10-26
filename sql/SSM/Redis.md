# 一、NoSQL

## 1、什么是NoSQL

NoSQL = Not Only SQL（不仅仅是SQL)，泛指 **非关系型数据库**

**关系型数据库**：列 + 行，同一个表下数据的结构是一样的。

**非关系型数据库**：数据存储没有固定的格式，并且可以进行横向扩展。

## 2、Nosql特点

1. 方便扩展（数据之间没有关系，很好扩展！）
2. 大数据量高性能（Redis一秒可以写8万次，读11万次，NoSQL的缓存记录级，是一种细粒度的缓存，性能会比较高！）
3. 数据类型是多样型的！（不需要事先设计数据库，随取随用）
4. 传统的 RDBMS 和 NoSQL

>**传统的 RDBMS(关系型数据库)**
>
>- 结构化组织
>- SQL
>- 数据和关系都存在单独的表中 row col
>- 操作，数据定义语言
>- 严格的一致性
>- 基础的事务

>**Nosql**
>
>- 不仅仅是数据
>- 没有固定的查询语言
>- 键值对存储，列存储，文档存储，图形数据库（社交关系）
>- 最终一致性
>- CAP定理和BASE
>- 高性能，高可用，高扩展

## 3、Nosql的四大分类

### 3.1、KV键值对

新浪：Redis
美团：Redis + Tair
阿里、百度：Redis + Memcache
文档型数据库（bson数据格式）：

### 3.2、MongoDB(掌握)

基于分布式文件存储的数据库。C++编写，用于处理大量文档。
MongoDB是RDBMS和NoSQL的中间产品。MongoDB是非关系型数据库中功能最丰富的，NoSQL中最像关系型数据库的数据库。
ConthDB

### 3.3、列存储数据库

HBase(大数据必学)
分布式文件系统

### 3.4、图关系数据库

用于广告推荐，社交网络

Neo4j、InfoGrid

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210729162205.png" alt="image-20210729162205654"  />

# 二、Redis入门、安装

## 1、概述

### 1.1、定义

Redis（Remote Dictionary Server )，即**远程字典服务**

​		是一个开源的使用ANSIC语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。

​		与memcached一样，为了保证效率，数据都是缓存在内存中。区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

### 1.2、作用

1. 内存存储、持久化，内存是断电即失的，所以需要持久化（RDB、AOF）
2. 高效率、用于高速缓冲
3. 发布订阅系统
4. 地图信息分析
5. 计时器、计数器(eg：浏览量)

### 1.3、特性

1. 多样的数据类型
2. 持久化
3. 集群
4. 事务

## 2、windows下安装

### windows下载安装redis的说明：

1. 由于日常开发中有时需要使用redis，但是开虚拟机或配置redis又比较麻烦并且耗费电脑内存（针对电脑内存不是很大情况，当然32G的大佬我就不打扰了）；
2. 没有搭建好的专属redis服务器（可能是资金紧缺相关因素，加之项目紧急等），因此就非常有必要进行安装windows版；

### 下载地址

- GitHub如：https://github.com/MicrosoftArchive/redis/releases，3.2.100版本
- 百度网盘链接: https://pan.baidu.com/s/1XDb2URcs_3Q3lCb7Fm4mvw 提取码: 5d1p
- GitHub一个老外上传的，地址：https://github.com/tporadowski/redis/tags

### 安装步骤

注意：本次演示以3.2.100版本为例
1、解压

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151127.png)

2、打开cmd指令窗口（快捷键：WIN键+R键），进入解压的路径里面

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151102.png)

4、然后输入`redis-server.exe redis.windows.conf` 命令。验证是否可用

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151096.png)

5、关掉前面开启窗口（**此为关闭启动redis**），再打开一个新的cmd命令窗口（**同样是刚刚的路径**）

输入指令`redis-server.exe --service-install redis.windows.conf`

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151595.png)

6、在电脑服务中开启`Redis`服务

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151195.png)

Redis常用的指令
安装服务：redis-server --service-install redis.windows.conf （步骤6）
卸载服务：redis-server --service-uninstall
开启服务：redis-server --service-start
停止服务：redis-server --service-stop

注意：redis常用指令需要在redis安装位置（或者说解压包位置）处cmd才可使用，不然会提示不是系统命令，当然可以采取配置环境变量的方式（未验证）

测试验证
通过cd到我们解压的目录,输入指令通过Set或get指令查看是否成功，当然也可key * 指令

![在这里插入图片描述](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109032151106.png)

以上就是windows10下redis安装。



## 2、Linux安装

1. 官网下载压缩包，放在服务器的opt文件夹里面

2. cd到这个目录，解压这个压缩包

   ```bash
   [root@LBJF opt]#  tar -zxvf redis-6.2.4.tar.gz
   ```

   

3. 安装gcc ==*也可以不装== 

   ```bash
   [root@LBJF redis-6.2.4]#   yum install gcc-c++
   
   [root@LBJF redis-6.2.4]#  gcc -v    //查看刚刚安装的gcc版本
   ```

   

4. 把所有文件自动配置上

   ```bash
   [root@LBJF redis-6.2.4]#  make
   
   [root@LBJF redis-6.2.4]#  make install    //确认安装
   ```

   

5. 把配置文件拷贝到根目录的/usr/local/bin/hyfConfig下，以后就用这个配置文件，出问题了，还可以恢复

   ```bash
   [root@LBJF bin]# mkdir hyfConfig       //创建文件夹
   
   cp /opt/redis-6.2.4/redis.conf hyfConfig        //复制文件到指定位置
   ```

6. 打开配置文件，把daemonize的no改为yes

   ![g2yiIPjaqnHmXBt](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730153904.png)

   ==**启动redis服务**==

   ```bash
   //进入到/usr/local/bin
   [root@LBJF bin]#  redis-server ./hyfConfig/redis.conf  
   redis-server redis.conf  
   
    //绝对路径完整版
   [root@LBJF /]# /usr/local/bin/redis-server /usr/local/bin//hyfConfig/redis.conf
   ```

7. 连接到指定端口号

   ```bash
   [root@LBJF bin]#  ./redis-cli    
   
   127.0.0.1:6379> quit     //退出， 用exit也可以退出
   ```

   ![image-20210624171118402](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730154151.png)

8. 测试一下

   <img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730154033.png" alt="image-20210624172247903" style="zoom:150%;" />

9. 查看所有的key

   

   <img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730154209.png" alt="image-20210624171337480" style="zoom:150%;" />

10. 查看radis进程是否开启

    ```bash
    [root@LBJF ......]#   ps -ef|grep redis      //查看所有关于redis的进程
    ```

    ![image-20210624173030765](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730161727.png)

11. 关闭服务 退出

    ```bash
    127.0.0.1:6379>  shutdown     #这是关闭radis进程，包括服务和端口
    
    not connected>  exit
    ```

    ![image-20210624173259181](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730183107.png)

    ![image-20210624173515450](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730183129.png)

## 3、测试性能

**redis-benchmark：**Redis官方提供的性能测试工具，参数选项如下：

![img](https://i.loli.net/2021/06/24/tUIwVMvJCLXnc8d.png)

**测试前一定要把redis服务和cli打开**

```bash
# 测试：100个并发连接 100000请求
[root@LBJF ~]#  redis-benchmark -h localhost -p 6379 -c 100 -n 100000
```

![image-20210624175340491](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730185832.png)

![image-20210624175853850](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730185834.png)

![image-20210624180009058](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730185839.png)

## 4、基础知识

> redis默认有16个数据库（DB 0~DB 15） 默认使用DB0

​		从redis.conf文件可以查看<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730185931.png" alt="image-20210624180619615" style="zoom:80%;" />

可以使用`select n`切换到DB n，`dbsize`可以查看当前数据库的大小，**key数量**有多少，大小就是多少。![image-20210624181059139](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730190001.png)

`flushdb`  清空当前数据库

`flushall`  清空所有数据库

`keys`  查看当前数据库所有key

> **Redis是单线程的，Redis是基于内存操作的。**

所以Redis的**性能**瓶颈不是CPU,而是机器**内存**和**网络带宽**。

> **Redis为什么单线程还这么快？**

 核心：Redis是将所有的数据放在内存中的，所以说使用单线程去操作效率就是最高的，多线程（CPU上下文会切换：耗时的操作！），`对于内存系统来说，如果没有上下文切换效率就是最高的`，多次读写都是在一个CPU上的，在内存存储数据情况下，单线程就是最佳的方案。

# 三、五大数据类型

 Redis是一个开源（BSD许可），内存存储的数据结构服务器，可用作数据库，高速缓存和消息队列代理。它支持字符串、哈希表、列表、集合、有序集合，位图，hyperloglogs等数据类型。内置复制、Lua脚本、LRU收回、事务以及不同级别磁盘持久化功能，同时通过Redis Sentinel提供高可用，通过Redis Cluster提供自动分区。

## 0、规则：

> 在redis中无论什么数据类型，在数据库中都是以key-value形式保存，通过进行对Redis-key的操作，来完成对数据库中数据的操作。

下面学习的命令：

- `exists [key]`：判断键是否存在<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194558.png" alt="image-20210624185843801"  />

  ```
  exists hyf  或者 exist key hyf
  ```

  

- `del [key]`：删除键值对![image-20210624185933006](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194603.png)

- `move [key] [db]`：将键值对移动到指定数据库

  ![image-20210624190201584](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194808.png)

- `expire [key] [second]`：设置键值对过期时间(秒)![image-20210624190851236](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194833.png)

- `ttl [key]`:查看key的过期剩余时间(秒)![image-20210624190938929](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194840.png)

  ​	**未**设置过期时间：返回**-1**

  ​	**已**设置过期时间 **已过期**：返回**-2**

  ​	已设置过期时间 未过期：正整数秒

- `type [key]`：查看value的数据类型![image-20210624191111680](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194847.png)

  

关于**重命名**`RENAME`和`RENAMENX`

- `rename [key] [newkey]`：强制修改 key 的名称，**会覆盖**
- `renamenx [key] [newkey]`仅当 newkey 不存在时改名，不会覆盖

## 1、String(字符串)

- `append [key] [value]`：后方**追加字符串**

![image-20210624201139319](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730194920.png)

- `incr/decr [key]`: **值+1/-1 (仅对于数字)**

  ![image-20210624201838318](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195320.png)

- `incrby/decrby [key] [n]`​：**加或者减一个数​**

![image-20210624202508068](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195556.png)

- `incrbyfloat [key] [n]`：**加一个小数**![image-20210624202743509](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195622.png)

- `strlen [key]`：**获取值的长度**![image-20210624202858760](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195626.png)

- `getrange [key] [start] [end]`：**字符串截取** （闭区间，起止位置都取）

  ![image-20210624203200834](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195636.png)

- `setrange [key] [offset] [value]`：**字符串替换**(替换key中从offset下标开始的值)

![image-20210624203601227](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730195712.png)

- `getset [key] [value]`：**设置新值，返回旧值**

![image-20210624203838384](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204239.png)

- `setnx [key]  [value]`：**当key不存在时才set**

- `setex [key]  [seconds] [value]`:  **设置键值 并 设置过期时间**

![image-20210624204357952](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204249.png)

- `mset [key1] [value1] [key2] [value2]...`: **批量set键值对**

  ![image-20210624204610450](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204256.png)

- `msetnx [key1] [value1] [key2] [value2]...`: **批量set键值对**, 仅当所有的key都不存在时执行

- `mget [key1] [key2]..`：**批量获取多个key的值**
- `psetex [key] [milliseconds] [value]`：**设置过期时间(毫秒)**

## 3、List(列表)

> Redis列表是简单的**字符串列表**，按照插入顺序排序。
>
> 你可以添加一个元素到列表的头部（左边）或者尾部（右边）
>
> 一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。

![[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-VPvbIltc-1597890996518)(狂神说 Redis.assets/image-20200813114255459.png)]](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109221754766.png)

正如图**Redis中List是可以进行双端操作的，所以命令也就分为了LXXX和RLLL两类**，有时候L也表示List例如LLEN

- `lpush/rpush [key] [value1][value2]...`：**从左/右向列表中push值**(一个或多个)

  ![image-20210625123137299](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204319.png)

- `lrange [key] [start] [end]`：**从左开始获取它的起止下标的值**

  ![image-20210625123414556](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204321.png)

- `lpushx/rpushx [key] [value]`：**向已存在的列表push值**（一个或者多个，不存在添加不了）![image-20210625124439042](C:/Users/24358/Desktop/dNhslDwv25aze8j.png)

- `linsert [key] before|after [pivot] [value]`：**在元素pivot的前/后 插入value**,从左开始查找![image-20210625130516643](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204335.png)

- `llen key`：**查看列表长度**

  ![image-20210625130707612](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204344.png)

- `lindex [key] [index]`：**通过索引获取列表元素**

  ![image-20210625131320874](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204354.png)

- `lset [key] [index] [value]`：**通过索引为元素修改值**(只能修改已存在的key的值)

  ![image-20210625131905461](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204401.png)

- `lpop/rpop [count]`：**从最左/最右边 移除count个值 并返回**

  ![image-20210625132121959](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204411.png)

- `rpoplpush source destination`：**将列表的最后一个值(尾部右)弹出**，并返回，然后加到另一个列表(**可以不存在**)的头部

  ![image-20210625132440375](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204426.png)

- `ltrim [key] [start] [end]`：**通过下标截取(只保留)指定范围内的列表**

  ![image-20210625132800569](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204438.png)

- `lrem [key] [count] [value]`：List中是允许value重复的 

  ​			`count > 0`：从头部开始搜索 然后删除指定的value 至多删除count个

  ​			`count < 0`：从尾部开始搜索… 

  ​			`count = 0`：删除列表中所有的指定value。

- `blpop/brpop [key1][key2] [timout]`：**移出并获取列表的第一个/最后一个元素**， **如果列表暂时还没有元素**会阻塞列表**直到等待超时或发现可弹出元素为止**。

- `brpoplpush [source] [destination] [timeout]`：和`rpoplpush`功能相同，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。

> 小结

- list实际上是一个链表，before Node after , left, right 都可以插入值
- **如果key不存在，则创建新的链表**
- 如果key存在，新增内容
- 如果移除了所有值，空链表，也代表不存在
- 在两边插入或者改动值，效率最高！修改中间元素，效率相对较低

> **应用：**

**消息排队！消息队列（Lpush Rpop）,栈（Lpush Lpop）**

## 4、Set(集合)

> Redis的Set是**String类型**的**无序集合**。集合**成员是唯一的**，这就意味着集合中**不能出现重复**的数据。
>
> Redis 中 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
>
> 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。

- `sadd key member1[member2..]`：**向集合中无序增加一个/多个成员**

  ![image-20210625134655785](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204454.png)

- `scard [key]`：**获取集合的成员数**

- `smembers [key]`：**返回集合中所有的成员**

  ![image-20210625134850205](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204504.png)

- `sismember [key] [member]`：**查询member元素是否是集合的成员**,结果是无序的![image-20210625135114749](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204511.png)

- `srandmember [key] [count]`：**随机返回集合中count个成员**，**count缺省值为1!**

- `spop [key] [count]`：随机**移除并返回**集合中count个成员，count缺省值为1

- `smove [source] [destination] [member]`：将source集合的成员member移动到destination集合

- `srem [key] [member1][member2..]`：移除集合中一个/多个成员

- `sdiff [key1][key2..]`：**返回所有集合的差集** key1- key2 - …

- `sdiffstore [destination] [key1][key2..]`：在差集的基础上，将结果保存到集合中*(覆盖)*。不能保存到其他类型key噢！

- `sinter [key1] [key2..]`：**返回所有集合的交集**

- `sinterstore [destination] [key1][key2..]`：在交集的基础上，存储结果到集合中。覆盖

- `sunion [key1] [key2..]`：**返回所有集合的并集**

- `sunionstore [destination] [key1] [key2..]`：在并集的基础上，存储结果到及和张。覆盖

- `sscan [key] [match pattern] [count count]`：**在大量数据环境下，使用此命令遍历集合中元素，每次遍历部分**

## 5、Hash（哈希）

> Redis hash 是一个string类型的field和value的映射表，**hash特别适合用于存储对象**。
>
> Set就是一种简化的Hash,只变动key,而value使用默认值填充。可以将一个Hash表作为一个对象进行存储，表中存放对象的信息。

- `hset [key] [field] [value]`：将哈希表 key 中的字段 field 的值设为 value 。重复设置同一个field会覆盖,返回0

  ![image-20210625141808420](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204520.png)

- `hmset [key] [field1] [value1]  [field2] [value2]...`：同时将多个 field-value (域-值)对设置到哈希表 key 中

- `hsetnx [key] [field] [value]`：**只有 field 不存在时，设置value。**

- `hexists [key] [field]`：**指定的字段是否存在。**

- `hget [key] [field]`：**获取*一个*字段的值**

  ​	![image-20210625142043078](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204742.png)

- `hmget [key] [field1] [field2..]`：**获取多个字段的值**，不可缺省

- `hgetall [key]`：**获取所有字段和值**

  ![image-20210625142641560](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204733.png)

- `hkeys [key]`：**获取所有的字段**

- `hlen [key]`：**获取字段的数量**

- `hvals [key]`：**获取所有值**

- `hdel [key] [field1] [field2]...`：**删除哈希表key中一个/多个field字段**

- `hincrby [key] [field] [n]`：为某字段（**值是数**）加上**n**，并返回增量后结果 一样只适用于整数型字段

  ![image-20210625143424842](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204731.png)

- `hincrbyfloat [key] [field] [n]`：为某字段（**值是数**）的浮点数值加上 n。

- `hscan [key] [cursor] [MATCH pattern] [COUNT count]`：**迭代哈希表中的键值对。**

>  Hash变更的数据user name age，尤其是用户信息之类的，经常变动的信息！**Hash更适合于对象的存储，Sring更加适合字符串存储！**



## 6、Zset（有序集合）

> 每个元素都会关联一个double类型的分数（score）。redis正是**通过分数来为集合中的成员进行从小到大的排序**。
>
> score相同：按字典顺序排序

- `zadd key score member1 [score2 member2]`：向有序集合添加一个或多个成员，或者更新已存在成员的分数

  ![image-20210625145115405](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204729.png)

- `zcard key`：**获取成员数**

  ![image-20210625145232608](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204726.png)

- `zcount key min max`：**指定score区间的成员数**

- `zinccrby key n member`：**成员的分数加上增量 n**

- 其他https://www.runoob.com/redis/redis-sorted-sets.html

> 应用案例：
>
> - set排序 存储班级成绩表 工资表排序！
> - 普通消息，1.重要消息 2.带权重进行判断
> - 排行榜应用实现，取Top N测试

# 四、三种特殊数据类型

## 1、Geospatial(地理位置)

> 使用经纬度定位地理坐标并用一个**有序集合zset保存**，所以zset命令也可以使用

- `geoadd key longitud(经度) latitude(纬度) member [..]`：将具体经纬度的坐标存入一个有序集合
- `geopos key member [member..]`：获取集合中的一个/多个成员坐标
- `geodist key member1 member2 [unit]`：返回两个给定位置之间的距离。默认以米作为单位。
- `georadius key longitude latitude radius m|km|mi|ft [WITHCOORD][WITHDIST] [WITHHASH] [COUNT count]`：以给定的经纬度为中心， 返回集合包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。
- `GEORADIUSBYMEMBER key member radius...`：功能与GEORADIUS相同，只是中心位置不是具体的经纬度，而是使用结合中已有的成员作为中心点。
- `geohash key member1 [member2..]`：返回一个或多个位置元素的Geohash表示。使用Geohash位置52点整数编码。

**有效经纬度**

>  有效的经度从-180度到180度。
>
> 有效的纬度从-85.05112878度到85.05112878度。

指定单位的参数 **unit** 必须是以下单位的其中一个：

- **m** 表示单位为米。
- **km** 表示单位为千米。
- **mi** 表示单位为英里。
- **ft** 表示单位为英尺。

**关于GEORADIUS的参数**

> 通过`georadius`就可以完成 **附近的人**功能
>
> withcoord:带上坐标
>
> withdist:带上距离，单位与半径单位相同
>
> COUNT n : 只显示前n个(按距离递增排序)

## 2、Hyperloglog(基数统计)

> Redis HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的。
>
> 花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基数。
>
> 因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
>
> 其底层使用string数据类型

**什么是基数？**

> 数据集中**不重复**的元素的个数。

**应用场景：**

网页的访问量（UV）：一个用户多次访问，也只能算作一个人。

> <u>传统实现</u>，存储用户的id,然后每次进行比较。当用户变多之后这种方式及其浪费空间，而我们的目的只是**计数**。
>
> Hyperloglog就能帮助我们利用最小的空间完成。

- `pfadd [key] [element1] [elememt2]...`：**添加1-n个元素到 HyperLogLog 中**

![image-20210626125202729](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204721.png)

- `pfcount [key]`：**返回基数个数。**

  ![image-20210626125452625](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204716.png)

- `pfmerge [destkey] [sourcekey] [sourcekey]...`：将**多个它合并为一个新的它**

![image-20210626125623319](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204714.png)

> 如果允许容错，那么一定可以使用Hyperloglog !
>
> 如果不允许容错，就使用set或者自己的数据类型即可 ！



## 3、BitMaps(位图)

> 使用**位存储**，信息状态只有 0 和 1
>
> Bitmap是一串连续的2进制数字（0或1），每一位所在的位置为偏移(offset)，在bitmap上可执行AND,OR,XOR,NOT以及其它位操作。

**应用场景**

签到统计、状态统计

- `setbit key offset value`：为指定key的offset位设置值

  ```bash
  127.0.0.1:6379> setbit sign 0 1 # 设置sign的第0位为 1 
  (integer) 0
  127.0.0.1:6379> setbit sign 2 1 # 设置sign的第2位为 1  不设置默认 是0
  (integer) 0
  ```

- `getbit key offset`：获取offset位的值

  ```bash
  127.0.0.1:6379> getbit sign 2 # 获取第2位的数值
  (integer) 1
  ```

- `bitcount key [start end]`：统计字符串被设置为1的bit数，也可以指定统计范围按字节

  ```bash
  127.0.0.1:6379> BITCOUNT sign # 统计sign中为1的位数
  (integer) 4
  ```

- `bitop operration destkey key[key..]`：对一个或多个保存二进制位的字符串 key 进行位元操作，并将结果保存到 destkey 上。

- `bitpos key bit [start] [end]`：返回字符串里面第一个被设置为1或者0的bit位。start和end只能按字节,不能按位

**bitmaps的底层**

bitmaps是一串从左到右的二进制串



# 五、事务

`Redis的单条命令是保证原子性的，但是redis事务不能保证原子性`

<u>**Redis事务本质：一组命令的集合。**</u>

事务中每条命令都会被序列化，执行过程中按顺序执行，不允许其他命令进行干扰。

- 一次性
- 顺序性
- 排他性

**Redis事务没有隔离级别的概念**

## 1、Redis事务操作过程

- 开启事务（`multi`）
- 命令入队
- 执行事务（`exec`）

所有事务中的命令在加入时都未被执行，直到提交时才会开始执行(Exec)一次性完成。

![image-20210626131735651](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204709.png)



**取消事务(`discard`)**

![image-20210626132133702](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204708.png)

**事务错误**

> 代码**<u>语法错误</u>**（编译时异常）**所有的命令都不执行**

![tbBceH7W2qOSzCi](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204706.png)



> 代码**<u>逻辑错误</u>** (运行时异常) **其他命令可以正常执行 ** >>> <mark>所以不保证事务原子性</mark>

![image-20210626133617812](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204704.png)

*# 虽然中间有一条命令报错了，但是后面的指令依旧正常执行成功了。* 

*# 所以说Redis单条指令保证原子性，但是Redis事务不能保证原子性。*

## 2、监控

### **悲观锁：**

​		 很悲观，认为什么时候都会出现问题，无论做什么都会加锁

### **乐观锁：**

- ​		很乐观，认为什么时候都不会出现问题，所以不会上锁！**更新数据的时候去判断一下，在此期间是否有人修改过这个数据**
- 获取version
- 更新的时候比较version

使用**watch key**监控指定数据，相当于乐观锁加锁。

> 正常执行

![image-20210626142447450](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204701.png)

> **多线程修改值**之后，使用watch可以当做redis的乐观锁操作（相当于getversion）

1. 左边线程先执行这么多

   ![image-20210626143932868](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204700.png)

2. 右边线程**插队**执行修改money的值

   ![](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204658.png)

3. 最后执行左边线程，执行失败

   ![image-20210626144131302](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210730204656.png)

> 解锁获取最新值，然后再加锁进行事务。
>
> `unwatch`进行解锁。

<mark>**注意：每次提交执行exec后都会自动释放锁，不管是否成功**</mark>

## 3、持久化

### （1）RDB持久化

每隔一段时间直接把数据存入文件，先写到一个临时文件，写完之后替换掉原来的文件。整个数据库只有一个文件。文件扩展名是RDB

#### 优点

- 如果是系统出现了大故障，这时候可以直接还原，而且恢复速度快
- 如果并发亮很大，可以减少很多IO操作



### （2）AOF

每执行一次修改或者删除的命令，就在文件末尾追加一行日志。文件扩展名AOF

#### 优点

- 保存数据更加安全，因为数据库只要一被修改，它就会添加日志进去
- 格式清晰，打开文件是一个日志的形式





# 六、Jedis(java操作)

使用Java来操作Redis，Jedis是Redis官方推荐使用的Java连接redis的客户端。

## 1、导入依赖

```xml
<dependency>
    <!--Spring-Data-Redis-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

## 2、配置文件yml

```yml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
```

## 3、测试

### （1）测试类

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Sb04DemoApplication.class) //加载spring的启动类
public class Demo {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
```

### （2）String类型

```java
BoundValueOperations<String, String> str1 = redisTemplate.boundValueOps("str");

//添加键值
str1.set("何昱飞飞");
//获取键值
String string = str1.get();
System.out.println(string);
```

### （3）list集合

```java
BoundListOperations<String, String> list1 = redisTemplate.boundListOps("list");

//添加list集合
list1.leftPush("aa");
list1.leftPush("bb");
list1.leftPush("cc");
list1.leftPush("dd");
list1.rightPush("11");
list1.rightPush("22");
list1.rightPush("33");

//删除指定值
list1.remove(1,"何昱飞");//3表示删除3个'aa'
//修改制定位置值
list1.set(0,"何昱飞");//设置第二个是何昱飞
//查询所有
List<String> list = list1.range(0,-1);//输出全部
System.out.println(list);
//查询单个
final String sub = list1.index(1);//下标是1的值
System.out.println(sub);
```

### （4）hash集合

```java
 BoundHashOperations<String, Object, Object> hash = redisTemplate.boundHashOps("hash");
hash.put("aa","何昱飞");
hash.put("bb","王惠敏");
hash.put("cc","xff");
hash.put("dd","宝贝");
// 查询所有Key
final Set<Object> hashKey = hash.keys();
System.out.println(hashKey);
//查询所有value
final List<Object> hashValue =  hash.values();
System.out.println(hashValue);
//根据键获取值
String value = (String) hash.get("aa");
System.out.println(value);
//根据键删除值
hash.delete("cc");
```

### （5）set集合

```java
 BoundSetOperations<String, String> set = redisTemplate.boundSetOps("set");
 
set.add("何昱飞","王惠敏","haha~","你好特别","你好特别","特别");
//查询所有
final Set<String> members = set.members();
System.out.println(members);

//根据键删除值
set.remove("特别");
```

```java
 BoundSetOperations<String, String> set = redisTemplate.boundSetOps("set");
 
set.add("何昱飞","王惠敏","haha~","你好特别","你好特别","特别");
//查询所有
final Set<String> members = set.members();
System.out.println(members);

//根据键删除值
set.remove("特别");
```

## 4、使用redis的缓存

### （1）实体类实现`Serializable`接口

​	变成二进制流

### （2）在服务层使用缓存

```java
@Service
@Transactional  //事务注解
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
	@Override
    public List<User> queryAllUser() {
        //先从缓存里面查，查不到再要数据库查
        List<User> users = (List<User>) redisTemplate.opsForValue().get("users");
        if (users==null){
            users = userDao.findAll(); //从数据库拿
            System.out.println("从数据库拿的");
            //写到缓存里面
//            redisTemplate.boundValueOps("users").set(users);
            redisTemplate.opsForValue().set("users",users);
            return users;
        }
        System.out.println("从缓存拿的");
        return users;
    }
}
```

### （3）查看redis中的键值

![image-20210904112120091](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109041121459.png)

# 七、缓存

## 1、缓存类型

单值缓存

对象缓存

![image-20210916234147969](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109162341453.png)

## 2、分布式锁

![image-20210916235654841](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109162356013.png)

