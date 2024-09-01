package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;
import com.global.utils.ScriptUtil;

public class ProductModifyCategoryOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String category_no = request.getParameter("category_no").trim();
		
		String name = request.getParameter("name").trim();
		
		String description = request.getParameter("description");
		
		ProductCategoryDTO dto = new ProductCategoryDTO();
		
		dto.setCategory_No(category_no);
		dto.setName(name);
		dto.setDescription(description);
		
		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		int check = dao.ModifyCategory(dto);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "카테고리 수정 성공", "productCategory.do");
		} else {
			ScriptUtil.sendScript(response, "카테고리 수정 실패!!!", null);
		}
		
		return null;
	}

}
