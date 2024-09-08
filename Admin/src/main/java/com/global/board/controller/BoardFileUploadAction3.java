package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardFileUploadAction3 implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 파일 업로드 경로 설정 (임시 경로)
        String tempDirectory = request.getServletContext().getRealPath("/resources/board/temp_upload/");
        int maxPostSize = 10 * 1024 * 1024; // 10MB 제한
        String encoding = "UTF-8";

        File uploadDir = new File(tempDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // 디렉토리가 없을 경우 생성
        }
        
        // cos 라이브러리로 파일 처리
        MultipartRequest multi = new MultipartRequest(
                request, 
                tempDirectory, 
                maxPostSize, 
                encoding, 
                new DefaultFileRenamePolicy()
        );

        // 업로드된 파일 정보 처리
        String fileInputName = "upload";  // CKEditor가 기본적으로 'upload'로 파일을 전송함
        String fileName = multi.getFilesystemName(fileInputName);  // 실제 저장된 파일 이름
        String fileUrl = request.getContextPath() + "/resources/board/temp_upload/" + fileName; // 임시 저장 경로

        // 세션에 파일 경로 추가 (Gson 사용)
        addToSessionWithGson(request, fileUrl);

        // CKEditor로 응답할 JSON 객체 생성
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("uploaded", true);
        jsonResponse.put("url", fileUrl);

        // JSON으로 응답
        String json = new Gson().toJson(jsonResponse);
        response.setContentType("application/json");
        response.getWriter().write(json);

        // 업로드 후 View는 필요 없으므로 null 반환
        return null;
    }

    // 세션에 파일 경로를 Gson으로 저장하는 메서드
    private void addToSessionWithGson(HttpServletRequest request, String fileUrl) {
        Gson gson = new Gson();

        // 세션에 저장된 파일 리스트 가져오기
        String uploadedFilesJson = (String) request.getSession().getAttribute("uploadedFiles");

        // 기존 리스트가 없으면 빈 리스트로 초기화
        if (uploadedFilesJson == null) {
            uploadedFilesJson = "[]";  // 빈 배열로 초기화
        }

        // JSON 배열을 리스트로 변환
        List<String> uploadedFiles = gson.fromJson(uploadedFilesJson, List.class);
        uploadedFiles.add(fileUrl);  // 새로운 파일 경로 추가

        // 업데이트된 리스트를 다시 JSON으로 변환하여 세션에 저장
        String updatedFilesJson = gson.toJson(uploadedFiles);
        request.getSession().setAttribute("uploadedFiles", updatedFilesJson);
    }
}
