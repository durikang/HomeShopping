package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.utils.PageInfo;

public class ContentProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProductDAO dao = ProductDAO.getInstance();
				
		String product_no = request.getParameter("no").trim(); 
		
		String userType = request.getParameter("userType");

		String status = request.getParameter("status");
		
		int listCount;
		
        int currentPage = 1; // 기본적으로 페이지는 1로 설정
        if (request.getParameter("currentPage") != null) {
        	currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        int boardLimit = 10; // 한 페이지에 보여질 게시글 최대 수
        
        int pageLimit = 10; // 한 페이지 하단에 보여질 페이지 수
        
		List<ProductDTO> list;
		
		if("N".equals(status)) {
			listCount = dao.getProductCount('N');
			list = dao.selectProductList(currentPage, boardLimit, 'N');
		}else if("Y".equals(status)) {
			listCount = dao.getProductCount('Y');
			list = dao.selectProductList(currentPage, boardLimit, 'Y');
		}else {
		listCount = dao.getProductListCount();
        list = dao.getProduct(currentPage, boardLimit);
		}
		
		
		// 조회하는 메서드 호출
		ProductDTO product = dao.getProductContent(product_no);

//		조회수 증가 로직은 프론트에서만
//		boolean flag = false; 
//		
//        // 조회수 증가 로직
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                if (c.getName().equals("no" + product_no)) {
//                    flag = true;
//                }
//            }
//            if (!flag) {
//                int res = dao.increaseViews(product_no);
//                if (res > 0) {
//                	product = dao.getProductContent(product_no);
////                	product = dao.selectProduct(product_no, userType);
//                    Cookie c1 = new Cookie("no" + product_no, String.valueOf(product_no));
//                    c1.setMaxAge(1 * 24 * 60 * 60);
//                    response.addCookie(c1);
//                }
//            } else {
//            	product = dao.getProductContent(product_no);
////            	product = dao.selectProduct(product_no, userType); 이해 X
//            }
//        }
		
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
        request.setAttribute("count", pi.getCurrentPage());
		request.setAttribute("Content", product);
		
		
		
		
		return new View("main.go").setUrl("/views/product/product_content.jsp");

		
	}

}
