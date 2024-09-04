package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;

public class ContentProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String product_no = request.getParameter("no").trim(); 
		
		ProductDAO dao = ProductDAO.getInstance();
		
		// 조회하는 메서드 호출
		ProductDTO cont = dao.getProductContent(product_no);
		
		request.setAttribute("Content", cont);
		
		
		
		
		return new View("main.go").setUrl("/views/product/product_content.jsp");

		
	}

}
