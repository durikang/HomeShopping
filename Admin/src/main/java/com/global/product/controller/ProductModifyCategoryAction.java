package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.ActionForward;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;

public class ProductModifyCategoryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String category_no = request.getParameter("no").trim(); 
				
		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		ProductCategoryDTO dto = dao.getCategoryContent(category_no);
		
		request.setAttribute("Modify", dto);
		
		return new View("main.go").setUrl("views/product/productCategoryModify.jsp");
	}

}
