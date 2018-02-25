package com.youtu.portal.service;
/**
 *@author:王贤锐
 *@date:2018年1月21日  下午8:16:09
**/

import com.youtu.pojo.TbItem;
import com.youtu.portal.pojo.SearchResult;

public interface ItemService {
	SearchResult searchItems(String queryString,int page);
	
	TbItem getItemBaseInfo(long itemId);
	
	String getItemDescById(long itemId);
	
	String getItemParamItem(long itemId);
}
