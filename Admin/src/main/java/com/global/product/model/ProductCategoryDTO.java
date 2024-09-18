package com.global.product.model;

public class ProductCategoryDTO {
    private String category_no;
    private String name;
    private String description;
	
    public ProductCategoryDTO() {
		super();
	}
	public String getCategory_No() {
		return category_no;
	}
	public void setCategory_No(String category_No) {
		this.category_no = category_No;
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