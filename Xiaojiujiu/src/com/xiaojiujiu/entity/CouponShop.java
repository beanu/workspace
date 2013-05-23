package com.xiaojiujiu.entity;

/**
 * 优惠券适用门店
 * 
 * @author beanu
 * 
 */
public class CouponShop {
	
	private int shopID;// 唯一标示
	private String shopTitle;
	private String shopAdress;
	private String shopTel;
	private String shopDistance;

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public String getShopAdress() {
		return shopAdress;
	}

	public void setShopAdress(String shopAdress) {
		this.shopAdress = shopAdress;
	}

	public String getShopTel() {
		return shopTel;
	}

	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}

	public String getShopDistance() {
		return shopDistance;
	}

	public void setShopDistance(String shopDistance) {
		this.shopDistance = shopDistance;
	}

}
