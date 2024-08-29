package com.global.cartitem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CartItemDAO {
	//DB를 연결해주는 객체
	Connection con = null;
	//DB에 SQL문을 전송하는 객체
	PreparedStatement pstmt = null;
	//DB 실행한 후 SQL문 값을 가지고 있는 객체
	ResultSet rs = null;
	//SQL문을 저장하는 변수
	String sql = null;
	
	private static CartItemDAO instance = null;
	
	public CartItemDAO() {} //기본생성자
	
	// 외부에서 접근할 수 있도록 해 주는 메서드
	public static CartItemDAO getInstanceCartItem() {
		
		if(instance != null) {
			instance = new CartItemDAO(); 
		}
		
		return instance; 
		
	}//getInstanceCartItem end
	
	//DB를 연결해주는 메서드
	public void openConn() {
		try {
			Context intntCtx = new InitialContext();
			Context ctx = (Context)intntCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//opConn end
	
	//DB를 종료해주는 메서드 ( rs,pstmt,con )
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
			try {
				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close(); 
				
			} catch (SQLException e) {
				
					e.printStackTrace();
				}
		
	}//closeConn(rs, pstmt, con) end
	
	////DB를 종료해주는 메서드 ( pstmt, con )
	public void closeConn(PreparedStatement pstmt, Connection con) {
		 
			
				try {
					
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
					
				} catch (SQLException e) {
					
						e.printStackTrace();
					}
	}//closeConn(pstmt, con)end
	
	//cartItem 테이블 정보를 추가하는 메서드
	public int insertCartItem(CartItemDTO dto) {
		int result = 0, count =0;
		
	
		try {
			openConn();
			
			sql ="SELECT MAX(CART_ITEM_NO) FROM CART_ITEM";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1)+1;
			}
			
			sql="INSERT INTO CART_ITEM VALUES(?, ?, ?, ?, SYSDATE, SYSDATE)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, count);
			pstmt.setInt(2, dto.getCartItem_cartNo());
			pstmt.setInt(3, dto.getCartItem_productNo());
			pstmt.setInt(4, dto.getCartItem_quantity());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
					e.printStackTrace();
				}finally {
						closeConn(rs, pstmt, con);
						}
		
		return result;
		
	} //insertCartItem end
	
	//cartItem 테이블 정보 전체 조회하는 메서드 
	public List<CartItemDTO> getCartItemList(){
		List<CartItemDTO> list = new ArrayList<CartItemDTO>();
		
	
		try {
			openConn();
			
			sql ="SELECT * FROM CART_ITEM ORDER BY  CART_ITEM_NO DESC ";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartItemDTO dto = new CartItemDTO();
				
				dto.setCartItem_no(rs.getInt("CART_ITEM_NO"));
				dto.setCartItem_cartNo(rs.getInt("CART_NO"));
				dto.setCartItem_productNo(rs.getInt("PRODUCT_NO"));
				dto.setCartItem_quantity(rs.getInt("QUANTITY"));
				dto.setCartItem_addedAt(rs.getDate("ADDED_AT"));
				dto.setCartItem_updatedAt(rs.getDate("UPDATED_AT"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
				}finally {
						closeConn(rs, pstmt, con);
					}
		
		return list;
		
	}//getCartItemList end
	
	//cartItem 테이블  CART_NO 검색하여 해당 정보를 조회하는 메서드 
	public CartItemDTO contentCartItem(int no) {
		CartItemDTO dto = null;
		
	
		try {
			openConn();
			
			sql ="SELECT * FROM CART_ITEM WHERE CART_NO =? ";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CartItemDTO();
				
				dto.getCartItem_no();
				dto.getCartItem_cartNo();
				dto.getCartItem_productNo();
				dto.getCartItem_quantity();
				dto.getCartItem_addedAt();
				dto.getCartItem_updatedAt();
	
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
				}finally {
						closeConn(rs, pstmt, con);
					}
	
		return dto;
	}
	
	//cartItem 테이블 삭제하는  메서드 
	
}//class end
