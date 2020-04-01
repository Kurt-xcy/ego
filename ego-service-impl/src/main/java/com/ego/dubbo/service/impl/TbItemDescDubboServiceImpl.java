package com.ego.dubbo.service.impl;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemDescExample;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public int insDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.insert(itemDesc);
	}
	@Override
	public TbItemDesc selByItemid(long itemid) {
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemid);
		return tbItemDesc;
	}

}
