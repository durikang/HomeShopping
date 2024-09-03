package com.global.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String admin_id = request.getParameter("id").trim();
		String admin_pwd = request.getParameter("pwd").trim();
		String admin_name = request.getParameter("name").trim();
		String admin_email = request.getParameter("email").trim();
		
		AdminDTO dto = new AdminDTO();
		
		dto.setUserId(admin_id);
		dto.setPassword(admin_pwd);
		dto.setName(admin_name);
		dto.setEmail(admin_email);
		
		AdminDAO dao = AdminDAO.getInstance();
		
		int check = dao.insertAdmin(dto);
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			out.println("<script>");
			out.println("alert('관리자 추가 성공')");
			out.println("location.href='select.do'");
			out.println("</script>");
		}
		
		else {
			out.println("<script>");
			out.println("alert('관리자 추가 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}
}