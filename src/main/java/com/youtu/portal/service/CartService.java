package com.youtu.portal.service;
/**
 *@author:王贤锐
 *@date:2018年1月24日  下午4:45:37
**/

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.portal.pojo.CartItem;

public interface CartService {
	YouTuResult addCartItem(long itemId,Integer num,HttpServletRequest request, HttpServletResponse response);
	
	List<CartItem> getCartList(HttpServletRequest request, HttpServletResponse response);
	
	YouTuResult updateNum(Long itemId,Integer num,HttpServletRequest request, HttpServletResponse response);
	
	YouTuResult deleteCartItemByItemId(Long itemId,HttpServletRequest request,HttpServletResponse response);
}
