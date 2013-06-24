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
	private String itemTitle;
	private String itemDetail;
	private List<String> couponTypeIconUrlList;
	private String itemAddress;
	private String itemImageUrl;
	private double distance;
	private int itemType;

	private String singleCouponTypeUrl;

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}

	public List<String> getCouponTypeIconUrlList() {
		return couponTypeIconUrlList;
	}

	public void setCouponTypeIconUrlList(List<String> couponTypeIconUrlList) {
		this.couponTypeIconUrlList = couponTypeIconUrlList;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getSingleCouponTypeUrl() {
		return singleCouponTypeUrl;
	}

	public void setSingleCouponTypeUrl(String singleCouponTypeUrl) {
		this.singleCouponTypeUrl = singleCouponTypeUrl;
	}

}
