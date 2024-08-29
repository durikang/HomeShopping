package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDTO;

@WebServlet("/insert_ok.go")
public class AdminInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String auser_no = request.getParameter("user_no").trim();
		String auser_id = request.getParameter("user_id").trim();
		String auser_password = request.getParameter("password").trim();
		String auser_name = request.getParameter("role_name").trim();
		String auser_email = request.getParameter("email").trim();
		String auser_code = request.getParameter("role_code").trim();
		
		AdminDTO dto = new AdminDTO();
		
		/*
		 * dto.setUser_no(auser_no);
		dto.setUser_no(user_id);
		dto.setUser_no(password);
		dto.setUser_no(role_name);
		dto.setUser_no(email);
		dto.setUser_no(role_code);
		 */
		
		return null;
	}
}