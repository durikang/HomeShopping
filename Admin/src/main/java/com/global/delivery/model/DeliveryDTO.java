package com.global.delivery.model;

import java.sql.Date;

public class DeliveryDTO {
	
	private int delivery_no;			// 배송번호
	private int delivery_orderNo;		// 주문
	private Date delivery_date;		// 배송일
	private Date delivery_status;		// 배송 상태
	
	public int getDelivery_no() {
		return delivery_no;
	}
	
	public void setDelivery_no(int delivery_no) {
		this.delivery_no = delivery_no;
	}
	
	public int getDelivery_orderNo() {
		return delivery_orderNo;
	}
	
	public void setDelivery_orderNo(int delivery_orderNo) {
		this.delivery_orderNo = delivery_orderNo;
	}
	
	public Date getDelivery_date() {
		return delivery_date;
	}
	
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}
	
	public Date getDelivery_status() {
		return delivery_status;
	}
	
	public void setDelivery_status(Date delivery_status) {
		this.delivery_status = delivery_status;
	}

	
}
