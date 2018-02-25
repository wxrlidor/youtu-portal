package com.youtu.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.CookieUtils;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.common.utils.JsonUtils;
import com.youtu.portal.pojo.Order;
import com.youtu.portal.service.OrderService;

/**
 * 订单Service
 *@author:王贤锐
 *@date:2018年1月26日  下午10:01:30
**/
@Service
public class OrderServiceImpl implements OrderService {
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	/**
	 * 接收Order对象，调用youtu-order提供的服务，提交订单。需要把pojo转换成json数据。
	 * 调用youtu-order提供的服务返回taotaoResult，包含订单号。订单创建成功后删除购物车cookie
	 */
	@Override
	public String createOrder(Order order,HttpServletRequest request,HttpServletResponse response) {
		//调用youtu-order的服务提交订单。
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成taotaoResult
		YouTuResult youtuResult = YouTuResult.format(json);
		if (youtuResult.getStatus() == 200) {
			Long orderId = (Long) youtuResult.getData();
			//提交订单成功后，删除购物车cooike
			CookieUtils.deleteCookie(request, response, "YOUTU_CART");
			return orderId.toString();
		}
		return "";
	}

}
