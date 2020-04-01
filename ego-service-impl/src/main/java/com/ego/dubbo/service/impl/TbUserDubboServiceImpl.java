package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

public class TbUserDubboServiceImpl implements TbUserDubboService{
	@Resource
	private TbUserMapper tbUserMapperImpl;
	@Override
	public TbUser selByUser(TbUser user) {
		TbUserExample example = new TbUserExample();
		System.out.println("username:"+user.getUsername());
		System.out.println("password:"+user.getPassword());
		example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
		List<TbUser> list = tbUserMapperImpl.selectByExample(example);		
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}

}
