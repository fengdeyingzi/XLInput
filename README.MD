
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
{type:"InputKey",code:66};

向手机发送输入请求
{type:"InputCommit",text:"风的影子"};

向手机发送文本设置请求
{type:"InputEdit",text:"文本"};
