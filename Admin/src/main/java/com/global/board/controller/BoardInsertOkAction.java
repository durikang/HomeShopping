package com.global.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.utils.ScriptUtil;

public class BoardInsertOkAction implements Action {

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
        
        board.setUserNo(user.getUserNo());
        String categoryNo = request.getParameter("categoryNo");
        String title = request.getParameter("title").trim();
        String content = request.getParameter("content").trim();
        
        board.setCategoryNo(categoryNo);
        board.setTitle(title);
        board.setContent(content);

        // 파일 업로드 처리
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");

        if (file != null && !file.isEmpty()) {
            // 파일 저장 경로 설정 (웹 애플리케이션 내의 경로로 설정 가능)
            ServletContext context = request.getServletContext();
            String uploadPath = context.getRealPath("/upload");

            // 파일명 중복 방지를 위한 파일명 처리
            String originalFilename = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            String savedFilename = System.currentTimeMillis() + "_" + originalFilename;

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();  // 디렉토리가 없으면 생성
            }

            // 파일 저장
            File saveFile = new File(uploadDir, savedFilename);
            file.transferTo(saveFile);

            // 파일 경로를 board 객체에 저장 (필요에 따라 절대경로나 상대경로로 설정)
            board.setImageUrl("/upload/" + savedFilename);
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
