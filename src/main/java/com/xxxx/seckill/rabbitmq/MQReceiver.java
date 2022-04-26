package com.xxxx.seckill.rabbitmq;

import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrder;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.utils.JsonUtil;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IGoodsService goodsService;

    @RabbitListener(queues = "seckillQueue")
    public  void receive(String msg)
    {
        log.info("接受的消息"+msg);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        User user = seckillMessage.getUser();
        Long goodId = seckillMessage.getGoodId();


        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodId);
        if(goodsVo.getStockCount()<1)return;
        SeckillOrder seckillOrder =(SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodId);
        if(seckillOrder!=null) return ;
        orderService.seckill(user,goodsVo);

    }

}
