package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {
	/**
	 * 根据用户名密码查询user
	 * @param user
	 * @return
	 */
	TbUser selByUser(TbUser user);
}
