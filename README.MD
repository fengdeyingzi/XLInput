这个输入法用了两个开源库，根据WhatsInput改的，加了新的功能
用于实现通过局域网连接，在电脑上给手机打字

## 使用方法：
首先开启输入法，
手机电脑链接同一个WIFI,然后在电脑上输入局域网网址就可以了

### 手机---> 前端

1.  开始编辑

```
{"text":"sssss", "type":"InputStart"}
```

- type 消息类型 InputStart
- text 开始编辑时的内容

2.  编辑结束

```
 {"type": "InputFinish" }
```

- type 消息类型 InputFinish


3. 编辑框内容变化

```
{"type": "InputChange", text: ""}
```

- type 消息类型
- text 变化后的编辑框内容。	



### js端

向手机发送按键请求
```
{type:"InputKey",code:66};
```

向手机发送输入请求
```
{type:"InputCommit",text:"风的影子"};
```

向手机发送文本设置请求
```
{type:"InputEdit",text:"文本"};
```


## 待实现的功能
向手机发送截图请求
{type:"getscreen"};



向手机发送触屏点击请求
{type:"touchscreen",x:"250",y:"10"};




