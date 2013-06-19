package com.xiaojiujiu.entity;

/**
 * 优惠券实体类
 * 
 * @author beanu
 * 
 */
public class Coupon {
	// private int row;
	private int CouponID;// 唯一标示
	private String CouponTitle;
	private String CouponDesc;
	private String ParentShopName;
	private int RemainingDays;

	private String UseIntroduction;
	private String NearestShopName;
	private int NearestShopAddress;
	private String NearestShopDistance;
	private int IsFavorite;
	private int AttentionNum;
	private double NearestShopLng;
	private double NearestShopLat;
	private int FitShopNum;

	public int getCouponID() {
		return CouponID;
	}

	public void setCouponID(int couponID) {
		CouponID = couponID;
	}

	public String getCouponTitle() {
		return CouponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		CouponTitle = couponTitle;
	}

	public String getCouponDesc() {
		return CouponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		CouponDesc = couponDesc;
	}

	public String getParentShopName() {
		return ParentShopName;
	}

	public void setParentShopName(String parentShopName) {
		ParentShopName = parentShopName;
	}

	public int getRemainingDays() {
		return RemainingDays;
	}

	public void setRemainingDays(int remainingDays) {
		RemainingDays = remainingDays;
	}

	public String getUseIntroduction() {
		return UseIntroduction;
	}

	public void setUseIntroduction(String useIntroduction) {
		UseIntroduction = useIntroduction;
	}

	public String getNearestShopName() {
		return NearestShopName;
	}

	public void setNearestShopName(String nearestShopName) {
		NearestShopName = nearestShopName;
	}

	public int getNearestShopAddress() {
		return NearestShopAddress;
	}

	public void setNearestShopAddress(int nearestShopAddress) {
		NearestShopAddress = nearestShopAddress;
	}

	public String getNearestShopDistance() {
		return NearestShopDistance;
	}

	public void setNearestShopDistance(String nearestShopDistance) {
		NearestShopDistance = nearestShopDistance;
	}

	public int getIsFavorite() {
		return IsFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		IsFavorite = isFavorite;
	}

	public int getAttentionNum() {
		return AttentionNum;
	}

	public void setAttentionNum(int attentionNum) {
		AttentionNum = attentionNum;
	}

	public double getNearestShopLng() {
		return NearestShopLng;
	}

	public void setNearestShopLng(double nearestShopLng) {
		NearestShopLng = nearestShopLng;
	}

	public double getNearestShopLat() {
		return NearestShopLat;
	}

	public void setNearestShopLat(double nearestShopLat) {
		NearestShopLat = nearestShopLat;
	}

	public int getFitShopNum() {
		return FitShopNum;
	}

	public void setFitShopNum(int fitShopNum) {
		FitShopNum = fitShopNum;
	}

}
