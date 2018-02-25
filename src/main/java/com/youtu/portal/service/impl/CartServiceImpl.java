package com.youtu.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.CookieUtils;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.common.utils.JsonUtils;
import com.youtu.pojo.TbItem;
import com.youtu.portal.pojo.CartItem;
import com.youtu.portal.service.CartService;

/**
 * 购物车service
 * 
 * @author:王贤锐
 * @date:2018年1月24日 下午4:46:34
 **/
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASEINFO_URL}")
	private String REST_ITEM_BASEINFO_URL;

	/**
	 * 从cookie中取购物车列表
	 * 
	 * @param request
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "YOUTU_CART", true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		// 把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	/**
	 * 添加购物车商品
	 */
	@Override
	public YouTuResult addCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 取商品信息
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cItem : itemList) {
			// 如果存在此商品
			if (cItem.getId() == itemId) {
				// 增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		if (cartItem == null) {
			cartItem = new CartItem();
			// 根据商品id查询商品基本信息。
			String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASEINFO_URL + itemId);
			// 把json转换成java对象
			YouTuResult taotaoResult = YouTuResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem item = (TbItem) taotaoResult.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			// 添加到购物车列表
			itemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "YOUTU_CART", JsonUtils.objectToJson(itemList), true);

		return YouTuResult.ok();
	}

	/**
	 * 展示购物车列表页面
	 */
	@Override
	public List<CartItem> getCartList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> result = getCartItemList(request);
		return result;
	}

	/**
	 * 购物车中修改商品数量
	 */
	@Override
	public YouTuResult updateNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cItem : itemList) {
			// 如果存在此商品
			if (cItem.getId() == itemId) {
				// 增加商品数量
				cItem.setNum(num);
			}
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "YOUTU_CART", JsonUtils.objectToJson(itemList), true);
		return YouTuResult.ok();
	}

	/**
	 * 删除购物车中的商品
	 */
	@Override
	public YouTuResult deleteCartItemByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 从列表中找到此商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				itemList.remove(cartItem);
				break;
			}

		}
		// 把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "YOUTU_CART", JsonUtils.objectToJson(itemList), true);

		return YouTuResult.ok();
	}

}
