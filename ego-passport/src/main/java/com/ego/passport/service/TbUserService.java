package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 登陆操作
	 * @param user
	 * @return
	 */
	EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	EgoResult getUserInfoByToken(String token);
	
	/**
	 * 根据token删除cookie,redis
	 * @param request
	 * @param response
	 * @param token
	 * @return
	 */
	EgoResult logout(HttpServletRequest request,HttpServletResponse response,String token);
}
