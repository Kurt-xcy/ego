package com.ego.portal.service.impl;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoPic;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigpic.key}")
	private String key;
	@Override
	public String showPage() {
		if(jedisDaoImpl.exists(key)){
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")){
				return value;
			}
		}
		
//		List<Map<String, Object>> list = new ArrayList<>();
		List<TbContent> listcontent = tbContentDubboServiceImpl.selByCount(6, true);
//		for(TbContent content :listcontent){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("srcB", content.getPic2()); 
//			map.put("height", 240); 
//			map.put("alt", "对不起,加载图片失败"); 
//			map.put("width", 670); 
//			map.put("src", content.getPic());
//			map.put("widthB", 550); 
//			map.put("href", content.getUrl() ); 
//			map.put("heightB", 240);
			
		/////////////////
		List<EgoPic> list = new ArrayList<>();
		for(TbContent content :listcontent){
			EgoPic pic = new EgoPic();
			pic.setSrcB(content.getPic2());
			pic.setHeight(240);
			pic.setAlt("对不起,加载图片失败");
			pic.setWidth(670);
			pic.setSrc(content.getPic());
			pic.setWidthB(550);
			pic.setHref(content.getUrl());
			pic.setHeightB(240);
			list.add(pic);
		}
		///////////////////
			
//			list.add(map);
		
		
		String listjson = JsonUtils.objectToJson(list);
		jedisDaoImpl.set(key, listjson);
		return listjson;
	}

}
