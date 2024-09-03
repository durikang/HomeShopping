package com.global.cart.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDTO;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.utils.PageInfo;

public class CartListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CartDAO dao = CartDAO.getInstanceCart();
		
		List<CartDTO> cartList = dao.getCartList();
		
		request.setAttribute("CartList", cartList);
		
		return new View("main.go").setUrl("/views/cart/cartList.jsp");
	}

}
