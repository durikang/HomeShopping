package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;

public class ModifyProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String product_no = request.getParameter("no").trim();
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductDTO dto = dao.getProductContent(product_no);
		
		request.setAttribute("Modify", dto);
		
		return new View("main.go").setUrl("/views/product/productModify.jsp");
		
		
		
	}

}
