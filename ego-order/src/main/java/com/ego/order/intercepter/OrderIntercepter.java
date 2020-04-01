package com.ego.order.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;

public class OrderIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (cookieValue!=null&&!cookieValue.equals("")) {
			String result = HttpClientUtil.doPost("http://localhost:8084/user/token/"+cookieValue);
			EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
			if (er.getStatus()==200) {
				return true;
			}
		}
		String num = request.getParameter("num");
		if (num != null && !num.equals("")) {
			response.sendRedirect("http://localhost:8084/user/showLogin?interurl="+request.getRequestURL()+"%3Fnum="+num);
		}else {
			response.sendRedirect("http://localhost:8084/user/showLogin?interurl="+request.getRequestURL());
		}
		
		return false;
	}

}
