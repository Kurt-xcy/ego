package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryServiceImpl implements TbContentCategoryDubboService {
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<TbContentCategory> selByPid(long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}
	@Override
	public int insCategory(TbContentCategory category) {
		int index =0;
		index = tbContentCategoryMapper.insertSelective(category);
		return index;
	}
	@Override
	public int updCategory(TbContentCategory category) {
		int index = 0;
		index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
		return index;
	}
	@Override
	public TbContentCategory selById(long id) {
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		return category;
	}

}
