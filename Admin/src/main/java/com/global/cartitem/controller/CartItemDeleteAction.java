package com.global.cartitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;
import com.global.utils.ScriptUtil;

public class CartItemDeleteAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
        int no = Integer.parseInt(request.getParameter("no").trim());
        CartItemDAO dao = CartItemDAO.getInstanceCartItem();
        dao.deleteCartItem(no);

       
		
        return new View("main.go").setUrl("/views/cart/cartList.jsp");
	}

}
