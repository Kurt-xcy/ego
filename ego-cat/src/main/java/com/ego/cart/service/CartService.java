package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.ItemChild;

public interface CartService {
	/**
	 * 增加购物车商品
	 * @param id
	 * @param num
	 * @param request
	 */
	void addCart(long id, int num,HttpServletRequest request);
	
	/**
	 * 显示购物车商品
	 * @return
	 */
	List<ItemChild> showCart(HttpServletRequest request);
	
	/**
	 * 删除购物车商品
	 * @param id
	 * @param request
	 * @return
	 */
	EgoResult delete(long id,HttpServletRequest request);
	
	/**
	 * 修改商品数量
	 * @param id
	 * @param num
	 * @param request
	 * @return
	 */
	EgoResult update(long id,int num,HttpServletRequest request);
}
