package com.global.product.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;

public class ProductInsertCategoryAction implements Action {

	
		@Override
		public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			String no = request.getParameter("cateNo").trim();
			String name = request.getParameter("cateName").trim();
			String description = request.getParameter("cateInfo").trim();
			
			ProductCategoryDTO dto = new ProductCategoryDTO();
			
			dto.setCategory_No(no);
			dto.setName(name);
			dto.setDescription(description);
			
			ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
			
			int check = dao.insertCategory(dto);
					
			PrintWriter out = response.getWriter();
			
			if(check > 0) {
				out.println("<script>");
				out.println("alert('카테고리 추가 성공')");
				out.println("location.href=''");
				out.println("</script>");
			}else {
				out.println("<script>");
				out.println("alert('카테고리  추가 실패')");
				out.println("history.back()");
				out.println("</script>");
				
			}
			
			
			
			
			return null;
		}
}
