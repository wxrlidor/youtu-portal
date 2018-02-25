package com.youtu.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youtu.pojo.TbUser;
import com.youtu.portal.pojo.CartItem;
import com.youtu.portal.pojo.Order;
import com.youtu.portal.service.CartService;
import com.youtu.portal.service.OrderService;

/**
 * 订单Controller
 * 
 * @author:王贤锐
 * @date:2018年1月26日 下午9:09:03
 **/
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 取购物车商品列表
		List<CartItem> list = cartService.getCartList(request, response);
		// 传递给页面
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	/**
	 * 接收页面提交的表单的内容，调用Service创建订单。返回成功页面。
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("/create")
	public String createOrder(Order order, Model model,HttpServletRequest request,HttpServletResponse response) {
		try {
			//从request中取出用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			//补全订单中的用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getNickname());
			String orderId = orderService.createOrder(order,request,response);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			//预计3天后送到
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "order-success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错，请联系管理员18813299913 王贤锐");
			//出错则返回友好提示页面
			return "error/exception";
		}
	}
}
