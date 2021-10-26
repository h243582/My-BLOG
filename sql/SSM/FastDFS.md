# 一、简介

## 1、简介

1. 是一个开源的轻量级的 分布式文件系统
2. 采用C语言开发，由阿里巴巴开发并开源。
3. `FastDFS`对文件进行管理，功能包括：文件存储、同步、访问（上传，下载，删除）
4. 重复注重冗余备份，线程扩容



# 二、单实例部署

## 1、安装依赖

```
sudo install -y gcc gcc-c++ perl perl-devel openssl openssl-devel pcre pcre-devel zlib zlib-devel libevent libevent-devel
```

## 2、安装`libfastcommon`库

### 下载：

```
wget https://github.com/happyfish100/libfastcommon/archive/V1.0.43.tar.gz
```

### 解压：

```
tar -zxvf V1.0.43.tar.gz
```

### 切换：

```
cd libfastcommon-1.0.43
```

### 编译：

```
./make.sh
```

### **安装：**

```
sudo ./make.sh install
cd ~
```

## 3、安装`FastDFS`

### **下载：**

```
wget https://github.com/happyfish100/fastdfs/archive/V6.06.tar.gz
```

### **解压：**

```
tar -zxvf V6.06.tar.gz
```

### **切换：**

```
cd fastdfs-6.06
```

### **编译：**

```
./make.sh
```

### **安装：**

```
sudo ./make.sh install*
```

### **查看可执行文件：**

```
ll /usr/bin/fdfs*
```

![image-20200905183808335](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9naXRlZS5jb20vY2FvY2hlbmxlaS9CbG9nSW1hZ2VzL3Jhdy9tYXN0ZXIvMjAyMDA5MDUxODM5MTEucG5n?x-oss-process=image/format,png)

### **查看配置文件**：

```
ll /etc/fdfs/
```

![image-20200905184005284](https://i.loli.net/2021/08/23/VmnyY81bEh6JpZv.png)

### **拷贝其它配置：**

```
cd conf

sudo cp http.conf /etc/fdfs/
sudo cp mime.types /etc/fdfs/

cd /etc/fdfs/

sudo mv client.conf.sample client.conf
sudo mv storage.conf.sample storage.conf
sudo mv storage_ids.conf.sample storage_ids.conf
sudo mv tracker.conf.sample tracker.conf
```

## 4、配置`FastDFS`

### **配置 tracker ：**

修改tracker.conf的以下几项配置项：vi tracker.conf

- \#配置tracker存储数据的目录
  - base_path = /opt/fastdfs/tracker

创建相对应的文件夹：

```
mkdir -p /opt/fastdfs/tracker
```

### 配置 storage ：

修改storage.conf的以下几项配置项：vi storage.conf

#storage存储数据目录
base_path = /opt/fastdfs/storage
#真正存放文件的目录
store_path0 = /opt/fastdfs/storage/files
#注册当前存储节点的跟踪器地址
tracker_server = 192.168.239.128:22122
创建相对应的文件夹：

