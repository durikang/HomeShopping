package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFileUploadDTO;
import com.global.utils.ScriptUtil;

public class BoardInsertOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");

        if (user == null) {
            // 사용자 정보가 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/loginForm.do");
            return null;
        }

        // 게시글 정보 가져오기
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String categoryNo = request.getParameter("categoryNo");

        // BoardDTO 객체 생성
        BoardDTO board = new BoardDTO();
        board.setTitle(title);
        board.setContent(content);
        board.setCategoryNo(categoryNo);

        // 파일 업로드 정보 가져오기 (세션에 저장된 List<BoardFileUploadDTO>)
        List<BoardFileUploadDTO> fileList = new ArrayList<>();
        List<BoardFileUploadDTO> uploadedFiles = (List<BoardFileUploadDTO>) session.getAttribute("uploadedFiles");

        if (uploadedFiles != null) {
            for (BoardFileUploadDTO file : uploadedFiles) {
                String tempFileUrl = file.getFileUrl();  // 임시 저장소 경로
                String fileName = file.getFileName();

                // 파일 이름 중복 방지 (파일 이름에 UUID 추가)
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                // 영구 저장소 경로 설정 (예: resources/board/board_upload_files/유저번호/유저아이디/파일명)
                String permanentFileUrl = "/resources/board/board_upload_files/" + user.getUserNo() + "/" + user.getUserId() + "/" + uniqueFileName;

                // 상대 경로에서 절대 경로로 변환
                String tempFileAbsolutePath = request.getServletContext().getRealPath(tempFileUrl);
                String permanentFileAbsolutePath = request.getServletContext().getRealPath(permanentFileUrl);

                // 영구 저장소 디렉토리가 존재하지 않으면 생성
                File permanentDir = new File(permanentFileAbsolutePath).getParentFile();  // 디렉토리 경로만 추출
                if (!permanentDir.exists()) {
                    permanentDir.mkdirs();  // 경로가 없으면 생성
                }


                // 실제 파일 이동
                try {
                    // 파일 이동 (임시 저장소에서 영구 저장소로)
                    Files.move(Paths.get(tempFileAbsolutePath), Paths.get(permanentFileAbsolutePath));
                    System.out.println("파일이 성공적으로 이동되었습니다.");
                    
                    System.out.println("Temp File Path: " + tempFileAbsolutePath);
                    System.out.println("Permanent File Path: " + permanentFileAbsolutePath);

                    if (new File(tempFileAbsolutePath).exists()) {
                        System.out.println("Temp file exists, moving...");
                    } else {
                        System.out.println("Temp file not found, check the path!");
                    }

                    
                    
                    // 파일 이동 후, 영구 저장소 경로를 DTO에 저장
                    BoardFileUploadDTO fileDTO = new BoardFileUploadDTO();
                    fileDTO.setFileUrl(permanentFileUrl);  // 영구 저장소 경로
                    fileDTO.setFileName(uniqueFileName);   // 유일한 파일명으로 변경
                    fileDTO.setFileSize(file.getFileSize());
                    fileDTO.setFileType(file.getFileType());

                    fileList.add(fileDTO);  // 파일 리스트에 추가

                } catch (IOException e) {
                    e.printStackTrace();
                    // 파일 이동 실패 시 예외 처리 필요
                    continue;
                }
            }
        }

        // DAO를 통해 게시글 및 파일 저장 (트랜잭션 처리)
        BoardDAO dao = BoardDAO.getInstance();
        int result = dao.insertBoard(board, user.getUserNo(), fileList);

        if (result > 0) {
            // 게시글 등록 성공 시 처리
            session.setAttribute("isSaved", true);  // 세션에 저장 성공 표시
            session.removeAttribute("uploadedFiles");  // 임시 파일 목록 제거
            ScriptUtil.sendScript(response, "게시글 작성 성공!", "boardList.do");
        } else {
            // 게시글 등록 실패 시 처리
            session.setAttribute("isSaved", false);
            ScriptUtil.sendScript(response, "게시글 작성 실패!", null);
        }

        return null;  // View는 필요 없음
    }
}
