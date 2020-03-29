package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		//使用分页插件，先设置分页条件
		PageHelper.startPage(page, rows);
		//设置查询的sql语句
		//查询全部
		//如果表中有一个或一个以上列的类型是text,生成的方法会有xxxwithBlogs()
				//如果使用xxxwithBlogs查询结果中包含带有text类的列，如果没有使用withBlogs方法不带有text类型
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//根据程序员自己编写的sql语句结合分页插件产生最终结果，封装到PageInfo
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		
		//设置方法返回结果
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		
		return dataGrid;
	}

	@Override
	public int delByIds(String ids) throws Exception {
		
		String[] idstrs = ids.split(",");
		int index = 0;
		for(String idstr:idstrs){
			long id = Long.parseLong(idstr);
			index += tbItemParamMapper.deleteByPrimaryKey(id);
		}
		if (index==idstrs.length) {
			return 1;
		}else{
			throw new Exception("删除错误，回滚");
		}
	}

	@Override
	public TbItemParam selByCatId(long catid) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if (list!=null&&list.size()>0) {
			//按数据库设计规则，一个商品条目对应一个规格参数
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insParam(TbItemParam param) {
		return tbItemParamMapper.insert(param);
	}
}
