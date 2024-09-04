package com.global.cartitem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.cart.model.CartDAO;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;

public class CartItemDeleteAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int No = Integer.parseInt(request.getParameter("no").trim());
		
		CartDAO dao = CartDAO.getInstanceCart();
		
		dao.deleteCart(No);
		
		return null;
	}

}
