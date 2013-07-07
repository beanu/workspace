package com.xiaojiujiu.entity;

/**
 * 优惠券适用门店
 * 
 * @author beanu
 * 
 */
public class CouponShop {

	private int shopID;// 唯一标示
	private String shopName;
	private String shopAddress;
	double shopLongitude;
	double shopLatitude;
	double shopDistance;
	private String shopTel;

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

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
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

	public double getShopDistance() {
		return shopDistance;
	}

	public void setShopDistance(double shopDistance) {
		this.shopDistance = shopDistance;
	}

	public String getShopTel() {
		return shopTel;
	}

	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}

}
