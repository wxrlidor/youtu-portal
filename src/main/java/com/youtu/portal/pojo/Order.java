package com.youtu.portal.pojo;

import java.util.List;

import com.youtu.pojo.TbOrder;
import com.youtu.pojo.TbOrderItem;
import com.youtu.pojo.TbOrderShipping;

/**
 *@author:王贤锐
 *@date:2018年1月26日  下午7:48:45
**/
public class Order extends TbOrder{
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
}
