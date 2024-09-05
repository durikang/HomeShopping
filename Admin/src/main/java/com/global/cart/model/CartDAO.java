package com.global.cart.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.global.board.model.BoardCategoryDTO;

public class CartDAO {
	
	//DB와 연결하는 객체
	Connection con = null;
	//DB에 SQL문을 전송하는 객체
	PreparedStatement pstmt = null;
	//SQL문을 실행한 후 결과값을 가지고 있는 객체
	ResultSet rs = null;
	//SQL문을 저장하는 변수
	String sql = null;
	
	private static CartDAO instance =null;
	
	public CartDAO() {}; //기본생성자
	
	// 외부에서 접근할 수 있도록 해 주는 메서드
	public static CartDAO getInstanceCart() {
		
		if(instance==null) {
				instance = new CartDAO();
			}
		
		return instance;
		
	} //getInstance() end
	
	//DB연동하는 작업을 진행하는 메서드
	public void openConn() {
		try {
			
			// 1단계 : JNDI 서버 객체 생성.
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext
			// 객체는 네이밍 서비스를 이용하기 위한 시작점이 됨. 
			Context initCtx = new InitialContext();
			
			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현제 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx =
					(Context)initCtx.lookup("java:comp/env");
			
			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			
			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();
			
			} 
		catch (Exception e) {
				e.printStackTrace();
			}
		
	} //openConn() end
	
	//DB연동하는 작업을 종료하는 메서드 (rs,pstmt,con)
	public void closeConn(ResultSet rs, Statement stmt, Connection con) {
		
			try {
				
				if(rs != null && !rs.isClosed()) rs.close();
				if(stmt != null && !stmt.isClosed()) stmt.close();
				if(con != null && !con.isClosed()) con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}//closeConn(rs,pstmt,con) end

	//DB연동하는 작업을 종료하는 메서드
	public void closeConn(Statement stmt, Connection con) {
			
				try {
					
					if(stmt != null && !stmt.isClosed()) stmt.close();
					if(con != null && ! con.isClosed()) con.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}//closeConn(pstmt, con) end
	

	//cart 테이블 전체 정보 조회 메서드
	public List<CartDTO> getCartList(){
		List<CartDTO> list = new ArrayList<>();
		
		try {
			
			openConn();
		
			sql = "select * from CART order by cart_no desc";
			
			pstmt = con.prepareStatement(sql);
		
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				CartDTO dto = new CartDTO();
				
				dto.setCart_no(rs.getInt("CART_NO"));
				dto.setCart_userNo(rs.getInt("USER_NO"));
				dto.setCart_createdAt(rs.getDate("CREATED_AT"));
				
				list.add(dto);
				
			}
		} catch (SQLException e) {
				e.printStackTrace();
			} finally {
					closeConn(rs, pstmt, con);
				}
	
		return list;
	}//getCartList end


	//cart 테이블 정보를 삭제하는 메서드
	public int deleteCart(int no) {
		int result =0;
		
		try {
			
			openConn();
		
			
			sql=" DELETE FROM cart_item where CART_ITEM_NO = ? ";
			
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
			sql ="delete from cart where cart_no =?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
				e.printStackTrace();
			} finally {
						closeConn(pstmt, con);
				}
		
		return result;
		
	}//deleteCart end
	
	//cart 테이블 정보를 상세보기하는 메서드[..?]
	
	//cart 테이블 정보를 수정하는 메서드[..?]
	
	
/*
 	//cart 테이블 정보를 추가하는 메서드 ( 장바구니 버튼에서 user가 처음 장바구니 담기를 할때 (장바구니No가 null일때 ))
	public int getCartCount(CartDTO dto) {
		int result=0, count=0; 
		
		try {
			
			openConn();
			
			sql="SELECT MAX(CART_NO) FROM CART"; 
			
			pstmt = con.prepareStatement(sql);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
					count = rs.getInt(1)+1;
				}
			
			sql = "INSERT INTO CART VALUES(?, ?, SYSDATE )";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, count);
			pstmt.setInt(2, dto.getCart_userNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
				}finally {
					closeConn(rs, pstmt, con);
						}
		
		return result;
		
	} //insertCart end
	
		// cart 테이블 user_no로 해당하는 정보를 조회하는 메서드
	public CartDTO contentCart(int no) {
		CartDTO dto = null;
		
		
		try {
			openConn();
			
			sql = "SELECT * FROM CART WHERE USER_NO =? ";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CartDTO();
				dto.getCart_no();
				dto.getCart_userNo();
				dto.getCart_createdAt();
			}
		} catch (SQLException e) {	
				e.printStackTrace();
			}finally {
					closeConn(rs, pstmt, con);
				}
		
		return dto;
	}//contentCrt end
	
	
	//cart 테이블 정보 삭제시 CART_NO 순서를 재작업하는 메서드
	public void updateSequenceCart(int no) {

		try {
			openConn();
			
			sql="update cart set CART_NO = CART_NO -1 where CART_NO > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
			sql="update cart_item set CART_NO = CART_NO -1 where CART_NO > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
				e.printStackTrace();
			} finally {
					closeConn(pstmt, con);
				}
		
	}//updateSequence end
	
	
	
 */
	
}//class end
