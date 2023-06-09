package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.OrderDetailVo;

public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsId);

    OrderDetailVo detail(Long orderId);
}
