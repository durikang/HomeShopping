package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.exception.BoardException;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;

public class BoardDetailFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int boardNo = Integer.parseInt(request.getParameter("no"));
		String userType = request.getParameter("userType");
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
    	
    	BoardDAO dao = BoardDAO.getInstance();
    	
    	BoardDTO board = null; 
    	
    	boolean flag = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				// boardNo 쿠키가 있는 경우
				if(c.getName().equals("boardNo"+boardNo)) {
					flag = true;
				}
			}
			// bId 쿠키가 없는 경우
			if(!flag) {
				// 게시글을 처음 클릭했으므로 조회수 증가 + 셀렉
				
				int res = dao.increaseViews(boardNo);
				
		        try {
		            // 조회수가 0보다 크지 않다면 예외 발생
		            if(res <= 0) {
		                throw new BoardException("조회수 증가 실패: 게시글 번호 " + boardNo);
		            }

		            // 조회수 증가에 성공한 경우 게시글 정보를 가져옴
		            board = dao.selectBoard(boardNo, userType);

		        } catch (BoardException e) {
		            // 사용자 정의 예외를 처리하는 부분
		            e.printStackTrace();
		        }
				
				// 쿠키 객체 생성
				Cookie c1 = new Cookie("boardNo"+boardNo, String.valueOf(boardNo));
				// 하루동안 저장
				c1.setMaxAge(1 * 24 * 60 * 60);
				response.addCookie(c1);
			}else {
				// boardNo 쿠키가 있는 경우는 게시글을 하루 안에 다시 클릭하는 것이므로
				// 조회수 증가하지 않고 셀렉
				board = dao.selectBoard(boardNo,userType);
			}
		
		}
    	
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	
    	request.setAttribute("info", board);
			
		return new View("main.go").setUrl("/views/board/boardDetailForm.jsp");
	}

}
