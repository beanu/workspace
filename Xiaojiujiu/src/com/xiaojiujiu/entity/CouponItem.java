package com.xiaojiujiu.entity;

import java.util.List;

/**
 * 优惠券列表Item信息
 * 
 * @author beanu
 * 
 */
public class CouponItem {
	
	private int itemID;// 唯一标示
	private String ItemTitle;
	private String ItemDetail;
	private List<String> CouponTypeIconUrlList;
	private String ItemAddress;
	private String ItemImageUrl;
	private double Distance;
	private int ItemType;
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemTitle() {
		return ItemTitle;
	}
	public void setItemTitle(String itemTitle) {
		ItemTitle = itemTitle;
	}
	public String getItemDetail() {
		return ItemDetail;
	}
	public void setItemDetail(String itemDetail) {
		ItemDetail = itemDetail;
	}
	public List<String> getCouponTypeIconUrlList() {
		return CouponTypeIconUrlList;
	}
	public void setCouponTypeIconUrlList(List<String> couponTypeIconUrlList) {
		CouponTypeIconUrlList = couponTypeIconUrlList;
	}
	public String getItemAddress() {
		return ItemAddress;
	}
	public void setItemAddress(String itemAddress) {
		ItemAddress = itemAddress;
	}
	public String getItemImageUrl() {
		return ItemImageUrl;
	}
	public void setItemImageUrl(String itemImageUrl) {
		ItemImageUrl = itemImageUrl;
	}
	public double getDistance() {
		return Distance;
	}
	public void setDistance(double distance) {
		Distance = distance;
	}
	public int getItemType() {
		return ItemType;
	}
	public void setItemType(int itemType) {
		ItemType = itemType;
	}

}
