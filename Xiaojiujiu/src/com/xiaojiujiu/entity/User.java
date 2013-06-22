package com.xiaojiujiu.entity;

/**
 * 用户信息实体类
 * 
 * @author beanu
 * 
 */
public class User {

	int MemberID;
	String UserName;
	String Password;
	String NickName;
	String Tel;
	int JiFen;
	int UserType;
	String RegTime;
	int FreezeFlag;
	String MemberName;
	String Sex;
	String Birthday;
	String Email;
	String City;
	String Address;
	String UserImageBig;
	String UserImageSmall;
	int favShopNum;
	int favCouponNum;
	int msgNum;

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int memberID) {
		MemberID = memberID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public int getJiFen() {
		return JiFen;
	}

	public void setJiFen(int jiFen) {
		JiFen = jiFen;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
	}

	public String getRegTime() {
		return RegTime;
	}

	public void setRegTime(String regTime) {
		RegTime = regTime;
	}

	public int getFreezeFlag() {
		return FreezeFlag;
	}

	public void setFreezeFlag(int freezeFlag) {
		FreezeFlag = freezeFlag;
	}

	public String getMemberName() {
		return MemberName;
	}

	public void setMemberName(String memberName) {
		MemberName = memberName;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getUserImageBig() {
		return UserImageBig;
	}

	public void setUserImageBig(String userImageBig) {
		UserImageBig = userImageBig;
	}

	public String getUserImageSmall() {
		return UserImageSmall;
	}

	public void setUserImageSmall(String userImageSmall) {
		UserImageSmall = userImageSmall;
	}

	public int getFavShopNum() {
		return favShopNum;
	}

	public void setFavShopNum(int favShopNum) {
		this.favShopNum = favShopNum;
	}

	public int getFavCouponNum() {
		return favCouponNum;
	}

	public void setFavCouponNum(int favCouponNum) {
		this.favCouponNum = favCouponNum;
	}

	public int getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}

}
