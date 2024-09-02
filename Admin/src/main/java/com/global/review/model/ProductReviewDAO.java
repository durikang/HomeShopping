package com.global.review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductReviewDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public static ProductReviewDAO instance = null;
	
	public ProductReviewDAO() {}
	
	public static ProductReviewDAO getInstance() {
		
		if(instance == null) {
			instance = new ProductReviewDAO();
		}
		
		return instance;
		
	}
	
	public void openConn() {
		
		try {

			Context initCtx = new InitialContext();

			Context ctx = (Context) initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			con = ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // openConn() end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {

			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	} // closeConn() end

	public int getReviewCount(char c) {
		
		int count = 0;
		
		return 0;
	} // getReviewCount() end

	public List<ProductReviewDTO> selectReviewList(int currentPage, int boardLimit, char c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getReviewCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<ProductReviewDTO> selectReviewList(int currentPage, int boardLimit) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
