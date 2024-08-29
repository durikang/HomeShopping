package com.global.board.model;

public class BoardCategoryDTO {
	
	private String categoryNo;
	private String name;
	private String description;
	
	public BoardCategoryDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public BoardCategoryDTO(String categoryNo, String name, String description) {
		super();
		this.categoryNo = categoryNo;
		this.name = name;
		this.description = description;
	}

	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
