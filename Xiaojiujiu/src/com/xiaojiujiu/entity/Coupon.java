package com.xiaojiujiu.entity;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * 优惠券实体类
 * 
 * @author beanu
 * 
 */
public class Coupon {

	@Id
	private int id;
	
	private int couponID;// 唯一标示
	private String couponTitle;
	private String couponDesc;
	private String parentShopName;
	private int remainingDays;

	private String useIntroduction;
	private String nearestShopName;
	private String nearestShopAddress;
	private double nearestShopDistance;
	private int isFavorite;
	private int attentionNum;
	private double nearestShopLng;
	private double nearestShopLat;
	private String nearestShopTel;
	private int fitShopNum;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getParentShopName() {
		return parentShopName;
	}

	public void setParentShopName(String parentShopName) {
		this.parentShopName = parentShopName;
	}

	public int getRemainingDays() {
		return remainingDays;
	}

	public void setRemainingDays(int remainingDays) {
		this.remainingDays = remainingDays;
	}

	public String getUseIntroduction() {
		return useIntroduction;
	}

	public void setUseIntroduction(String useIntroduction) {
		this.useIntroduction = useIntroduction;
	}

	public String getNearestShopName() {
		return nearestShopName;
	}

	public void setNearestShopName(String nearestShopName) {
		this.nearestShopName = nearestShopName;
	}

	public String getNearestShopAddress() {
		return nearestShopAddress;
	}

	public void setNearestShopAddress(String nearestShopAddress) {
		this.nearestShopAddress = nearestShopAddress;
	}

	public double getNearestShopDistance() {
		return nearestShopDistance;
	}

	public void setNearestShopDistance(double nearestShopDistance) {
		this.nearestShopDistance = nearestShopDistance;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public int getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(int attentionNum) {
		this.attentionNum = attentionNum;
	}

	public double getNearestShopLng() {
		return nearestShopLng;
	}

	public void setNearestShopLng(double nearestShopLng) {
		this.nearestShopLng = nearestShopLng;
	}

	public double getNearestShopLat() {
		return nearestShopLat;
	}

	public void setNearestShopLat(double nearestShopLat) {
		this.nearestShopLat = nearestShopLat;
	}

	public String getNearestShopTel() {
		return nearestShopTel;
	}

	public void setNearestShopTel(String nearestShopTel) {
		this.nearestShopTel = nearestShopTel;
	}

	public int getFitShopNum() {
		return fitShopNum;
	}

	public void setFitShopNum(int fitShopNum) {
		this.fitShopNum = fitShopNum;
	}

}
