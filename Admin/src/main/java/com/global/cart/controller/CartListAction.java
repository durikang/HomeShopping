package com.global.cart.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.board.model.BoardDTO;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.utils.PageInfo;
import com.global.utils.ScriptUtil;

public class CartListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
		CartDAO dao = CartDAO.getInstanceCart();
		
		String subtitle = request.getParameter("subtitle");
		
		int listCount=0; // 조건에 따른 회원 수
		// 현재 페이지
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// 한 페이지에 보여질 장바구니 최대수
		int cartLimit = 30;
		
		// 페이지 하단에 보여질 페이지 수
		int pageLimt = 10;
		
		// 전체 페이지 수 계산
		int maxPage = (int)Math.ceil((double)listCount/cartLimit);
		
		// 시작 페이지 계산 
		int startPage = (currentPage -1)/ pageLimt * pageLimt +1;
		
		// 끝 페이지 계산
		int endPage = startPage + pageLimt -1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		//PageInfo 객체 생성 및 설정
		PageInfo pi = new PageInfo(currentPage, listCount, pageLimt, maxPage, startPage, endPage, cartLimit);
		
		List<CartDTO> cartList = dao.getCartList();
		
		request.setAttribute("CartList", cartList);
		request.setAttribute("count",pi.getListCount());
		request.setAttribute("address","cart_list.do");
		request.setAttribute("pi", pi);
		request.setAttribute("subtitle", subtitle);
		
		return new View("main.go").setUrl("/views/cart/cartList.jsp");
	}

}
