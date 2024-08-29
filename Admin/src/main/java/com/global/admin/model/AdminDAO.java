package com.global.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AdminDAO {
   
   Connection con = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   String sql = null;
   
   public static AdminDAO instance = null;
   
   public AdminDAO() {}
   
   public AdminDAO getInstance() {
      
      if(instance == null) {
         instance = new AdminDAO();
      }
      
      return instance;
   }
   
   // DB 연동하는 작업을 진행하는 메서드
   public void openConn() {
      
      try {
         Context initCtx = new InitialContext();

         Context ctx = (Context) initCtx.lookup("java:comp/env");

         DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

         con = ds.getConnection();
      } 
      
      catch (Exception e) {
         e.printStackTrace();
      }
   } // openConn() end

   // DB에 연결된 자원을 종료하는 메서드
   public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

      try {
         if (rs != null)
            rs.close();
         if (pstmt != null)
            pstmt.close();
         if (con != null)
            con.close();
      } 
      
      catch (SQLException e) {
         e.printStackTrace();
      }
   } // closeConn() end
   
   // 관리자인지 여부를 확인하는 메서드
   public int adminCheck(String id, String password) {
	   
	   int result = 0;
	   
	   try {
		   sql = "select * from ADMIN where user_id = ?";
		   
		   pstmt = con.prepareStatement(sql);
		   
		   pstmt.setString(1, id);
		   
		   rs = pstmt.executeQuery();
		   
		   if(rs.next()) {
			   if(password.equals(rs.getString("password"))) {
				   result = 1;
			   }
			   
			   else {
				   result = -1;
			   }
		   }
	   } 
	   
	   catch (SQLException e) {
		e.printStackTrace();
	   }
	   
	   finally {
		closeConn(rs, pstmt, con);
	   }
	   
	   return result;
   } // adminCheck() end
   
   // 관리자에 대한 상세 정보를 조회하는 메서드
	public AdminDTO getAdmin(String id) {
		
		AdminDTO dto = null;
		
		try {
			openConn();
			
			sql = "select * from ADMIN where admin_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new AdminDTO();
				
				dto.setUser_no(rs.getInt(1));
				dto.setUser_id(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setName(rs.getString(4));
				dto.setEmail(rs.getString(5));
				dto.setRole_code(rs.getString(6));
				dto.setRole_name(rs.getString(7));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	} // getAdmin() 메서드 end
}