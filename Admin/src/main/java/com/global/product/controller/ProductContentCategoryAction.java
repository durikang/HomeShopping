package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;

public class ProductContentCategoryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String category_no = request.getParameter("no").trim(); 
		
		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		// 조회하는 메서드 호출
		ProductCategoryDTO cont = dao.getCategoryContent(category_no);
		
		request.setAttribute("Content", cont);
		
		
		
		
		return new View("main.go").setUrl("/views/product/productCategoryContent.jsp");
	}

}
