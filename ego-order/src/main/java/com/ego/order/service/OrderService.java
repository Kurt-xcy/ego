package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.ItemChild;
import com.ego.order.pojo.MyOrderParam;

public interface OrderService {
	List<ItemChild> showOrder(HttpServletRequest request,List<Long> id);
	
	EgoResult create(MyOrderParam param,HttpServletRequest request);
}
