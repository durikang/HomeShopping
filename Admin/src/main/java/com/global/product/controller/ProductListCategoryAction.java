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
		
		
		List<ProductCategoryDTO> list = dao.getCategoryList();
		
		request.setAttribute("List", list);
		
		int listCount;
        int currentPage = 1; // 기본적으로 페이지는 1로 설정
        if (request.getParameter("currentPage") != null) {
        	currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        int boardLimit = 10; // 한 페이지에 보여질 게시글 최대 수
        
        int pageLimit = 10; // 한 페이지 하단에 보여질 페이지 수
        
		
		listCount = dao.getProductCategoryListCount();
        list = dao.getProductCategoryPage(currentPage, boardLimit);
		
        // 전체 페이지 수 계산
        int maxPage = (int) Math.ceil((double) listCount / boardLimit);

        // 시작 페이지 계산
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        
        // 끝 페이지 계산
        int endPage = startPage + pageLimit - 1;
        if (maxPage < endPage) {
            endPage = maxPage;
        }
        
        // PageInfo 객체 생성 및 설정
        PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
        
        // request에 필요한 속성 설정
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("list", list);
        request.setAttribute("pi", pi);
		
		
		return new View("main.go").setUrl("/views/product/product_category.jsp");
	}	

}
