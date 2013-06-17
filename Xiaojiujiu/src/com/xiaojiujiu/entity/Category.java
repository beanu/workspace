package com.xiaojiujiu.entity;

import java.util.List;

/**
 * 分类信息，二级分类
 * @author beanu
 *
 */
public class Category {

	private int CategoryID;
	private String CategoryName;
	private String Letter;
	private List<Category> ChildCategoryList;

	public int getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
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
