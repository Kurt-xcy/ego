package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 新增
	 * @param itemDesc
	 * @return
	 */
	int insDesc(TbItemDesc itemDesc);
	
	/**
	 * 通过商品id查询描述
	 * @param itemid
	 * @return
	 */
	TbItemDesc selByItemid(long itemid);
}
