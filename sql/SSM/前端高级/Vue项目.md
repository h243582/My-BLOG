# 一、安装cnpm和cli

## 安装`Node.js`

然后就可以在命令行查看到版本了

D:\Program Files\Node.js>`node -v`
v16.6.1

D:\Program Files\Node.js>`npm -v`
7.20.3

## 设置npm安装路径和缓存路径

```
npm config set prefix "D:\Program Files\Node.js\node_modules"

npm config set cache "D:\Program Files\Node.js\node_cache"
```

## 设置环境变量

先别设置，看看有没有用

```
//添加系统变量
NODE_PATH     D:\Program Files\Node.js\node_modules

//添加用户变量
Path     D:\Program Files\Node.js\node_global
```



## 安装express

==-g是安装的意思，如果没有-g就会安装到在当前路径下==

```
npm install express -g
```

## 安装cnpm 

```
npm install cnpm -g
```

## 安装vue-cli

```
cnpm install vue-cli -g

//安装指定版本cli
cnpm install -g @vue/cli@3    安装3版本最高版
cnpm install -g @vue/cli@版本号  安装 3.x 和 4.x 版本
cnpm install -g vue-cli@版本号    安装 1.x 和 2.x 版本


//1.查看 Vue Cli 的所有版本号
cnpm view @vue/cli versions --json    3.x 和 4.x 的所有版本号
cnpm view vue-cli versions --json   1.x 和 2.x 的所有版本号

//卸载Vue Cli
npm uninstall -g @vue/cli   卸载 3.x 或 4.x 版本的Vue Cli
npm uninstall vue-cli -g   卸载 1.x 或 2.x 版本的Vue Cli
```





## 查看创建项目的方式

```
vue list
```

![image-20210808141835419](C:/Users/24358/AppData/Roaming/Typora/typora-user-images/image-20210808141835419.png)



# 二、安装webpack和webpack-cli

## 安装webpack 

+ ```
  npm install webpack -g
  ```

## 安装webpack-cli

```
npm install webpack-cli -g
```

## 查看版本

```
webpack -v
```



# 三、命令行创建项目

## 创建好项目文件夹

### 进入目录

```
F:
cd F:\WebStorm_se\Vue\
```

### 在这个目录创建项目

```cmd
vue init webpack my-first-vue
```

![image-20210808153154499](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210808153154.png)

## 进入项目下载依赖

```
cd my-first-vue
npm install
```

## 打包并且运行服务器

```
npm run dev
```

## 安装vue-router路由

```
cnpm install vue-router --save-dev
```

## 安装SASS加载器

==用来解析sass文件，不用安装==

```
npm install sass-loader@7.3.1 node-sass@4.14.1 --save-dev
```









# 三、ui方式安装

### 安装vue ui

```
cnpm i -g @vue/cli
```

### 启动vue ui

```
vue ui
```

### 跟着步骤装好项目



### 添加element-ui插件

![image-20210803134634283](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803134634.png)

`vue-cli-plugin-element`

![image-20210803134320002](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803134320.png)

![image-20210803134458145](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803134458.png)

### 添加axios

![image-20210803134657338](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803134657.png)

![image-20210803134716813](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803134716.png)

# 安装touch

```
npm install touch-cli -g
```















## 使用webpack打包

#### main.js文件

```js
var hello = require("./hello");

hello.sayHi();
```

#### hello.js文件

```js
//暴露一个方法
exports.sayHi = function () {
    document.write("<h1>何昱飞</h1>")

};
```

#### webpack.config.js配置文件，文件名貌似不能改。 必须放在根目录下

```js
module.exports = {
    entry:"./modules/main.js",
    output:{
        filename:"./js/bundle.js"
    }
}
```

#### 在终端打包

输入webpack， idea必须以管理员身份运行，不然可能出错

![p2SuyKPHkIXA4gt](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803132242.png)



也可以**热部署**， 输入`webpack --watch`, 随时自动打包，直接覆盖原文件，Ctrl + c停止

#### 这时候会生成打包好的js文件

![yVSrna6qBJ21lG8](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803131754.png)

#### 最后直接调用

![7WOxiaIyuYsJfdN](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210803131709.png)







