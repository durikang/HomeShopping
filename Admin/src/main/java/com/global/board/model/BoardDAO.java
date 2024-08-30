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

		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?";

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
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));
				
				/* 카테고리 정보 */
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				/* USERS 정보도 필요하므로 SET*/
				board.setUserNo(rs.getInt("USER_NO"));
				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USEr_EMAIL"));
				board.setUserType(rs.getString("USER_TYPE"));
				
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
		/* boardList페이지에서 BOARD_DETAIL을 호출할때 USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다. */
		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?";

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
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));
				
				/* 카테고리 정보 */
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				/* USERS 정보도 필요하므로 SET*/
				board.setUserNo(rs.getInt("USER_NO"));
				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USEr_EMAIL"));
				board.setUserType(rs.getString("USER_TYPE"));
				
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList(int currentPage, int boardLimit) {
		List<BoardCategoryDTO> list = new ArrayList<>();
		/* boardList페이지에서 BOARD_DETAIL을 호출할때 USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다. */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY category_no ASC) AS rnum, b.* FROM BOARD_CATEGORY b ) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardCategoryDTO category = new BoardCategoryDTO();
				
				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));
				
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public int getBoardCategoryCount() {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD_CATEGORY";
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

	public int getBoardCategoryCount(char status) {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD_CATEGORY";
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

	public List<BoardCategoryDTO> selectBoardCategoryList(int currentPage, int boardLimit, char status) {
		List<BoardCategoryDTO> list = new ArrayList<>();

		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY CATEGORY_NO ASC) AS rnum, b.* FROM BOARD_CATEGORY b) WHERE rnum BETWEEN ? AND ?";

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
				BoardCategoryDTO category = new BoardCategoryDTO();
				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));

				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList() {
		List<BoardCategoryDTO> list = new ArrayList<>();
		try {
			sql = "select * from BOARD_CATEGORY order by 1 asc";
			openConn();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardCategoryDTO category = new BoardCategoryDTO();
				category.setCategoryNo(rs.getString(1));
				category.setName(rs.getString(2));
				category.setDescription(rs.getString(3));
				list.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public int insertBoardCategory(BoardCategoryDTO category) {
		int res = 0;

		try {
			openConn();
			sql = "insert into BOARD_CATEGORY values(?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getCategoryNo());
			pstmt.setString(2, category.getName());
			pstmt.setString(3, category.getDescription());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public int insertBoard(BoardDTO board) {
		int res = 0;
		
		openConn();
		/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
		/*
		 * sql = "insert into board values(seq_board_no.nextval,?,?,?,?,sysdate,null,'N')";
		 */
		
		sql = "insert into board values(seq_board_no.nextval,NULL,?,?,?,sysdate,null,'N')";
		
		try {
			pstmt = conn.prepareStatement(sql);
			/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
			/*
			 * pstmt.setInt(1, board.getUserNo());
			 */
			
			pstmt.setString(1, board.getCategoryNo());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}
		
		return res;
	}

	public BoardDTO selectBoard(int no, String userType) {
		BoardDTO board = null;
		try {
			openConn();
			
			if ("ADMIN".equals(userType)) {
                sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, ar.ROLE_NAME " +
                        "FROM BOARD b " +
                        "JOIN USERS u ON b.USER_NO = u.USER_NO " +
                        "JOIN ADMIN a ON u.USER_NO = a.USER_NO " +
                        "JOIN ADMIN_ROLE ar ON a.ROLE_CODE = ar.ROLE_CODE " +
                        "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " +
                        "WHERE b.BOARD_NO = ?";
            } else if ("CUSTOMER".equals(userType)) {
                sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, c.AGE, c.JOB, c.LOCATION " +
                        "FROM BOARD b " +
                        "JOIN USERS u ON b.USER_NO = u.USER_NO " +
                        "JOIN CUSTOMER c ON u.USER_NO = c.USER_NO " +
                        "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " +
                        "WHERE b.BOARD_NO = ?";
            }
			
			 if (sql != null) {
			
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
                    board = new BoardDTO();
                    board.setBoardNo(rs.getInt("BOARD_NO"));
                    board.setUserNo(rs.getInt("USER_NO"));
                    board.setTitle(rs.getString("TITLE"));
                    board.setContent(rs.getString("CONTENT"));
                    board.setCreateAt(rs.getDate("CREATED_AT"));
                    board.setUpdateAt(rs.getDate("UPDATED_AT"));
                    board.setIsDeleted(rs.getString("IS_DELETED"));
                    board.setCategoryNo(rs.getString("CATEGORY_NO"));
                    board.setCategoryName(rs.getString("CATEGORY_NAME"));
                    board.setDescription(rs.getString("DESCRIPTION"));
                    board.setUserId(rs.getString("USER_ID"));
                    board.setUserName(rs.getString("USER_NAME"));
                    board.setUserEmail(rs.getString("USER_EMAIL"));
                    board.setUserType(rs.getString("USER_TYPE"));
				}
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, conn);
		}
		
		return board;
	}

	public int updateBoard(BoardDTO board) {
		int res = 0;
		try {
			openConn();
			sql = "update board set CATEGORY_NO = ?, TITLE = ?, CONTENT = ?, UPDATED_AT = SYSDATE WHERE BOARD_NO = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getCategoryNo());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getBoardNo());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}
		
		return res;
	}
	/*
	 * by 두리
	 * 조회수 증가 로직입니다.
	 * 
	 * */
	public int increaseViews(int boardNo) {
		int res = 0;
		
		try {
			sql = "UPDATE BOARD SET VIEWS=VIEWS+1 WHERE BOARD_NO=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}
		
		
		return res;
	}

}
