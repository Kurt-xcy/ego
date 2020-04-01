package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.ItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService{
	@Resource
	private JedisDao jedisDaoImpl;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Override
	public void addCart(long id, int num, HttpServletRequest request) {
		List<ItemChild> list = new ArrayList<>();
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String json = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
		String key = "cart:"+((LinkedHashMap<String, String>)er.getData()).get("username");
		System.out.println("key:"+key);
		System.out.println("num:"+num);
		//如果 redis中存在key
		if (jedisDaoImpl.exists(key)) {
			String json2 = jedisDaoImpl.get(key);
			if (json2!=null&&!json2.equals("")) {
				System.out.println("json2:"+json2);
				list = JsonUtils.jsonToList(json2, ItemChild.class);
				System.out.println("list:"+list);
				for(ItemChild child :list){
					if(child.getId()==id){
						child.setNum(child.getNum()+num);
						JsonUtils.objectToJson(list);
						return;
					}
				}
			}
			
		}
		TbItem item = tbItemDubboServiceImpl.selByPrimaryKey(id);
		ItemChild child2 = new ItemChild();
		child2.setId(item.getId()); 
		child2.setTitle(item.getTitle()); 
		child2.setImages(item.getImage()==null||item.getImage().equals("")?new String[1]:item.getImage().split(",")); 
		child2.setNum(num); 
		child2.setPrice(item.getPrice()); 
		list.add(child2);
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
	}
	@Override
	public List<ItemChild> showCart(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = "cart:"+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		return JsonUtils.jsonToList(json, ItemChild.class);
	}
	@Override
	public EgoResult delete(long id, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = "cart:"+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<ItemChild> list = JsonUtils.jsonToList(json, ItemChild.class);
		ItemChild delchild = new ItemChild();
		for(ItemChild child :list){
			if (child.getId()==id) {
				delchild = child;
			}
		}
		if (delchild!=null) {
			list.remove(delchild);
			String jsonlist = JsonUtils.objectToJson(list);
			String ok = jedisDaoImpl.set(key, jsonlist);
			if (ok.equals("OK")) {
				er.setStatus(200);
				er.setMsg("删除成功");
			}
		}
		return er;
	}
	@Override
	public EgoResult update(long id, int num, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = "cart:"+((LinkedHashMap)er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<ItemChild> list = JsonUtils.jsonToList(json, ItemChild.class);
		for(ItemChild child:list){
			if (child.getId()==id) {
				child.setNum(num);
				er.setStatus(200);
			}
		}
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		return er;
	}

}
