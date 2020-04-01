package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap; 
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.ItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
@Service
public class OrderServiceImpl implements OrderService{
	@Resource
	private JedisDao jedisDaoImpl;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	@Override
	public List<ItemChild> showOrder(HttpServletRequest request, List<Long> id) {
		
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult result = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = "cart:"+((LinkedHashMap)result.getData()).get("username");
		List<ItemChild> newlist = new ArrayList<>();
		if (jedisDaoImpl.exists(key)) {
			String json = jedisDaoImpl.get(key);
			List<ItemChild> childlist = JsonUtils.jsonToList(json, ItemChild.class);
			for(ItemChild child:childlist){
				for(Long iid :id){
					TbItem item = tbItemDubboServiceImpl.selByPrimaryKey((long)iid);
					if (iid.longValue()==child.getId().longValue()) {
						if (item.getNum()>=child.getNum()) {
							child.setEnough(true);
						}else{
							child.setEnough(false);
						}
						newlist.add(child);
					}
				}
			}
			return newlist;
		}
		return null;
	}
	@Override
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {
		//订单表数据
		TbOrder order = new TbOrder();
		order.setPayment(param.getPayment());
		order.setPaymentType(param.getPaymentType());
		long id = IDUtils.genItemId();
		order.setOrderId(id+"");
		Date date =new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String result = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		Map user= (LinkedHashMap)er.getData();
		order.setUserId(Long.parseLong(user.get("id").toString()));
		order.setBuyerNick(user.get("username").toString());
		order.setBuyerRate(0);
		//订单-商品表
		for (TbOrderItem  item : param.getOrderItems()) {
			item.setId(IDUtils.genItemId()+"");
			item.setOrderId(id+"");
		}
		//收货人信息
		TbOrderShipping shipping = param.getOrderShipping();
		shipping.setOrderId(id+"");
		shipping.setCreated(date);
		shipping.setUpdated(date);
		
		EgoResult erResult = new EgoResult();
		try {
			int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
			if(index>0){
				erResult.setStatus(200);
				//删除购买的商品
				String json = jedisDaoImpl.get("cart:"+user.get("username"));
				List<ItemChild> listCart = JsonUtils.jsonToList(json, ItemChild.class);
				List<ItemChild> listNew = new ArrayList<>();
				for (ItemChild child : listCart) {
					for (TbOrderItem item : param.getOrderItems()) {
						System.out.println("1"+child.getId().longValue());
						System.out.println("2"+Long.parseLong(item.getItemId()));
						
						if(child.getId().longValue()==Long.parseLong(item.getItemId())){
							listNew.add(child);
						}
					}
				}
				for (ItemChild mynew : listNew) {
					listCart.remove(mynew);
				}
				jedisDaoImpl.set("cart:"+user.get("username"), JsonUtils.objectToJson(listCart));
				//删除
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return erResult;
	}

}
