package com.global.cart.model;

public class CartDTO {
	
	private int cart_no;				// 장바구니 번호
	private int cart_userNo;			// 장바구니 소유자 
	private String cart_createdAt;		// 장바구니 생성일
	
	public int getCart_no() {
		return cart_no;
	}
	
	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
	}
	
	public int getCart_userNo() {
		return cart_userNo;
	}
	
	public void setCart_userNo(int cart_userNo) {
		this.cart_userNo = cart_userNo;
	}
	
	public String getCart_createdAt() {
		return cart_createdAt;
	}
	
	public void setCart_createdAt(String cart_createdAt) {
		this.cart_createdAt = cart_createdAt;
	}
	
}
