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

	public int getBoardCount(String status) {
	    int count = 0;
	    try {
	        openConn();
	        sql = "SELECT COUNT(*) FROM board LEFT JOIN users USING(user_no)";
	        
	        if (status != null) {
	            sql += " WHERE is_deleted = ?";
	        }
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        if (status != null) {
	            pstmt.setString(1, status);
	        }

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
		/*
			sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?";
	 	*/
		// 현재 회원 관련된 로직이 미구현 상태이므로 임시로 아래 쿼리를 사용합니다.
			sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?";
				
		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, String.valueOf(status));
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				
			    // 아직 회원 관련 로직이 구현되지 않았으므로
			    int userNo = rs.getInt("USER_NO");
			    if (rs.wasNull()) {
			        board.setUserNo(null); // USER_NO가 NULL이면 null 설정
			    } else {
			        board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
			    }
				
				
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS"));				// 조회수 컬럼 추가 20240830 by 두리
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
		/* boardList페이지에서 BOARD_DETAIL을 호출할때 USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다. 
			sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?";
		*/
		// 현재 회원 관련된 로직이 미구현 상태이므로 임시로 아래 쿼리를 사용합니다.
		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?";
		
		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				
				// 아직 회원 관련 로직이 구현되지 않았으므로
			    int userNo = rs.getInt("USER_NO");
			    if (rs.wasNull()) {
			        board.setUserNo(null); // USER_NO가 NULL이면 null 설정
			    } else {
			        board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
			    }
			    
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS"));				// 조회수 컬럼 추가 20240830 by 두리
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));
				
				/* 카테고리 정보 */
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				/* USERS 정보도 필요하므로 SET*/
				
				board.setUserNo(rs.getObject("USER_NO"));
				
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
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

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
			int paramIndex=1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, String.valueOf(status));
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

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
			int paramIndex=1;
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, category.getCategoryNo());
			pstmt.setString(paramIndex++, category.getName());
			pstmt.setString(paramIndex++, category.getDescription());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public int insertBoard(BoardDTO board,Integer userNo) {
		int res = 0;
		
		openConn();
		/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
		/*
		 * sql = "insert into board values(seq_board_no.nextval,?,?,?,?,sysdate,null,'N')";
		 */
		
		sql = "insert into board values(seq_board_no.nextval,?,?,?,?,default,sysdate,null,'N')";
		
		try {
			pstmt = conn.prepareStatement(sql);
			int paramIndex=1;
			/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
			/*
			 * pstmt.setInt(1, board.getUserNo());
			 */
			
	        if (userNo == null) {
	            pstmt.setNull(paramIndex++, java.sql.Types.INTEGER); // userNo가 null일 때 처리
	        } else {
	            pstmt.setInt(paramIndex++, userNo); // userNo가 null이 아닐 때 처리
	        }
			pstmt.setString(paramIndex++, board.getCategoryNo());
			pstmt.setString(paramIndex++, board.getTitle());
			pstmt.setString(paramIndex++, board.getContent());
			
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
		/*
			만약 UserType이 Admin이고, admin_roll이 '최고 관리자' 일경우도 아래 setter해줘야함.
			이건 추후에 추가할 예정
		*/
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
			/* 만약 user정보 없이 삽입된 게시글일 경우*/
			sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO LEFT JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.BOARD_NO = ?";
			
			 if (sql != null) {
				int paramIndex=1;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(paramIndex++, no);
				
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
			int paramIndex = 1;
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(paramIndex++, board.getCategoryNo());
			pstmt.setString(paramIndex++, board.getTitle());
			pstmt.setString(paramIndex++, board.getContent());
			pstmt.setInt(paramIndex++, board.getBoardNo());
			
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
			openConn();
			int paramIndex = 1;
			sql = "UPDATE BOARD SET VIEWS = VIEWS+1 WHERE BOARD_NO=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex, boardNo);
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}
		
		
		return res;
	}

	public BoardCategoryDTO selectBoardCategory(String categoryNo) {
		BoardCategoryDTO category = null;
		try {
			openConn();
			sql = "select * from BOARD_CATEGORY where CATEGORY_NO = ?";
			int paramIndex = 1;
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, categoryNo);
		
			rs = pstmt.executeQuery();
			if(rs.next()) {
				category = new BoardCategoryDTO();
				
				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return category;
	}

	public int updateCategory(BoardCategoryDTO category) {
		int res = 0;

		try {
			sql = "update BOARD_CATEGORY set NAME = ?, DESCRIPTION = ? where CATEGORY_NO = ?";
			
			int paramIndex = 1;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, category.getName());
			pstmt.setString(paramIndex++, category.getDescription());
			pstmt.setString(paramIndex++, category.getCategoryNo());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(pstmt, conn);
		}
		
		return res;
	}
	
	public int getBoardCount(BoardFilter filter) {
	    int count = 0;
	    sql = "SELECT COUNT(*) FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO WHERE 1=1";
	    
	    // 필터 조건 추가
	    List<Object> params = new ArrayList<>();
	    if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
	        sql += " AND b.CATEGORY_NO = ?";
	        params.add(filter.getCategoryNo());
	    }
	    if (filter.getUserNo() != null) {
	        sql += " AND b.USER_NO = ?";
	        params.add(filter.getUserNo());
	    }
	    if (filter.getMinViews() != null) {
	        sql += " AND b.VIEWS >= ?";
	        params.add(filter.getMinViews());
	    }
	    if (filter.getMaxViews() != null) {
	        sql += " AND b.VIEWS <= ?";
	        params.add(filter.getMaxViews());
	    }
	    if (filter.getStartDate() != null) {
	        sql += " AND b.CREATED_AT >= ?";
	        params.add(filter.getStartDate());
	    }
	    if (filter.getEndDate() != null) {
	        sql += " AND b.CREATED_AT <= ?";
	        params.add(filter.getEndDate());
	    }
	    if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
	        sql += " AND b.IS_DELETED = ?";
	        params.add(filter.getIsDeleted());
	    }

	    try {
	        openConn();
	        pstmt = conn.prepareStatement(sql);
	        
	        // 파라미터 바인딩
	        for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }

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

	
	public List<BoardDTO> searchBoardList(int currentPage, int boardLimit, String searchKeyword, BoardFilter filter) {
	    List<BoardDTO> list = new ArrayList<>();
	    
	    // 동적 SQL을 구성하기 위해 기본 SELECT 쿼리 작성
	    sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME " +
	          "FROM BOARD b " +
	          "LEFT JOIN USERS u ON b.USER_NO = u.USER_NO " +
	          "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " +
	          "WHERE 1=1";

	    // 검색어(제목) 필터 추가
	    if (searchKeyword != null && !searchKeyword.isEmpty()) {
	        sql += " AND b.TITLE LIKE ?";
	    }

	    // 필터링 조건 추가
	    if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
	        sql += " AND b.CATEGORY_NO = ?";
	    }
	    if (filter.getUserNo() != null) {
	        sql += " AND b.USER_NO = ?";
	    }
	    if (filter.getMinViews() != null) {
	        sql += " AND b.VIEWS >= ?";
	    }
	    if (filter.getMaxViews() != null) {
	        sql += " AND b.VIEWS <= ?";
	    }
	    if (filter.getStartDate() != null) {
	        sql += " AND b.CREATED_AT >= ?";
	    }
	    if (filter.getEndDate() != null) {
	        sql += " AND b.CREATED_AT <= ?";
	    }
	    if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
	        sql += " AND b.IS_DELETED = ?";
	    }

	    // 페이지네이션을 위한 서브 쿼리 조건 추가
	    sql += ") WHERE rnum BETWEEN ? AND ?";

	    try {
	        openConn();
	        pstmt = conn.prepareStatement(sql);

	        int paramIndex = 1;

	        // 검색어(제목) 파라미터 세팅
	        if (searchKeyword != null && !searchKeyword.isEmpty()) {
	            pstmt.setString(paramIndex++, "%" + searchKeyword + "%");
	        }

	        // 필터 파라미터 세팅
	        if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
	            pstmt.setString(paramIndex++, filter.getCategoryNo());
	        }
	        if (filter.getUserNo() != null) {
	            pstmt.setInt(paramIndex++, filter.getUserNo());
	        }
	        if (filter.getMinViews() != null) {
	            pstmt.setInt(paramIndex++, filter.getMinViews());
	        }
	        if (filter.getMaxViews() != null) {
	            pstmt.setInt(paramIndex++, filter.getMaxViews());
	        }
	        if (filter.getStartDate() != null) {
	            pstmt.setDate(paramIndex++, filter.getStartDate());
	        }
	        if (filter.getEndDate() != null) {
	            pstmt.setDate(paramIndex++, filter.getEndDate());
	        }
	        if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
	            pstmt.setString(paramIndex++, filter.getIsDeleted());
	        }

	        // 페이지네이션 파라미터 세팅
	        int startRow = (currentPage - 1) * boardLimit + 1;
	        int endRow = startRow + boardLimit - 1;
	        pstmt.setInt(paramIndex++, startRow);
	        pstmt.setInt(paramIndex++, endRow);

	        rs = pstmt.executeQuery();

	        // 결과를 리스트에 담음
	        while (rs.next()) {
	            BoardDTO board = new BoardDTO();
	            board.setBoardNo(rs.getInt("BOARD_NO"));
	            board.setTitle(rs.getString("TITLE"));
	            board.setContent(rs.getString("CONTENT"));
	            board.setViews(rs.getInt("VIEWS"));
	            board.setCreateAt(rs.getDate("CREATED_AT"));
	            board.setUpdateAt(rs.getDate("UPDATED_AT"));
	            board.setIsDeleted(rs.getString("IS_DELETED"));
	            board.setCategoryNo(rs.getString("CATEGORY_NO"));
	            board.setCategoryName(rs.getString("CATEGORY_NAME"));
			    
	            // 아직 회원 관련 로직이 구현되지 않았으므로
			    int userNo = rs.getInt("USER_NO");
			    if (rs.wasNull()) {
			        board.setUserNo(null); // USER_NO가 NULL이면 null 설정
			    } else {
			        board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
			    }
         
	            board.setUserId(rs.getString("USER_ID"));
	            board.setUserEmail(rs.getString("USER_EMAIL"));
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



}
