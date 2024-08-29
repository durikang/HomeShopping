package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;
import com.global.utils.PageInfo;
import com.global.utils.PageUtils;

public class ProductListCategoryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		int listCount = dao.getProductCategoryListCount();
        int currentPage = 1; // 기본적으로 페이지는 1로 설정
        int boardLimit = 10; // 한 페이지에 보여질 게시글 최대 수
        int pageLimit = 10; // 한 페이지 하단에 보여질 페이지 수
        
        // 현재 페이지
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
		
        PageInfo pi = PageUtils.getPageInfo(currentPage, listCount, boardLimit, pageLimit);
        
		List<ProductCategoryDTO> list = dao.getCategoryList();
		
		int count = dao.getProductCategoryListCount();
		
		request.setAttribute("List", list);
		
		
		return new View("main.go").setUrl("/views/product/product_category.jsp");
	}	

}
