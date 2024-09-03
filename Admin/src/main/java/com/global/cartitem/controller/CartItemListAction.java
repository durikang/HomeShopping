package com.global.cartitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;

public class CartItemListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int no = Integer.parseInt(request.getParameter("no").trim());
		
		CartItemDAO dao = CartItemDAO.getInstanceCartItem();	
		
		List<CartItemDTO> cartItemList = dao.getCartItemList(no);
		
		request.setAttribute("CartItemList", cartItemList);
		
		return new View("main.go").setUrl("/views/cartitem/cartitemList.jsp");
	}

}
