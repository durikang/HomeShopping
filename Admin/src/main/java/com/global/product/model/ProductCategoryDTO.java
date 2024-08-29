package com.global.product.model;

public class ProductCategoryDTO {
    private String Category_No;
    private String name;
    private String description;

    public ProductCategoryDTO() {}

    public String getCategory_No() {
        return Category_No;
    }

    public void setCategory_No(String category_no) {
        this.Category_No = category_no;
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