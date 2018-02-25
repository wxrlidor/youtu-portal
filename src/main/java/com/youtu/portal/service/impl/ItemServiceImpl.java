package com.youtu.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.common.utils.JsonUtils;
import com.youtu.pojo.TbItem;
import com.youtu.pojo.TbItemDesc;
import com.youtu.pojo.TbItemParamItem;
import com.youtu.portal.pojo.SearchResult;
import com.youtu.portal.service.ItemService;

/**
 * 商品业务
 *@author:王贤锐
 *@date:2018年1月21日  下午8:17:15
**/
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${YOUTU_SEARCH_ITEM}")
	private String YOUTU_SEARCH_ITEM;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASEINFO_URL}")
	private String REST_ITEM_BASEINFO_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	
	/**
	 * 调用serach服务，取得搜索条件相匹配的商品列表
	 */
	@Override
	public SearchResult searchItems(String queryString, int page) {
		//创建查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		try {
			//调用search服务，带参数
			String json = HttpClientUtil.doGet(YOUTU_SEARCH_ITEM, param);
			//把json字符串转换成YoutuResult对象
			YouTuResult youTuResult = YouTuResult.formatToPojo(json, SearchResult.class);
			if(youTuResult.getStatus() == 200){
				//取得商品列表
				SearchResult result = (SearchResult) youTuResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 调用rest服务，取得商品的基本信息，返回一个pojo对象
	 */
	@Override
	public TbItem getItemBaseInfo(long itemId) {
		try {
			// 调用rest服务,参数直接跟在url后面
			String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASEINFO_URL +itemId);
			//判断是否为空
			if(!StringUtils.isBlank(json)){
				//将json转换成pojo对象
				YouTuResult result = YouTuResult.formatToPojo(json, TbItem.class);
				if(result.getStatus() == 200){//响应结果正确，取出数据
					TbItem item = (TbItem) result.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 调用rest服务，取得商品描述html片段，返回字符串
	 */
	@Override
	public String getItemDescById(long itemId) {
		try {
			//调用rest服务，参数跟在url后面
			String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
			//判断是否为空
			if(!StringUtils.isBlank(json)){
				//将json转换成pojo对象
				YouTuResult result = YouTuResult.formatToPojo(json, TbItemDesc.class);
				if(result.getStatus() == 200){//响应结果正确，取出数据
					TbItemDesc itemDesc = (TbItemDesc) result.getData();
					return itemDesc.getItemDesc();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得规格参数html片段
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getItemParamItem(long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
			//把json转换成java对象
			YouTuResult taotaoResult = YouTuResult.formatToPojo(json, TbItemParamItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
				String paramData = itemParamItem.getParamData();
				//生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html片段
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
