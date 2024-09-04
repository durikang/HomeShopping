package com.global.review.model;

import java.sql.Date;

public class ProductReviewDTO {
	private int Review_No;
	private int Product_No;
	private int User_No;
	private int Rating;
	private String Comm;
	private Date Created_At;
	private Date Updated_At;
	private String Is_Deleted;
	
	private int categoryNo;
	

	public int getReview_No() {
		return Review_No;
	}
	
	public void setReview_No(int review_No) {
		Review_No = review_No;
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


}
