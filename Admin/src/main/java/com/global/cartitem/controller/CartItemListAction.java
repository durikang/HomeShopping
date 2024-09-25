package com.global.cartitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;

public class CartItemListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
		
		int No = Integer.parseInt(request.getParameter("cart_no").trim());
		
		CartItemDAO dao = CartItemDAO.getInstanceCartItem();	
		
		List<CartItemDTO> cartItemList = dao.getCartItemList(No);
		
		request.setAttribute("no", No);
		request.setAttribute("CartItemList", cartItemList);
		
		return new View("main.go").setUrl("/views/cartitem/cartitemList.jsp");
	}

}
