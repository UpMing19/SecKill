
逆向工程

https://www.bilibili.com/video/BV1sf4y1L7KE
秒杀地址接口的隐藏未做

分布式session
jmeter压测
缓存：  页面、对象
页面静态化： 静态资源在前端完成跳转，这里不在用Vue等前端的框架，选择用ajax模拟手动渲染
yml过时了部分（缓存部分）没有加
超卖问题：
使用订单表 加 索引的 方式解决多线程下同一用户秒杀多次商品
问题：UpdateWrapper

redis预减库存：  内存标记减少redis的访问
使用RabbitMQ完成秒杀操作：  在Listener中完成原来controller中的操作
客户端的轮询： （异步下单操作）订单是否生成成功，这里是用RabbitMQ 来 存所有的”点击秒杀“，然后消费者监听 轮询 产生订单
lua脚本分布式锁：确保原子性


-----------------安全性------------
秒杀按钮 置灰
隐藏接口地址   随机uuid存在redis （设置过期时间 ，过期重置）   拼接在秒杀接口地址中
验证码   生成  +  验证 （通过后点击才可以点击秒杀）


限流：  一般为最大QPS的70%,  计数器算法容易浪费资源，（比如前一半时间打满 ，后面一直空闲）