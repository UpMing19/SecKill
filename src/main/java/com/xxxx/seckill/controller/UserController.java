package com.xxxx.seckill.controller;


import com.xxxx.seckill.pojo.User;


import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;



}
