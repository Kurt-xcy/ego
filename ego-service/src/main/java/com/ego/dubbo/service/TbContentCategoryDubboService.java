package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	/**
	 * 根据父id查询所有子类目
	 * @param id
	 * @return
	 */
	List<TbContentCategory> selByPid(long id);
	/**
	 * 新增内容
	 * @param category
	 * @return
	 */
	int insCategory(TbContentCategory category);
	/**
	 * 更新内容
	 * @param category
	 * @return
	 */
	int updCategory(TbContentCategory category);
	/**
	 * 根据主键id查找内容
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
	
	
}
