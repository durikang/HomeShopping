package com.global.board.model;

import java.sql.Date;

public class BoardDTO {
	private int boardNo;
	private int userNo;
	private String categoryNo;
	private String title;
	private String content;
	private Date createAt;
	private Date updateAt;
	private String isDeleted;
	
	public BoardDTO() {
	}

	public BoardDTO(int boardNo, int userNo, String categoryNo, String title, String content, Date createAt,
			Date updateAt, String isDeleted) {
		super();
		this.boardNo = boardNo;
		this.userNo = userNo;
		this.categoryNo = categoryNo;
		this.title = title;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.isDeleted = isDeleted;
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

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
