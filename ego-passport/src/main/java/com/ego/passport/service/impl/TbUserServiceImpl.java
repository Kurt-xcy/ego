package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService{
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = new EgoResult();
		TbUser getuser = tbUserDubboServiceImpl.selByUser(user);
		if (getuser!=null) {
			
			String key = UUID.randomUUID().toString();
			jedisDaoImpl.set(key, JsonUtils.objectToJson(getuser));
			jedisDaoImpl.expire(key, 3*24*60*60);
			//产生cookie
			CookieUtils.setCookie(request, response, "TT_TOKEN", key, 3*24*60*60);
			er.setStatus(200);
			er.setData(getuser);
			return er;
		}else {
			er.setMsg("用户名和密码错误");
		}
		return er;	
	}
	@Override
	public EgoResult getUserInfoByToken(String token) {
		EgoResult er = new EgoResult();
		String json = jedisDaoImpl.get(token);
		if (json!=null&&!json.equals("")) {
			TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
			user.setPassword(null);
			er.setData(user);
			er.setMsg("OK");
			er.setStatus(200);
		}else {
			er.setMsg("获取失败");
		}
		return er;
	}
	@Override
	public EgoResult logout(HttpServletRequest request, HttpServletResponse response, String token) {
		Long index = jedisDaoImpl.del(token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		EgoResult er = new EgoResult();
		er.setStatus(200);
		er.setMsg("OK");
		return er;
	}
	
}
