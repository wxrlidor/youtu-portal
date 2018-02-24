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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		// 把字符串转换成TaotaoResult
		try { 
			YouTuResult youTuResult = YouTuResult.formatToList(result, TbContent.class);
			// 取内容列表
			List<TbContent> list = (List<TbContent>) youTuResult.getData();
			List<Map> resultList = new ArrayList<>();
			// 转换成Json数据格式，创建一个jsp页码要求的pojo列表
			// 遍历列表list
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
				//添加上面的List<Map>
				resultList.add(map);
			}
			// 把resultList转换成Json格式字符串
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			//在控制台打印出异常
			e.printStackTrace();
		}
		return null;
	}

}
