package com.youtu.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.portal.pojo.CartItem;
import com.youtu.portal.service.CartService;

/**
 * 购物车Controller
 *@author:王贤锐
 *@date:2018年1月24日  下午7:54:55
**/
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 添加商品到购物车,用户必须登录，才能使用购物车，购物车信息保存到redis中
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		YouTuResult result = cartService.addCartItem(itemId, num, request, response);
		//跳转到别的url请求，防止用户刷新页面就添加一个新的商品
		return "redirect:cartSuccess.html";
	}
	
	@RequestMapping("/add/cartSuccess")
	public String showCarSuccess(){
		return "cartSuccess";
	}
	/**
	 * 展示购物车列表页面，从cookie中取数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/cartList")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> list = cartService.getCartList(request, response);
		model.addAttribute("cartList", list);
		if(list != null){
			model.addAttribute("itemNum", list.size());
		}else{
			model.addAttribute("itemNum", 0);
		}
		
		return "cart";
	}
	/**
	 * 购物车修改商品数量
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping(value="/update/num/{itemId}/{num}",method=RequestMethod.POST)
	@ResponseBody
	public String updateNum(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response){
		YouTuResult result = cartService.updateNum(itemId, num, request, response);
		if(result.getStatus()==200){
			return "OK";
		}
		return "ERROR";
		
	}
	/**
	 * 删除购物车中的商品，刷新购物车列表页面
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		cartService.deleteCartItemByItemId(itemId, request, response);
		return "redirect:/cart/cartList.html";
	}
}
