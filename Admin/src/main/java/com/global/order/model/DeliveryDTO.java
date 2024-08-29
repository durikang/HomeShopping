package com.global.order.model;

import java.sql.Date;

public class DeliveryDTO {
	private int delivery_no;
	private int order_no;
	private Date delivery_date;
	private String delivery_status;
	
	public int getDelivery_no() {
		return delivery_no;
	}
	public void setDelivery_no(int delivery_no) {
		this.delivery_no = delivery_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Date getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}


}
