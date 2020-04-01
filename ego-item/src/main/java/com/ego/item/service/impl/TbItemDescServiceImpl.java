package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Value("${redis.desc.key}")
	private String descKey;
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public String showDesc(long itemid) {
		
		String key = descKey+itemid;
		String desc = tbItemDescDubboServiceImpl.selByItemid(itemid).getItemDesc();
		if (jedisDaoImpl.exists(key)) {
			if (jedisDaoImpl.get(key)!=null&&!jedisDaoImpl.get(key).equals("")) {
				return  jedisDaoImpl.get(key);
			}
		}
		jedisDaoImpl.set(descKey+itemid, desc);
		return desc;
	}

}
