package com.global.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.utils.MyFileRenamePolicy;
import com.global.utils.ScriptUtil;
import com.oreilly.servlet.MultipartRequest;

public class BoardInsertOkAction2 implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	BoardDTO board = new BoardDTO();

        // 세션에서 UserDTO 객체를 가져옵니다.
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "boardList.do");
            return null;
        }
        
        try {
        	int maxSize = 1024 * 1024 * 10;
        	
        	String root = request.getSession().getServletContext().getRealPath("/");
        	
        	String savePath = root + "/resources/board/saveImage/";
        	
            board.setUserNo(user.getUserNo());
            
            MultipartRequest multiRequest
            	= new MultipartRequest(request,savePath,maxSize,"UTF-8",new MyFileRenamePolicy());
            
            ArrayList<String> changeFiles = new ArrayList<String>();
            
            ArrayList<String> originFiles = new ArrayList<String>();
            
            Enumeration<String> files = multiRequest.getFileNames();
        	while(files.hasMoreElements()) {
				
				//files에 담겨있는 파일 리스트들의 name 값을 반환
				String name = files.nextElement();
				
				// 해당 파일이 null이 아닌 경우
				if(multiRequest.getFilesystemName(name) != null) {
					// getFilesystemName() - MyRenamePolicy의 rename 메소드에서
					// 작성한대로 rename 된 파일명
					String changeName = multiRequest.getFilesystemName(name);
					
					// getOriginalFileName() - 실제 사용자가 업로드 할 때 파일명
					String originName = multiRequest.getOriginalFileName(name);
					
					changeFiles.add(changeName);
					originFiles.add(originName);
				}
			}
        	String categoryNo = multiRequest.getParameter("categoryNo");
            String title = multiRequest.getParameter("title").trim();
            String content = multiRequest.getParameter("content").trim();
        	
            board.setCategoryNo(categoryNo);
            board.setTitle(title);
            board.setContent(content);
            
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        
        // 데이터베이스에 게시글 삽입
        int check = BoardDAO.getInstance().insertBoard(board, user.getUserNo());
        
        if (check > 0) {
            ScriptUtil.sendScript(response, "게시글 작성 성공!", "boardList.do");
        } else {
            ScriptUtil.sendScript(response, "게시글 작성 실패!", null);
        }

        return null;
    }
}
