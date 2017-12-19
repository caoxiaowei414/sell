# SpringBoot微信点餐系统


> 以Spring Boot和微信特性为核心技术栈，实现一个从下单到接单流程完整，包含买家端和卖家端前后台功能的微信点餐系统 ,采用前后端分离，前端采用vue实现,后端使用Spring Boot开发 前端项目地址  [传送门](https://github.com/ldlood/VUE-ELM " 传送门")  注意 这个前端代码和这个项目中的那个前端代码是有差别的请使用项目中的那个 前端代码.7z  请修改 前端代码/config/index.js中的 sellUrl,openidUrl,wechatPayUrl为自己的路径
还有 项目中的支付 授权 退款，请使用自己的appid
微信授权可以自己注册测试号 ，但是微信支付必须是企业号，个人没法注册。


## 技术点
* Spring Boot
* 分布式Session
* 分布式锁
* 微信授权登录
* 微信消息推送
* 微信支付与退款
* Spring Boot + WebSocket 
* 前后端分离
* Vue.js

## Sql语句
``` sql
-- 类目
create table `product_category` (
    `category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment '类目名字',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`category_id`),
    unique key `uqe_category_type` (`category_type`)
);

-- 商品
create table `product_info` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '单价',
    `product_stock` int not null comment '库存',
    `product_description` varchar(64) comment '描述',
    `product_icon` varchar(512) comment '小图',
    `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`product_id`)
);

-- 订单
create table `order_master` (
    `order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment '买家名字',
    `buyer_phone` varchar(32) not null comment '买家电话',
    `buyer_address` varchar(128) not null comment '买家地址',
    `buyer_openid` varchar(64) not null comment '买家微信openid',
    `order_amount` decimal(8,2) not null comment '订单总金额',
    `order_status` tinyint(3) not null default '0' comment '订单状态, 默认为新下单',
    `pay_status` tinyint(3) not null default '0' comment '支付状态, 默认未支付',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

-- 订单商品
create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '当前价格,单位分',
    `product_quantity` int not null comment '数量',
    `product_icon` varchar(512) comment '小图',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`),
    foreign key(`order_id`) REFERENCES order_master(`order_id`)
);

-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '微信openid',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
) comment '卖家信息表';
```

## 前端代码运行方式
``` 
#安装依赖包
npm install

#运行项目 
npm run dev  

#打包
npm run build

#最后部署dist文件到nginx/iis/apache  推荐使用nginx 
``` 
## 项目配置说明
-  前端项目配置  打开前端项目的 `config/index.js` 里面 `build` 节点下的 `sellUrl`  `openidUrl`  `wechatPayUrl`   配置的自己的项目地址
- 后端项目配置 打开后端项目的的 `src/main/resources/application.yml`  ，请将里面的mysql ,redis配置为自己的地址，微信配置见下表

| 配置项  | 说明 |
| ------------- | ------------- |
| mpAppId  |微信公众号AppId  |
| mpAppSecret  | 微信公众号AppSecret |
| openAppId  | 微信开放平台AppId |
|  openAppSecret |  微信开放平台AppSecret|
|  mchId | 微信支付Id |
| mchKey  |  微信支付密钥|
|keyPath   |微信支付文件路径  |
|   notifyUrl|  微信支付异步回调地址|
| templateId  |微信模板消息Id  |

## 微信账号说明
- 微信公众号认证需要企业资质，个人无法申请。但是个人可以申请微信测试号，在本项目中，微信测试号可以用于微信授权登陆，微信模板消息发送。
- 微信支付和微信退款必须要企业认证的公众号，所以如果自己个人想做微信支付和微信退款，只能用公司的公众号或者去买。
- 微信开放平台账号也必须是企业认证的，个人没法注册，在本项目中，微信开放平台的账号主要用于扫码登陆，如果你没有微信开放平台的账号，你可以fork项目，然后自己开发一个账号密码登陆也可以。
- 我项目中的各种微信账号是自己的测试号 ，请使用自己的。
- 如果谁有微信开放平台的账号借我一用，非常感谢，只是测试微信扫描登陆的，不做其他任何事情

## 项目部署说明
- 按照上面的 项目配置说明 将项目配置好，，前端和后端都需
- 将打包后的前端代码部署好，个人使用Nginx , 我在Nginx设置如下代理

      location /sell/ {
            proxy_pass http://127.0.0.1:8080/;
        }
- 用Maven将java项目打包成jar  用java -jar启动项目即可

## 有任何问题，欢迎邮件勾搭，小弟毕业不久的菜鸟，项目中还有很多问题，还请各位大佬指出。


# 老铁 如果项目对你有任何一点帮助的话 请给个Star 感谢 
