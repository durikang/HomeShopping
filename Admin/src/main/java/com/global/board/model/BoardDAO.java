package com.global.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	private static BoardDAO instance = null;

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
		return instance;
	}

	// DB를 연동하는 작업을 위한 매서드
	// JDBC방식이 아닌 DBCP 방식으로 DB와 연동하는 작업 진행
	public void openConn() {

		try {
			// 1단계 : JNDI 서버 객체 생성
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext 객체는
			// 네이밍 서비스를 이용하기 위한 시작점이 된다.

			Context initCtx = new InitialContext();

			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현제 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨
			Context envctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.

			DataSource ds = (DataSource) envctx.lookup("jdbc/myoracle");

			// 4 단계 : DataSoure 객체를 이요하여 컨넥션을 하나 가져오면 됨.
			conn = ds.getConnection();

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (stmt != null && !stmt.isClosed())
				stmt.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn(Statement stmt, Connection conn) {
		try {
			if (stmt != null && !stmt.isClosed())
				stmt.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getBoardCount() {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public int getBoardCount(char status) {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD";
			// 상태에 따라 SQL 조건 추가
			if (status == 'Y') {
				sql += " WHERE is_deleted = 'Y' order by 1 desc";
			} else if (status == 'N') {
				sql += " WHERE is_deleted = 'N' order by 1 desc";
			}
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public List<BoardDTO> selectBoardList(int currentPage, int boardLimit, char status) {
		List<BoardDTO> list = new ArrayList<>();

		String sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY BOARD_NO ASC) AS rnum, b.* FROM BOARD b WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(status));
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setUserNo(rs.getInt("USER_NO"));
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardDTO> selectBoardList(int currentPage, int boardLimit) {
		List<BoardDTO> list = new ArrayList<>();

		String sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY BOARD_NO ASC) AS rnum, b.* FROM BOARD b ) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setUserNo(rs.getInt("USER_NO"));
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

}
