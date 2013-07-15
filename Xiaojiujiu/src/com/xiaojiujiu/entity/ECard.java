package com.xiaojiujiu.entity;

import net.tsz.afinal.annotation.sqlite.Id;

public class ECard {

	@Id
	int itemID;
	String itemTitle;
	int jiFen;
	String cardGrade;
	String awardInfo;
	String expirationTime;
	String ruleStr;
	String itemImageUrl;
	String eCardDesc;
	String memberRight;
	String eCardDetailDescUrl;
	int isFavorite;
	String nearestShopName;
	String nearestShopAddress;
	double nearestShopDistance;
	double nearestShopLng;
	double nearestShopLat;
	String nearestShopTel;
	int fitShopNum;
	String eCardType;

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

	public int getJiFen() {
		return jiFen;
	}

	public void setJiFen(int jiFen) {
		this.jiFen = jiFen;
	}

	public String getCardGrade() {
		return cardGrade;
	}

	public void setCardGrade(String cardGrade) {
		this.cardGrade = cardGrade;
	}

	public String getAwardInfo() {
		return awardInfo;
	}

	public void setAwardInfo(String awardInfo) {
		this.awardInfo = awardInfo;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getRuleStr() {
		return ruleStr;
	}

	public void setRuleStr(String ruleStr) {
		this.ruleStr = ruleStr;
	}

	public String getItemImageUrl() {
		return itemImageUrl;
	}

	public void setItemImageUrl(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public String geteCardDesc() {
		return eCardDesc;
	}

	public void seteCardDesc(String eCardDesc) {
		this.eCardDesc = eCardDesc;
	}

	public String getMemberRight() {
		return memberRight;
	}

	public void setMemberRight(String memberRight) {
		this.memberRight = memberRight;
	}

	public String geteCardDetailDescUrl() {
		return eCardDetailDescUrl;
	}

	public void seteCardDetailDescUrl(String eCardDetailDescUrl) {
		this.eCardDetailDescUrl = eCardDetailDescUrl;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
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

	public String geteCardType() {
		return eCardType;
	}

	public void seteCardType(String eCardType) {
		this.eCardType = eCardType;
	}

}
