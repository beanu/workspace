package com.xiaojiujiu.entity;

import java.util.List;

/**
 * 分类信息，二级分类
 * @author beanu
 *
 */
public class Category {

	private int categoryID;
	private String categoryName;
	private String Letter;
	private List<Category> ChildCategoryList;

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLetter() {
		return Letter;
	}

	public void setLetter(String letter) {
		Letter = letter;
	}

	public List<Category> getChildCategoryList() {
		return ChildCategoryList;
	}

	public void setChildCategoryList(List<Category> childCategoryList) {
		ChildCategoryList = childCategoryList;
	}

}
