package com.global.cartitem.model;

import java.sql.Date;

public class CartItemDTO {
	
	private int cartItem_no;			// 장바구니 항목 번호
	private int cartItem_cartNo;		// 장바구니
	private int cartItem_productNo;		// 장바구니에 담긴 상품
	private int cartItem_quantity;		// 수량
	private Date cartItem_addedAt;		// 항목 추가일
	private Date cartItem_updatedAt;	// 항목 수정일
	
	public int getCartItem_no() {
		return cartItem_no;
	}
	
	public void setCartItem_no(int cartItem_no) {
		this.cartItem_no = cartItem_no;
	}
	
	public int getCartItem_cartNo() {
		return cartItem_cartNo;
	}
	
	public void setCartItem_cartNo(int cartItem_cartNo) {
		this.cartItem_cartNo = cartItem_cartNo;
	}
	
	public int getCartItem_productNo() {
		return cartItem_productNo;
	}
	
	public void setCartItem_productNo(int cartItem_productNo) {
		this.cartItem_productNo = cartItem_productNo;
	}
	
	public int getCartItem_quantity() {
		return cartItem_quantity;
	}
	
	public void setCartItem_quantity(int cartItem_quantity) {
		this.cartItem_quantity = cartItem_quantity;
	}
	
	public Date getCartItem_addedAt() {
		return cartItem_addedAt;
	}
	
	public void setCartItem_addedAt(Date cartItem_addedAt) {
		this.cartItem_addedAt = cartItem_addedAt;
	}
	
	public Date getCartItem_updatedAt() {
		return cartItem_updatedAt;
	}
	
	public void setCartItem_updatedAt(Date cartItem_updatedAt) {
		this.cartItem_updatedAt = cartItem_updatedAt;
	}
	
}
