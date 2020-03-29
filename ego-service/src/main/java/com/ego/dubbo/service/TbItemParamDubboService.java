package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 分页查询数据
	 * @param page
	 * @param rows
	 * @return 包含：当前页显示数据和总条数
	 */
	EasyUIDataGrid showPage(int page,int rows);
	/**
	 * 根据id批量删除商品规格参数条目
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int delByIds(String ids) throws Exception;
	/**
	 * 根据catid返回商品规格参数
	 * @param catid
	 * @return
	 */
	TbItemParam selByCatId(long catid);
	/**
	 * 新增商品规格参数条目
	 * @return
	 */
	int insParam(TbItemParam param);
}
