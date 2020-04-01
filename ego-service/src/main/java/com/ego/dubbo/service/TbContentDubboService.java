package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 按页查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selByPage(long categoryId,int page,int rows);
	/**
	 * 插入内容
	 * @param content
	 * @return
	 */
	int insContent(TbContent content);
	
	/**
	 * 按序查询一定数量的内容
	 * @param count
	 * @param sort
	 * @return
	 */
	List<TbContent> selByCount(int count,boolean issort);
}
