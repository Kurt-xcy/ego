package com.ego.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	@Resource
	private TbContentMapper tbContentMapper;
	@Override
	public EasyUIDataGrid selByPage(long categoryId, int page, int rows) {
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		TbContentExample example = new TbContentExample();
		PageHelper.startPage(page, rows);
		if (categoryId!=0) {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}
	@Override
	public int insContent(TbContent content) {
		return tbContentMapper.insertSelective(content);
	}
	
	
	
	@Override
	public List<TbContent> selByCount(int count, boolean issort) {
		List<TbContent> list = new  ArrayList<>();
		
		TbContentExample example = new TbContentExample();
		if(issort){
			example.setOrderByClause("updated desc");
		}
		if (count != 0) {
			PageHelper.startPage(1, count);
		}
		list = tbContentMapper.selectByExample(example);
		return list;
	}
	
}
