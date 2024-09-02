package com.global.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;

public class ListOrderAction implements Action{
	
	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		OrderDAO dao = OrderDAO.getInstance();
		
		List<OrderDTO> list = dao.getOrderList(); 
		
		request.setAttribute("List", list);
		
		return new View("main.go").setUrl("/views/order/order_list.jsp");
	}
	
	
	

}
