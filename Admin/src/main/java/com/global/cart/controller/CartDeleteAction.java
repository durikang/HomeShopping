package com.global.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.utils.ScriptUtil;

public class CartDeleteAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
        
		int No = Integer.parseInt(request.getParameter("no").trim());
		
		CartDAO dao = CartDAO.getInstanceCart();
		
		
		dao.deleteCart(No);
		
		dao.updateSequenceCart(No);
		
		List<CartDTO> cartList = dao.getCartList();
		
		request.setAttribute("CartList", cartList);	
		return new View("main.go").setUrl("/views/cart/cartList.jsp");
	}

}
