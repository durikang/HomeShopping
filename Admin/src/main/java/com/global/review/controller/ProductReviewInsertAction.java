package com.global.review.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;


public class ProductReviewInsertAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		List<ProductCategoryDTO> list = dao.getCategoryList();
		
		
		request.setAttribute("list", list);
		
		return new View("main.go").setUrl("/views/review/product_review_insert.jsp");
	}
	
	

}
