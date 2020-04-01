package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.ItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemServiceImpl implements TbItemService{
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Value("${redis.item.key}")
	private String itemkey;
	@Reference
	TbItemDubboService tbItemDubboServiceImpl;
	@Override
	public ItemChild show(long id) {
		String key = itemkey + id;
		if(jedisDaoImpl.exists(key)){
			String json = jedisDaoImpl.get(key);
			if(json!=null&&!json.equals("")){
				return JsonUtils.jsonToPojo(json, ItemChild.class);
			}
		}
		
		TbItem tbItem = tbItemDubboServiceImpl.selByPrimaryKey(id);
		ItemChild child = new ItemChild();
		child.setId(tbItem.getId());
		child.setTitle(tbItem.getTitle());
		child.setPrice(tbItem.getPrice());
		child.setSellPoint(tbItem.getSellPoint());
		child.setImages(tbItem.getImage()!=null&&!tbItem.equals ("")?tbItem.getImage().split(","):new String[1]);
		
		//存到redis数据库中
		jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		return child;
	}

}
