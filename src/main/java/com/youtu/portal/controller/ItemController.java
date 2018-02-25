package com.youtu.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.pojo.TbItem;
import com.youtu.portal.pojo.SearchResult;
import com.youtu.portal.service.ItemService;

/**
 * 搜索相关controller
 * 
 * @author:王贤锐
 * @date:2018年1月21日 下午8:32:40
 **/
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 接收请求的参数查询条件和页码。调用Service查询商品列表得到SearchResult对象。 需要把 Query：回显的查询条件
	 * totalPages：总页数 itemList：商品列表 Page：当前页码 传递到页面。返回一个逻辑视图search字符串。
	 * 
	 * @param queryString
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/search")
	public String searchItems(@RequestParam(value = "q", defaultValue = "手机") String queryString,
			@RequestParam(defaultValue = "1") Integer page, Model model) {
		if (queryString != null) {
			try {
				//解决乱码问题
				queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		SearchResult result = itemService.searchItems(queryString, page);
		// 向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);

		return "search";
	}
	/**
	 * 根据商品id调用rest服务查询商品基本信息
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String getItemBaseInfoById(@PathVariable Long itemId,Model model){
		TbItem item = itemService.getItemBaseInfo(itemId);
		model.addAttribute("item", item);
		
		return "item";
	}
	/**
	 * 根据商品id调用rest服务查询商品描述信息，返回字符串
	 * 加入utf-8防止中文乱码
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDescById(@PathVariable Long itemId,Model model){
		String desc = itemService.getItemDescById(itemId);
		return desc;
	}
	/**
	 * 根据商品id调用rest服务查询商品规格参数，返回html
	 * 加入utf-8防止中文乱码
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParamById(@PathVariable Long itemId,Model model){
		String param = itemService.getItemParamItem(itemId);
		return param;
	}
}
