package com.global.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.exception.BoardException;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardReplyDTO;

public class BoardDetailFormAction2 implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * 테이블의 구조를 보면
		 * 상위에 USERS라는 테이블과 
		 * 자식 테이브로 ADMIN 테이블, CUSTOMER테이블이 존재한다.
		 * 그러므로 선택해서 들어온 게시글의 유저 타입 userType이 ADMIN인지 CUSTOMER인지 구분하여 
		 * BOARD 테이블, BOARD_CATEGORY테이블 그리고 userType에 따라 ADMIN 테이블 혹은 CUSTOMER테이블을 JOIN하여 갖고온다.
		 * */
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String userType = request.getParameter("userType");
		
		/*
			status와 currenetPage는 처음에 넘어오는 값이 없을 수도 있다.
			맨 처음 List페이지에 접속했을때. 그러므로 3항 연산자를 이용하여 
			null값일 경우 처리를 해줬다.
		*/
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
			// boardNo 쿠키가 없는 경우
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
    	
        // 최신 댓글 리스트를 가져와서 리턴
        List<BoardReplyDTO> comments = dao.getCommentsByBoardNo(boardNo);
        request.setAttribute("comments", comments);
		
		
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	request.setAttribute("replyUrl", "/views/board/replyListForm.jsp");
    	request.setAttribute("info", board);
			
		return new View("main.go").setUrl("/views/board/boardDetailForm.jsp");
	}

}
