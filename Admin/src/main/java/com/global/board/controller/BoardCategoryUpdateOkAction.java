package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.utils.ScriptUtil;

public class BoardCategoryUpdateOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
		/*
		int userNo;
		if(request.getParameter("userNo") != null) {
			userNo = Integer.parseInt(request.getParameter("userNo"));
			board.setUserNo(userNo);
		}
		*/
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
    	
		String categoryNo = request.getParameter("categoryNo").trim();
		String categoryTitle = request.getParameter("name").trim();
		String description = request.getParameter("description").trim();
		
		BoardCategoryDTO category = new BoardCategoryDTO();
		category.setCategoryNo(categoryNo);
		category.setName(categoryTitle);
		category.setDescription(description);
		
		int check = BoardDAO.getInstance().updateCategory(category);
		
		request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
		
		if(check>0) {
			ScriptUtil.sendScript(response, "카테고리 변경 성공!", "boardCategoryList.do");
		}else {
			ScriptUtil.sendScript(response, "카테고리 변경 실패!", null);
		}
		
		
		return null;
	}

}
