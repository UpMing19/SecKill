package com.xxxx.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    LOGIN_ERROR(500210,"用户名或密码不正确"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    EMPTY_STOCK(500213,"空库存"),
    REPEATE_ERROR(500214,"重复秒杀"),
    MOBILE_NOT_EXIST(500215,"号码不存在"),
    PASSWORD_UPDATE_FAILED(500215,"密码更新失败"),
    SESSION_ERROR(500216,"用户不存在"),
    ORDER_NOT_EXIT(500217,"订单不存在")
    ;
    private  final  Integer code;
    private  final  String message;

}
