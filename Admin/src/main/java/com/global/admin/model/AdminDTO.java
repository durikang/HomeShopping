package com.global.admin.model;

public class AdminDTO {
	
	private int user_no;
	private String user_id;
	private String password;
	private String role_name;
	private String email;
	private String role_code;
	
	public int getUser_no() {
		return user_no;
	}
	
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole_name() {
		return role_name;
	}
	
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole_code() {
		return role_code;
	}
	
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
}