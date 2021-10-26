# 一、JPA

简化JDBC操作，直接没有了mapper.xml

## 1、接口命名

```java
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
	
    public List<User> queryAllByUsernameContains(String username);
	
}
```

![img](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109021449370.jpeg)

例如：![image-20210902145559274](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109021455326.png)

```
public List<User> findByUserLike(String usernameLike);
//select * from stu where sanme 
```

## 2、如果不按规范写方法

###  `@Query`自定义SQL代码

==参数中加上`nativeQuery =true`，变成原生的SQL代码==

```java
@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    //模糊查询
    public List<User> queryAllByUsernameContains(String username);

    //查询前五名
    @Query(value = "select * from `ssm-01-login`.user order by id desc ",nativeQuery = true)
    public List<User> queryUserById();
}
```

### jpa遇到关键字的解决办法

①平时数据库中：user是关键字，加上`` `就行了

```
select * from `user`
```

②但是web中 application.yml中加上下面代码即可：

```
#    可以解决实体类设计的关键字的问题
spring:
	jpa:    
	    properties:
	      hibernate:
	        globally_quoted_identifiers: true
```



## 3、使用步骤

### （1）依赖 	

![image-20210902105911484](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202109021059160.png)

```xml
<dependency>
    <!--数据访问层data-jpa-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### （2）yml开启

```yml
jpa:
  database: mysql
  show-sql: true    #显示SQL语句
  generate-ddl: true
```

### （3）User类

```java
@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id  //主键
    private int id;
    @Column(name = "rid") //字段不一致这样子指定
    private int ridddd;
    private String username;
    private String password;
    private String photo;
}
```

### （4）Dao层

==继承JpaRepository类，其中User指定表，Integer指定主键==

```java
import com.gan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {}
```

### （5）UserService

下面的这几个方法，在DAO层里面不用写，父类**`JpaRepository`**自带了有

```java
public interface UserService {
    public List<User> queryAllUser();

    public User queryUserById(int id);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUserById(int id);
}
```

### （6）UserServiceImpl

==这些都是父类写好了的，可以直接用==

1. findAll 遍历所有用户
2. findById  根据id查找某个用户
3. save  添加用户
4. saveAndFlush 修改用户
5. deleteById  删除用户，通过id

```java
@Service
@Transactional  //事务注解
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryAllUser() {
        return userDao.findAll();
    }

    @Override
    public User queryUserById(int id) {
        return userDao.findById(id).get();
    }

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.saveAndFlush(user);
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }
}
```

# 二、SpringCaching缓存

## 1、开启缓存

为启动类添加`@EnableCaching`开启缓存支持

```
@SpringBootApplication
@EnableCaching //qspring Caching缓存
public class GatheringApplication {

   public static void main(String[] args) {
      SpringApplication.run(GatheringApplication.class, args);
   }

   @Bean
   public IdWorker idWorkker(){
      return new IdWorker(1, 1);
   }
   
}
```

## 2、具体要缓存的地方添加注解

在GatheringService的findById方法添加缓存注解，这样当此方法第一次运行，在
缓存中没有找到对应的value和key，则将查询结果放入缓存。

==**"gathering"整个缓存在springdata中叫什么名字，没什么用但是必须起**==

### （1）添加`@Cacheable`

`@Cacheable`(value="gathering",key="#id")

~~~java
@Cacheable(value="gathering",key="#id")
public Gathering findById(String id) {
	return gatheringDao.findById(id).get();
}
~~~

### （2）修改或删除`@CacheEvict`

在GatheringService的update、deleteById方法上添加清除缓存的注解

~~~java
/**
* 修改
* @param gathering
*/
@CacheEvict(value="gathering",key="#gathering.id")
public void update(Gathering gathering) {
gatheringDao.save(gathering);
}
/**
* 删除
* @param id
*/
@CacheEvict(value="gathering",key="#id")
public void deleteById(String id) {
gatheringDao.deleteById(id);
}
~~~

