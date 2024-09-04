package com.global.review.model;

import java.sql.Date;

public class ProductReviewDTO {
	private int Review_No;
	private int Board_No;
	private int Product_No;
	private int User_No;
	private int Rating;
	private String Comm;
	private Date Created_At;
	private Date Updated_At;
	private String Is_Deleted;
	
	/*상품 정보 조인용*/
	private String category_no;
	private String name;
	private String description;
	private int price;
	private int stock_quantity;
	private int views;
	
	/*상품 조인용*/
	private String title;
	private String content;
	
	public int getReview_No() {
		return Review_No;
	}
	
	public void setReview_No(int review_No) {
		Review_No = review_No;
	}
	
	public int getBoard_No() {
		return Board_No;
	}
	
	public void setBoard_No(int board_No) {
		Board_No = board_No;
	}
	
	public int getProduct_No() {
		return Product_No;
	}
	
	public void setProduct_No(int product_No) {
		Product_No = product_No;
	}
	
	public int getUser_No() {
		return User_No;
	}
	
	public void setUser_No(int user_No) {
		User_No = user_No;
	}
	
	public int getRating() {
		return Rating;
	}
	
	public void setRating(int rating) {
		Rating = rating;
	}
	
	public String getComm() {
		return Comm;
	}
	
	public void setComm(String comm) {
		Comm = comm;
	}
	
	public Date getCreated_At() {
		return Created_At;
	}
	
	public void setCreated_At(Date created_At) {
		Created_At = created_At;
	}
	
	public Date getUpdated_At() {
		return Updated_At;
	}
	
	public void setUpdated_At(Date updated_At) {
		Updated_At = updated_At;
	}
	
	public String getIs_Deleted() {
		return Is_Deleted;
	}
	
	public void setIs_Deleted(String is_Deleted) {
		Is_Deleted = is_Deleted;
	}
	
	public String getCategory_no() {
		return category_no;
	}
	
	public void setCategory_no(String category_no) {
		this.category_no = category_no;
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
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getStock_quantity() {
		return stock_quantity;
	}
	
	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	
	public int getViews() {
		return views;
	}
	
	public void setViews(int views) {
		this.views = views;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
