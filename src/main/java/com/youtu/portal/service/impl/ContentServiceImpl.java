package com.youtu.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.common.utils.JsonUtils;
import com.youtu.pojo.TbContent;
import com.youtu.portal.service.ContentService;

/**
 * 调用服务层服务，查询内容列表
 * 
 * @author:张晓芬
 * @date:2018年2月24日 下午7:21:11
 **/
@Service
public class ContentServiceImpl implements ContentService {
	
	// @Value注入属性REST_BASE_URL // 获得字符串数值REST_BASE_URL
	@Value("${REST_BASE_URL}") 
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	@Value("${REST_INDEX_RIGHT_AD_URL}")
	private String REST_INDEX_RIGHT_AD_URL;
	@Value("${REST_INDEX_LITTLE_AD_URL}")
	private String REST_INDEX_LITTLE_AD_URL;
	
	/**
	 * 调用服务层，查询大广告位列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getAdContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		// 把字符串转换成TaotaoResult
		try {
			YouTuResult taotaoResult = YouTuResult.formatToList(result, TbContent.class);
			// 取内容列表
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<>();
			// 创建一个jsp页面要求的pojo列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 调用服务层，查询右上角广告位列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getRightAdContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_RIGHT_AD_URL);
		// 把字符串转换成TaotaoResult
		try {
			YouTuResult taotaoResult = YouTuResult.formatToList(result, TbContent.class);
			// 取内容列表
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<>();
			// 创建一个jsp页面要求的pojo列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 70);
				map.put("width", 310);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 210);
				map.put("heightB", 70);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询小广告位数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getLittleAdContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_LITTLE_AD_URL);
		// 把字符串转换成TaotaoResult
		try {
			YouTuResult taotaoResult = YouTuResult.formatToList(result, TbContent.class);
			// 取内容列表
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<>();
			int count = 0;// list遍历的计数器
			// 创建一个jsp页面要求的pojo列表
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("alt", "");
				map.put("href", tbContent.getUrl());
				map.put("index", count);
				map.put("src", tbContent.getPic());
				map.put("ext", "");
				resultList.add(map);
				count++;
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
