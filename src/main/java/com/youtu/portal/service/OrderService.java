package com.youtu.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youtu.portal.pojo.Order;

/**
 *@author:王贤锐
 *@date:2018年1月26日  下午9:59:38
**/
public interface OrderService {
	String createOrder(Order order,HttpServletRequest request,HttpServletResponse response);
}
