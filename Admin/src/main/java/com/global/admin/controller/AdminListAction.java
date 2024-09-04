package com.global.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		AdminDAO dao = AdminDAO.getInstance();
		
		List<AdminDTO> list = dao.getAdminList();
		
		request.setAttribute("List", list);
		
		return new View("main.go").setUrl("/views/admin/admin_insert_list.jsp");
	}
}