package com.xiaojiujiu.entity;


/**
 * 优惠券实体类
 * 
 * @author beanu
 * 
 */
public class Coupon {
//	private int row;
	private int couponID;//唯一标示
	private String couponTitle;
	private String couponDesc;
	private String viewTimes;
	private String updateTime;
	private String bigImageUrl;
	private String smallImageUrl;
	private int parentShopID;
	private String parentShopName;
	private int shopID;
	private String shopName;
	private float distance;
	private String shopAddress;
	private String shopTel;
	private double shopLongitude;
	private double shopLatitude;
	private String shopNum;

//	public int getRow() {
//		return row;
//	}
//
//	public void setRow(int row) {
//		this.row = row;
//	}

	public int getCouponID() {
		return couponID;
	}

	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	public String getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(String viewTimes) {
		this.viewTimes = viewTimes;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public int getParentShopID() {
		return parentShopID;
	}

	public void setParentShopID(int parentShopID) {
		this.parentShopID = parentShopID;
	}

	public String getParentShopName() {
		return parentShopName;
	}

	public void setParentShopName(String parentShopName) {
		this.parentShopName = parentShopName;
	}

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopTel() {
		return shopTel;
	}

	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}

	public double getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	public double getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public String getShopNum() {
		return shopNum;
	}

	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}

}
