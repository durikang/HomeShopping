package com.global.cart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.utils.ScriptUtil;

public class CartInsertAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		int user_no = Integer.parseInt(request.getParameter("user_no").trim());
		int product_no = Integer.parseInt(request.getParameter("no").trim());
		
		CartDAO dao = CartDAO.getInstanceCart();
		CartDTO dto = new CartDTO();
		
		int check = dao.insertCart(dto);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "상품 찜하기 성공", "cartItem_list.do?user_no="+user_no);
        }  
        
        else {
        	ScriptUtil.sendScript(response, "상품 찜하기 실패", null);
        }
		
		return null;
	}

}
