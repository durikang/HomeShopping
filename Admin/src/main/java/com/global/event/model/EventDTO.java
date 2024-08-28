package com.global.event.model;

public class EventDTO {
	
	private int event_no;				// 이벤트 No
	private String event_name;			// 이벤트 이름
	private String event_description;	// 이벤트 설명
	private String event_startDate;		// 이벤트 시작일
	private String event_endDate;		// 이벤트 종료일
	private String event_createdAt;		// 이벤트 생성일
	private String event_updatedAt;		// 이벤트 수정일
	
	public int getEvent_no() {
		return event_no;
	}
	
	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}
	
	public String getEvent_name() {
		return event_name;
	}
	
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	
	public String getEvent_description() {
		return event_description;
	}
	
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	
	public String getEvent_startDate() {
		return event_startDate;
	}
	
	public void setEvent_startDate(String event_startDate) {
		this.event_startDate = event_startDate;
	}
	
	public String getEvent_endDate() {
		return event_endDate;
	}
	
	public void setEvent_endDate(String event_endDate) {
		this.event_endDate = event_endDate;
	}
	
	public String getEvent_createdAt() {
		return event_createdAt;
	}
	
	public void setEvent_createdAt(String event_createdAt) {
		this.event_createdAt = event_createdAt;
	}
	
	public String getEvent_updatedAt() {
		return event_updatedAt;
	}
	
	public void setEvent_updatedAt(String event_updatedAt) {
		this.event_updatedAt = event_updatedAt;
	}

}
