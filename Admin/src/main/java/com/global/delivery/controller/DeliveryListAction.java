package com.global.delivery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.delivery.model.DeliveryDAO;
import com.global.delivery.model.DeliveryDTO;

public class DeliveryListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		DeliveryDAO dao = DeliveryDAO.getInstance();
		
		List<DeliveryDTO> list = dao.getDeliveryList();
		
		request.setAttribute("List", list);
		
		return new View("main.go").setUrl("/views/order/delivery_list.jsp");
	}

}
