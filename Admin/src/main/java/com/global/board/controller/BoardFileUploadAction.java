package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.google.gson.Gson;
import com.global.board.model.BoardFileUploadDTO;
import com.global.board.model.UploadResponse;

public class BoardFileUploadAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Gson 객체 생성 (JSON 응답을 위해)
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");

        // 세션에서 사용자 정보를 가져옴
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(new UploadResponse(false, null, "Unauthorized")));
            return null;
        }

        // 세션에서 userNo와 userId를 가져옴
        int userNo = user.getUserNo();
        String userId = user.getUserId();

        // userId가 null일 경우 예외 처리
        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new UploadResponse(false, null, "Invalid user information")));
            return null;
        }
        
        // 날짜별 경로 설정 (년/월/일)
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        // 파일이 저장될 임시 루트 경로 설정 (임시 저장소 경로)
        String tempUploadRoot = request.getServletContext().getRealPath("/resources/board/board_temp_files");
        String uploadDir = Paths.get(tempUploadRoot, String.valueOf(userNo), userId, year, month, day).toString();
        
        // 디렉토리가 존재하지 않으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();  // 경로가 없으면 생성
        }
        
        // 파일 파트 가져오기 (Froala에서 이미지나 파일을 업로드할 때 사용)
        Part filePart = request.getPart("file");  // 'file'은 Froala에서 전송하는 파일 파라미터 이름
        
        if (filePart != null && filePart.getSize() > 0) {
            // 파일의 원본 이름을 가져옴
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            
            // 파일 이름 중복 방지를 위해 UUID를 추가하여 파일 이름을 변경
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            String filePath = Paths.get(uploadDir, uniqueFileName).toString();

            // 파일 저장
            filePart.write(filePath);
            
         // 실제로 필요한 파일 URL (웹 루트 경로에서 시작)
            String contextPath = request.getContextPath();  // "/Admin" 값이 들어갑니다.
            String fileUrl = contextPath + "/resources/board/board_temp_files/" + userNo + "/" + userId + "/" + year + "/" + month + "/" + day + "/" + uniqueFileName;

            // 파일 정보를 DTO에 담기
            BoardFileUploadDTO fileDTO = new BoardFileUploadDTO();
            fileDTO.setFileUrl(fileUrl);
            fileDTO.setFileName(uniqueFileName);
            fileDTO.setFileSize(filePart.getSize());
            fileDTO.setFileType(filePart.getContentType());

            // 세션에 업로드된 파일 정보 저장 (BoardFileUploadDTO 리스트 형태로)
            List<BoardFileUploadDTO> uploadedFiles = (List<BoardFileUploadDTO>) session.getAttribute("uploadedFiles");
            if (uploadedFiles == null) {
                uploadedFiles = new ArrayList<>();
            }
            uploadedFiles.add(fileDTO);
            session.setAttribute("uploadedFiles", uploadedFiles);

            // JSON 응답 생성 (Froala가 성공적으로 업로드했는지 확인할 때 사용)
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Gson을 사용해 JSON 응답 전송
            String jsonResponse = gson.toJson(new UploadResponse(true, fileUrl, null));
            response.getWriter().write(jsonResponse);
        } else {
            // 파일이 없을 경우 실패 응답
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = gson.toJson(new UploadResponse(false, null, "No file uploaded"));
            response.getWriter().write(jsonResponse);
        }

        return null;  // View를 리턴할 필요 없음 (JSON 응답 처리)
    }
}
