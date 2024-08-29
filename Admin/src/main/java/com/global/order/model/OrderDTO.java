package com.global.order.model;


import java.sql.Date;

public class OrderDTO {
	private int order_no;
	private int user_no;
	private Date order_date;
	private String status;
	private int total_amount;

public class OrderDTO {

	private int order_no;			// 주문 번호
	private int order_userNO;		// 주문한 고객
	private String order_date;		// 주문 일자
	private String order_status;	// 주문 상태
	private int order_totalAmount;	// 총 주문 금액

	
	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	
	
=======
	
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	
	public int getOrder_userNO() {
		return order_userNO;
	}
	
	public void setOrder_userNO(int order_userNO) {
		this.order_userNO = order_userNO;
	}
	
	public String getOrder_date() {
		return order_date;
	}
	
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	public String getOrder_status() {
		return order_status;
	}
	
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
	public int getOrder_totalAmount() {
		return order_totalAmount;
	}
	
	public void setOrder_totalAmount(int order_totalAmount) {
		this.order_totalAmount = order_totalAmount;
	}


}
