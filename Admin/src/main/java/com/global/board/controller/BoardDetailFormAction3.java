package com.global.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFileUploadDTO;
import com.global.board.model.BoardReplyDTO;
import com.global.utils.PageInfo;
import com.global.utils.RequestParameterUtils;

public class BoardDetailFormAction3 implements Action {

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
			
			--> RequestParameterUtils라는 유틸 클래스를 추가하였다.
				아래와 같이 간단히 표현이 가능하다.
         */
        String status = RequestParameterUtils.parseString(request.getParameter("status"), "");
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"),1);
        
        String subtitle = request.getParameter("subtitle");
        
        
        BoardDAO dao = BoardDAO.getInstance();
        BoardDTO board = null;
        boolean flag = false;

        // 쿠키로 조회수 증가 로직
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("boardNo" + boardNo)) {
                    flag = true;
                }
            }
            if (!flag) {
                int res = dao.increaseViews(boardNo);
                if (res > 0) {
                    board = dao.selectBoard(boardNo, userType);
                    Cookie c1 = new Cookie("boardNo" + boardNo, String.valueOf(boardNo));
                    c1.setMaxAge(1 * 24 * 60 * 60);
                    response.addCookie(c1);
                }
            } else {
                board = dao.selectBoard(boardNo, userType);
            }
        }
        
        
        

     // PageInfo 객체 생성 및 설정
        PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
        
        
        // 파일 목록 가져오기
        List<BoardFileUploadDTO> files = dao.getFilesByBoardNo(boardNo);

        // 댓글 목록 가져오기
        List<BoardReplyDTO> comments = dao.getCommentsByBoardNo(boardNo);
        request.setAttribute("comments", comments);
        request.setAttribute("files", files);  // JSP에 파일 리스트 전달
        request.setAttribute("status", status);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("info", board);
        
        
        request.setAttribute("address", "boardList.do"); // 페이지의 매핑을 던져줘야 함
        
        request.setAttribute("list", boardList);
        request.setAttribute("pi", pi);
 
        request.setAttribute("subtitle", subtitle); // 메뉴바에서 누른 게시글 현 위치를 보냄
        

        return new View("main.go").setUrl("/views/board/boardDetailForm.jsp");
    }
}
