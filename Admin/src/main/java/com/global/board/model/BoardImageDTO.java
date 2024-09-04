package com.global.board.model;

import java.util.Date;

public class BoardImageDTO {

    private int imageNo;        // 사진의 고유 번호
    private int boardNo;        // 게시글 번호 (BOARD 테이블 참조)
    private String imageUrl;    // 사진의 파일 경로 또는 URL
    private String description; // 사진에 대한 설명 (선택)
    private Date uploadedAt;    // 사진 업로드 날짜

    // 기본 생성자
    public BoardImageDTO() {}

    // 매개변수 있는 생성자
    public BoardImageDTO(int imageNo, int boardNo, String imageUrl, String description, Date uploadedAt) {
        this.imageNo = imageNo;
        this.boardNo = boardNo;
        this.imageUrl = imageUrl;
        this.description = description;
        this.uploadedAt = uploadedAt;
    }

    // Getter and Setter methods
    public int getImageNo() {
        return imageNo;
    }

    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    // toString() 메서드 (선택 사항)
    @Override
    public String toString() {
        return "BoardImageDTO{" +
                "imageNo=" + imageNo +
                ", boardNo=" + boardNo +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
