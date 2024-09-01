package com.global.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardReplyDTO;
import com.global.utils.RequestParameterUtils;

public class BoardReplyInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BoardDAO dao = BoardDAO.getInstance();
        String status = request.getParameter("status"); 
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"));
        
        Integer boardNo = RequestParameterUtils.parseInteger(request.getParameter("boardNo")); 
        Integer userNo = RequestParameterUtils.parseInteger(request.getParameter("userNo"),4); // 임시 유저번호 4번(어드민)
        String content = RequestParameterUtils.parseString(request.getParameter("content"), "");
        Integer parentReplyNo = RequestParameterUtils.parseInteger(request.getParameter("parentReplyNo")); // 부모 댓글 번호

        BoardReplyDTO reply = new BoardReplyDTO();
        reply.setBoardNo(boardNo);
        reply.setUserNo(userNo);
        reply.setContent(content);

        if (parentReplyNo == null) {
            // 댓글
            dao.insertReply(reply);
        } else {
            // 대댓글
            reply.setParentReplyNo(parentReplyNo);
            dao.insertSubReply(reply);
        }
    	
        request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	request.setAttribute("replyUrl", "/views/board/replyListForm.jsp");
    	
    	
        return new View("main.go").setUrl("/views/board/boardDetailForm.jsp");
	}

}
