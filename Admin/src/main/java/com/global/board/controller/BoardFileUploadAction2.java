package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.global.action.Action;
import com.global.action.View;
import com.google.gson.Gson;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class BoardFileUploadAction2 implements Action {

    private static final String UPLOAD_DIR = "resources/board/board_tmp_uploads_files"; // 임시 업로드 폴더 경로

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 응답 타입을 JSON으로 설정
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 결과를 저장할 Map
        Map<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        
        try {
            // 파일 받기 (파일은 multipart/form-data 형식으로 전송됨)
            Part filePart = request.getPart("upload");  // CKEditor에서 보낸 이미지 파일은 "upload"라는 이름으로 전송됨
            String fileName = getFileName(filePart);
            
            // 서버의 업로드 경로 설정
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
            
            // 업로드 디렉토리 생성
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            // 파일 저장 경로 설정
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);  // 파일 임시 저장
            
            // 세션에 업로드된 파일 경로 저장
            List<String> uploadedFiles = (List<String>) session.getAttribute("uploadedFiles");
            if (uploadedFiles == null) {
                uploadedFiles = new ArrayList<>();
            }
            uploadedFiles.add(filePath);  // 세션에 파일 경로 추가
            session.setAttribute("uploadedFiles", uploadedFiles);

            // JSON 응답 형식에 맞춰 이미지 URL과 성공 여부를 반환
            String fileUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
            result.put("uploaded", true);
            result.put("url", fileUrl);
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("uploaded", false);
            result.put("error", "파일 업로드 중 문제가 발생했습니다.");
        }
        
        // Gson을 이용해 JSON 형식으로 응답
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        out.print(jsonResponse);
        out.flush();
        
        return null;
    }
    
    // 파일명 추출 메소드
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
