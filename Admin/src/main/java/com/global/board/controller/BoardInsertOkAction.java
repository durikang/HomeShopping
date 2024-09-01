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
import com.global.utils.ScriptUtil;

public class BoardInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDTO board = new BoardDTO();

		/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
		
		Integer userNo = request.getParameter("userNo").equals("") ? null : Integer.parseInt(request.getParameter("userNo"));
		
		board.setUserNo(userNo);
		String categoryNo = request.getParameter("categoryNo");
		String title = request.getParameter("title").trim();
		String content = request.getParameter("content").trim();
		
		board.setCategoryNo(categoryNo);
		board.setTitle(title);
		board.setContent(content);
		
		
		int check = BoardDAO.getInstance().insertBoard(board,userNo);
		
		if(check>0) {
			ScriptUtil.sendScript(response, "게시글 작성 성공!", "boardList.do");
		}else {
			ScriptUtil.sendScript(response, "게시글 작성 실패!", null);
		}

		return null;
	}

}
