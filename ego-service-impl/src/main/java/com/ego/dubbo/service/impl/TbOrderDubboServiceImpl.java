package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService{
	@Resource
	TbOrderMapper tbOrderMapperImpl;
	@Resource
	TbOrderItemMapper tbOrderItemMapperImpl;
	@Resource
	TbOrderShippingMapper tbOrderShippingMapperImpl;
	
	@Override
	public int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception {
		int index = tbOrderMapperImpl.insertSelective(order);
		for(TbOrderItem item:list){
			index += tbOrderItemMapperImpl.insertSelective(item);
		}
		index += tbOrderShippingMapperImpl.insertSelective(shipping);
		if (index==2+list.size()) {
			return 1;
		}else {
			throw new Exception("创建订单失败");
		}
	}

}
