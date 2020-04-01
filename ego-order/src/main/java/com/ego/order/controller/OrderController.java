package com.ego.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.ItemChild;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.OrderService;

@Controller
public class OrderController {
	@Resource
	private OrderService orderServiceImpl;
	
	@RequestMapping("order/order-cart.html")
	public String showOrder(HttpServletRequest request,@RequestParam("id") List<Long> id,Model model){
		List<ItemChild> list =  orderServiceImpl.showOrder(request, id);
		model.addAttribute("cartList",list);
		return "order-cart";
	}
	
	@RequestMapping("order/create.html")
	public String createOrder(MyOrderParam param,HttpServletRequest request){
			EgoResult er = orderServiceImpl.create(param, request);
			if (er.getStatus()==200) {
				return "my-orders";
			}else {
				request.setAttribute("message", "订单创建失败");
			}
			return "error/exception";
	}
}
