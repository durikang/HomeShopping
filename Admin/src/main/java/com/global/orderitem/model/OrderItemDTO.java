package com.global.orderitem.model;

public class OrderItemDTO {
	private int orderItem_no;			// 주문 항복 번호
	private int orderItem_orderNo;		// 주문
	private int orderItem_productNo;	// 수량
	private int orderItem_price;		// 가격
	
	public int getOrderItem_no() {
		return orderItem_no;
	}
	
	public void setOrderItem_no(int orderItem_no) {
		this.orderItem_no = orderItem_no;
	}
	
	public int getOrderItem_orderNo() {
		return orderItem_orderNo;
	}
	
	public void setOrderItem_orderNo(int orderItem_orderNo) {
		this.orderItem_orderNo = orderItem_orderNo;
	}
	
	public int getOrderItem_productNo() {
		return orderItem_productNo;
	}
	
	public void setOrderItem_productNo(int orderItem_productNo) {
		this.orderItem_productNo = orderItem_productNo;
	}
	
	public int getOrderItem_price() {
		return orderItem_price;
	}
	
	public void setOrderItem_price(int orderItem_price) {
		this.orderItem_price = orderItem_price;
	}
	
	
}
