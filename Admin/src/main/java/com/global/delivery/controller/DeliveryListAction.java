package com.global.delivery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDTO;
import com.global.delivery.model.DeliveryDAO;
import com.global.delivery.model.DeliveryDTO;
import com.global.utils.PageInfo;

public class DeliveryListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int o;
		DeliveryDAO dao = DeliveryDAO.getInstance();
		
		List<DeliveryDTO> list = dao.getDeliveryList();
		

		
		String st = request.getParameter("st");
		
		int listCount; // 조건에 따른 회원 수

		// 현재 페이지
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		// 한 페이지에 보여질 게시글 최대 수
		int boardLimit = 10;

		// 페이지 하단에 보여질 페이지 수
		int pageLimit = 10;
		List<BoardDTO> boardList;
		if("N".equals(st)) {
			listCount = dao.getDeliveryCount(st);
			list = dao.selectDeliveryList(currentPage, boardLimit, 'N');
		}else if("Y".equals(st)) {
			listCount = dao.getDeliveryCount(st);
			list = dao.selectDeliveryList(currentPage, boardLimit, 'Y');
		}else {
			/* 필터를 적용하지 않고 검색*/
			listCount = dao.getDeliveryCount((String)null);
			list = dao.selectDeliveryList(currentPage, boardLimit);
		}
		
		
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
        request.setAttribute("List", list);
        request.setAttribute("address", "delivery_list.do"); // 페이지의 매핑을 던져줘야 함
        request.setAttribute("pi", pi);
		
		return new View("main.go").setUrl("/views/order/delivery_list.jsp");
	}

}
