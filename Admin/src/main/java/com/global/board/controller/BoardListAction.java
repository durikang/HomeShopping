package com.global.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.utils.PageInfo;

public class BoardListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDAO dao = BoardDAO.getInstance();
		
		String status = request.getParameter("status");
		String subtitle = request.getParameter("subtitle");
		
		int listCount; // 조건에 따른 회원 수

		// 현재 페이지
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		// 한 페이지에 보여질 게시글 최대 수
		int boardLimit = 100;

		// 페이지 하단에 보여질 페이지 수
		int pageLimit = 10;
		List<BoardDTO> boardList;
		if("N".equals(status)) {
			listCount = dao.getBoardCount(status);
			boardList = dao.selectBoardList(currentPage, boardLimit, 'N');
		}else if("Y".equals(status)) {
			listCount = dao.getBoardCount(status);
			boardList = dao.selectBoardList(currentPage, boardLimit, 'Y');
		}else {
			/* 필터를 적용하지 않고 검색*/
			listCount = dao.getBoardCount((String)null);
			boardList = dao.selectBoardList(currentPage, boardLimit);
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
        
        // 필터용 BoardCategory List
		List<BoardCategoryDTO> categoryList = dao.selectBoardCategoryList();
		
        // request에 필요한 속성 설정
		request.setAttribute("categoryList", categoryList);
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("list", boardList);
        request.setAttribute("pi", pi);
        request.setAttribute("status", status); // 필터 상태를 JSP로 전달
        request.setAttribute("subtitle", subtitle); // 메뉴바에서 누른 게시글 현 위치를 보냄

		return new View("main.go").setUrl("/views/board/boardList.jsp");
	}

}
