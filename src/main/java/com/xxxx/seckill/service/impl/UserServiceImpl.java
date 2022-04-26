package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxxx.seckill.exception.GlobalException;
import com.xxxx.seckill.mapper.UserMapper;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;

import com.xxxx.seckill.utils.CookieUtil;
import com.xxxx.seckill.utils.MD5Util;
import com.xxxx.seckill.utils.UUIDUtil;
import com.xxxx.seckill.utils.ValidatoUtil;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public RespBean doLogin(LoginVo loginVo,HttpServletRequest request, HttpServletResponse response) {
		String mobile = loginVo.getMobile();
		String password = loginVo.getPassword();

//		if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//			return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//		}
//		if(!ValidatoUtil.isMobile(mobile)) return RespBean.error(RespBeanEnum.MOBILE_ERROR);
		User user = userMapper.selectById(mobile);
		System.out.println(user);
		//System.out.println(user);
		if(user==null) throw  new GlobalException(RespBeanEnum.LOGIN_ERROR);

		if(!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
			throw  new GlobalException(RespBeanEnum.LOGIN_ERROR);
		}

		//生存cookie
		String ticket = UUIDUtil.uuid();
		//System.out.println(ticket);
	//	request.getSession().setAttribute(ticket,user);

		redisTemplate.opsForValue().set("user:"+ticket,user);

		CookieUtil.setCookie(request,response,"userTicket",ticket);

		return RespBean.success(ticket);

	}

	@Override
	public User getUserByCookie(String userTicket,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(userTicket) ) return null;
		User user  =(User) redisTemplate.opsForValue().get("user:"+userTicket);
		if(user!=null)
		CookieUtil.setCookie(request,response,"userTicket",userTicket);
		return user;
	}

	@Override
	public RespBean updatePassword(String userTicket, Long id, String password) {

		User user = userMapper.selectById(id);

		if(user==null) throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);

		user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
		int result = userMapper.updateById(user);
		if(result==1)
		{
			redisTemplate.delete("user:"+userTicket);
			return RespBean.success();
		}
		return  RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAILED);

	}


}
