# 一、概念

```
<script src="https://unpkg.com/vue/dist/vue.js"></script>
```

## 1、思想

- 整机通常由各个部件构成，各个部件实现特定的功能

- 利用这些模块，进行排列组合，就可以实现不同功能的机器设备。

  

**标准：**只有标准化，才能带来更大程度的互换性。
**分治：**每个模块可以实现各自独立的功能
**重用：**同一个功能模块，也可能被多次复用
**组合：**基于前三个特征而产生！各个模块组合起来成为一个新的产品。

# 二、语法

## 1、基础语法

### （1）插值

```html
<div id="app">
  <p>{{ msg }}</p>
</div>

<script>
new Vue({
  el: '#app',
  data: {
    msg: 'Hello Vue.js!'
  }
})
</script>
```



### （2）v-html

==v-text同理==

```html
<div id="app">
    <div v-html="msg"></div>
</div>

<script>
new Vue({
  el: '#app',
  data: {
    msg: '<h1>菜鸟教程</h1>'
  }
})
</script>
```

### （3）`v-model`绑定值

**v-model** 指令用来在 `input`、`select`、`textarea`、`checkbox`、`radio` 等表单控件元素上创建双向数据绑定，根据表单上的值，自动更新绑定的元素的值。

#### ①作用

在给`<input/>`元素添加`v-model`属性时，默认会把`value`作为元素的属性，然后把'input'事件作为实时传递value的触发事件

#### ③v-model 的缺点和解决办法

##### 问题

在创建类似**单选框**的常见组件时，`v-model`就不好用了。

它需要的不是 其他属性，而是 `name`属性(==一次只能一个被选中==)

##### 解决方法

```html
<input type="radio" name="{{use}}">
```



### （4）`v-bind`绑定属性

==v-bind可以被缩写成 :==

例：`:value`   `:id`

**绑定标签的属性**![image-20210801192656415](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210801192656.png)

![image-20210801192559921](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210801192601.png)

#### ① `v-bind:class`

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802125321.png" alt="image-20210802125321749" style="border: 1px solid;" />

```html
<div id="app">
    <div v-bind:class="{'active': isActive,'www':true}"></div>
    <div v-bind:class="rr"></div> <!--这里的rr不是普通字符串，它是变量-->
    <div v-bind:class="lists"></div>
    <div v-bind:class="maps"></div>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            isActive: true,
            rr: "te",
            lists: ['ww','qq','rr'],
            maps: {'ww':true,'qq':true}
        }
    })
</script>
```

#### ②`v-bind:style`

```html
<div v-bind:style="{ color: myColor, fontSize: mySize + 'px' }">菜鸟教程</div>
            myColor: '#992299',
            mySize: 30

<div v-bind:style="{ color: myColor, fontSize: mySize}">菜鸟教程</div>
            myColor: '#992299',
            mySize: '30px'
  <div v-bind:style="styleObject">菜鸟教程</div>
	    styleObject: {
      	      color: 'red',
                fontSize: "40px",
                border: "1px solid red",
                marginTop:"10px"
    	}

```









####  `$event.target`获取属性

 ==获取当前元素的子元素==可以获取id

下面这是一个全选功能，`v-bind:checked`的值是`flag`这个变量，`@change`(当字段改变时)所有被`flag`绑定的`checkbox`标签都会被选定或取消

```html
<input type="checkbox" :checked="flag" @change="flag = $event.target.checked" >
```

### （5）`v-on`事件

==v-on可以被缩写成@==

例：`@click`  `@change`

![image-20210801195738542](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210801195738.png)

#### ①`v-on:click`

`v-on:click` `<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210801204328.png" alt="image-20210801204328699" style="zoom:80%;" />

```html
<button v-on:click="demo">banniu</button>
<button      @click="demo">banniu</button>
```

### （6）`v-if` `v-else` `v-else-if`

```html
<div id="app">     
    <p v-if="seen ===1">你看见了1</p> <!--seen 是data的变量-->
    <p v-else-if="seen ===2">你看见了2</p>
    <p v-else>你看不见我</p>
</div>
```

### （7）`v-show`

v-show 指令来根据条件展示元素：



### （8）`v-for`

```html
<div id="app">
    <div v-for="s in lists">
        {{s}}
    </div><br>

    <span v-for="s in a">
        {{s}}
    </span><br>

    <div v-for="s in users">
        {{s.name +' - ' + s.password}}
        {{s}}
    </div>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            lists:{
                ua1:'何昱飞',
                ua2:'王惠敏',
                ua3:'何小飞'
            }            ,
            a: [1,2,3,4,5],
            users: [
                {name:'何昱飞',password:'123456'},
                {name:'王惠敏',password:'123456'},
            ]
        }
    })
</script>
```

<img src="https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/20210802002054.png" alt="image-20210802002054087" style="zoom: 80%;border: 1px solid red" />

可以提供第二个的参数为键名：

```html
    <li v-for="(value, key) in object"> 
```

第三个参数为索引：

```html
    <li v-for="(value, key, index) in object">
```

也可以循环整数

```html
    <li v-for="n in 10">    <!--n的值是1~10 ！-->
```

### （9）watch监听

```JavaScript
var vm = new Vue({
    el:"#app",
    data:{
        msg: 1
    },
    watch:{
        msg:function (new, old){
            alert("只要msg值被改变，就执行这个方法内容")
        }
    }
})
```

**也可以下面这么写**

```JavaScript
 vm.$watch('msg', function(new, old) {
     alert("只要msg值被改变，就执行这个方法内容")
 });
```



# 三、组件

## 1、component-组件

`<hyf> </hyf>`

```javascript
方法1：
        Vue.component('hyf',{
		template:'<h1>自定义的组件</h1>'
        })
方法2：
        var Child = {
        	template: '<h1>自定义组件!</h1>'
        }
        new Vue({
        	el: '#app',
        	components:{
            	hyf: Child
       	 	}
	})
方法3：
        new Vue({
        	el: '#app',
        	components:{
			'hyf': {template: "<h1>自定义的组件</h1>"}
       	 	}
	}) 
```

### ①Prop

**prop 是子组件用来接受父组件传递过来的数据的一个自定义属性。**

```html
<div id="app">
    <child heyufei="hello!"></child>
</div>

<script>
    // 创建根实例
    new Vue({
        el: '#app',
        components: {
            "child": {
                props: ['heyufei'],
                template: '<span>{{ message }}</span>'
            }
        }
    })
</script>
```





![image-20210821110746722](https://heyufei-1305336662.cos.ap-shanghai.myqcloud.com/my_img/202108211107555.png)









