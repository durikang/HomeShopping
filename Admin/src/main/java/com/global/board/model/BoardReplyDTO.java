package com.global.board.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BoardReplyDTO {
    private int replyNo;        // 댓글 번호
    private int boardNo;        // 게시글 번호
    private int userNo;         // 유저 번호
    private String content;     // 댓글 내용
    private int leftVal;        // 중첩 집합 모델에서의 왼쪽 값
    private int rightVal;       // 중첩 집합 모델에서의 오른쪽 값
    private int nodeLevel;      // 계층 수준
    private int parentReplyNo;  // 부모 댓글 번호 (대댓글을 위한 필드)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;     // 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;     // 수정일
    private String isDeleted;   // 삭제 여부 ('N'은 삭제 X, 'Y'는 삭제 O)

    
	/* USERS 정보를 함께 담을 필요가있음.*/
	
	private String userId;
	private String userName;
	private String userEmail;
	private String userType;
    
    
    // 기본 생성자
    public BoardReplyDTO() {}

    // 모든 필드를 사용하는 생성자
    public BoardReplyDTO(int replyNo, int boardNo, int userNo, String content, int leftVal, int rightVal, int nodeLevel, int parentReplyNo, Date createdAt, Date updatedAt, String isDeleted) {
        this.replyNo = replyNo;
        this.boardNo = boardNo;
        this.userNo = userNo;
        this.content = content;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
        this.nodeLevel = nodeLevel;
        this.parentReplyNo = parentReplyNo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	// Getter and Setter methods
    public int getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(int replyNo) {
        this.replyNo = replyNo;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLeftVal() {
        return leftVal;
    }

    public void setLeftVal(int leftVal) {
        this.leftVal = leftVal;
    }

    public int getRightVal() {
        return rightVal;
    }

    public void setRightVal(int rightVal) {
        this.rightVal = rightVal;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public int getParentReplyNo() {
        return parentReplyNo;
    }

    public void setParentReplyNo(int parentReplyNo) {
        this.parentReplyNo = parentReplyNo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

}
