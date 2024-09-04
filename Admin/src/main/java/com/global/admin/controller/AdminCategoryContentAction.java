package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminCategoryContentAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String admin_rolecode = request.getParameter("role_code").trim();
		String admin_rolename = request.getParameter("role_name").trim();
		
		AdminDAO dao = AdminDAO.getInstance();
		
		AdminDTO content = dao.contentAdminCategory(admin_rolecode, admin_rolename);
		
		request.setAttribute("Cont", content);
		
		return null;
	}
}
