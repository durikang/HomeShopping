package com.global.admin.model;

import java.sql.Date;

public class AdminDTO extends UsersDTO{
	
	private String roleCode;
    private String roleName;
    
    private String rolePwd;
    
	public AdminDTO() {
		super();
	}
	
	public AdminDTO(int userNo, String userId, String password, String name, String email, String userType,
			String isDeleted, Date createdAt, Date updatedAt,
			String roleCode, String roleName) {
		
		super(userNo, userId, password, name, email, userType, isDeleted, createdAt, updatedAt);
		
		this.roleCode = roleCode;
        this.roleName = roleName;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRolePwd() {
		return rolePwd;
	}

	public void setRolePwd(String rolePwd) {
		this.rolePwd = rolePwd;
	}
}