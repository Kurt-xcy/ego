package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Override
	public PortalMenu selAll() {
		if(tbItemCatDubboServiceImpl==null){
			System.out.println("tbItemCatDubboServiceImpl  is  null");
		}
		PortalMenu menu = new PortalMenu();
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		List<Object> li=selIn(list);
		menu.setData(li);
		return menu;
	}
	
	private List<Object> selIn(List<TbItemCat> list){
		List<Object> myList = new ArrayList<>();
		for(TbItemCat cat:list){
			if (cat.getIsParent()) {//如果有子节点
				PortalMenuNode node = new PortalMenuNode();
				node.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
				node.setU("/products/"+cat.getId()+".html");
				node.setI(selIn(tbItemCatDubboServiceImpl.show(cat.getId())));
				myList.add(node);
			}else {
				myList.add("/products/"+cat.getId()+".html|"+cat.getName());
			}
		}
		return myList;
	}

}
