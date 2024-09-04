package com.global.order.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class UpdateOkOrderAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int order_no = Integer.parseInt(request.getParameter("order_no").trim());
		String status = request.getParameter("status");
		int user_no = Integer.parseInt(request.getParameter("user_no").trim());
		Date order_date = Daterequest.getParameter("order_date").trim();
		int total_amount = Integer.parseInt(request.getParameter("total_amount").trim());
		return null;
	}

}
