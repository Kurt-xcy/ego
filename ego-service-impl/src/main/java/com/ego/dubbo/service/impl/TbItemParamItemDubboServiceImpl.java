package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{

	@Resource
	private TbItemParamItemMapper tbItemParamItemMapperImpl;
	
	@Override
	public TbItemParamItem selByItemid(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapperImpl.selectByExampleWithBLOBs(example);
		
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
