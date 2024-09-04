package com.global.admin.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AdminDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	private static AdminDAO instance = null;
	
	private AdminDAO() {}
	
	public static AdminDAO getInstance() {
		
		if(instance == null) {
			instance = new AdminDAO();
		}
		
		return instance;
	} // getInstance() 메서드 end
	
	public void openConn() {
		
		try {
			Context initCtx = new InitialContext();

			Context ctx = (Context)initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			
			con = ds.getConnection();
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	} // openConn() 메서드 end
	
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
			try {
				if(rs != null) rs.close();
				
				if(pstmt != null) pstmt.close();
				
				if(con != null) con.close();
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
	} // closeConn() 메서드 end
	
	// 전체 게시물 수를 확인하는 메서드.
	public int getCategoryCount() {
			
		int count = 0;
			
		try {
			openConn();
				
			sql = "select count(*) from admin_role";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return count;
	} // getCategoryCount() end
	
	// 직책 전체 리스트를 조회하는 메서드
	public List<AdminDTO> getAdminCategoryList() {
			
		List<AdminDTO> list = new ArrayList<AdminDTO>();
			
		try {
			openConn();
				
			sql = "select * from admin_role order by role_code desc";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				AdminDTO dto = new AdminDTO();
					
				dto.setRoleCode(rs.getString("role_code"));
				dto.setRoleName(rs.getString("role_name"));
					
				list.add(dto);
			}
		} 
			
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return list;
	} // getAdminCategoryList() 메서드 end
		
	// 직책 추가하는 메서드
	public int insertAdminCategory(AdminDTO dto) {
		
		int result = 0, count = 0;
			
		try {
			openConn();
				
			sql = "select max(role_code) from admin_role";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				count = rs.getInt(1);
			}
				
			sql = "insert into admin_role values(?, ?)";
				
			pstmt = con.prepareStatement(sql);
				
			pstmt.setString(1, dto.getRoleCode());
			pstmt.setString(2, dto.getRoleName());
				
			result = pstmt.executeUpdate();
		} 
			
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return result;
	} // insertAdminCategory() 메서드 end
		
	// 관리자 직책의 정보를 조회하는 메서드
	public AdminDTO contentAdminCategory(String code, String name) {
			
		AdminDTO dto = null;
			
		try {
			openConn();
				
			sql = "select * from admin_role where role_name = ?";
				
			pstmt = con.prepareStatement(sql);
				
			pstmt.setString(1, code);
			pstmt.setString(2, name);
				
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				dto = new AdminDTO();
					
				dto.setRoleCode(rs.getString("roleCode"));
				dto.setRoleName(rs.getString("roleName"));
			}
		} 
			
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return dto;
	} // contentAdminCategory() 메서드 end
	
	// 전체 리스트를 조회하는 메서드
	public List<AdminDTO> getAdminList() {
		
		List<AdminDTO> list = new ArrayList<AdminDTO>();
		
		try {
			openConn();
			
			sql = "select * from users order by num desc";
			sql = "select * from admin_role order by num desc";
			
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AdminDTO dto = new AdminDTO();
				
				dto.setUserNo(rs.getInt("userNo"));
				dto.setUserId(rs.getString("userId"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setRoleCode(rs.getString("role_code"));
				dto.setRoleName(rs.getString("role_name"));
				dto.setCreatedAt(rs.getDate("created_At"));
				dto.setUpdatedAt(rs.getDate("updated_At"));
				
				list.add(dto);
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	} // getAdminList() 메서드 end
	
	// 추가하는 메서드
	public int insertAdmin(AdminDTO dto) {
		
		int result = 0, count = 0;
		
		try {
			openConn();

			sql = "insert into users values(seq_user_no.nextval, ?, ?, ?, ?, 'ADMIN', DEFAULT , ?, sysdate, sysdate)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getUserType());
			pstmt.setString(6, dto.getRoleCode());
			pstmt.setString(7, dto.getRoleName());
			pstmt.setDate(8, dto.getCreatedAt());
			pstmt.setDate(9, dto.getUpdatedAt());
			
			result = pstmt.executeUpdate();
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}  // insertAdmin() 메서드 end
	
	// 번호에 해당하는 관리자의 정보를 조회하는 메서드
	public AdminDTO contentAdmin(int no) {
		
		AdminDTO dto = null;
		
		try {
			openConn();
			
			sql = "select * from admin where num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new AdminDTO();
				
				dto.setUserNo(rs.getInt("userNo"));
				dto.setUserId(rs.getString("userId"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setRoleCode(rs.getString("roleCode"));
				dto.setRoleName(rs.getString("roleName"));
				dto.setCreatedAt(rs.getDate("createdAt"));
				dto.setUpdatedAt(rs.getDate("updatedAt"));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}  // contentAdmin() 메서드 end
	
	// 정보를 수정하는 메서드
	public int updateAdmin(AdminDTO dto) {
		
		int result = 0;
		
		try {
			openConn();
			
			sql = "update admin set password = ?, name = ?, email = ? where num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getEmail());
			pstmt.setInt(4, dto.getUserNo());
			
			result = pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	} // updateAdmin() 메서드 end
	
	// 번호에 해당하는 관리자를 삭제하는 메서드
	public int deleteAdmin(int no, String pwd) {
		
		int result = 0;
		
		try {
			openConn();
			
			sql = "select * from admin where num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(pwd.equals(rs.getString("pwd"))) {
					sql = "delete from admin where num = ?";
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setInt(1, no);
					
					result = pstmt.executeUpdate();
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
	} // deleteAdmin() 메서드 end
	
	// 번호를 재작업 하는 메서드
	public void updateSequence(int no) {
		
		try {
			openConn();
			
			sql = "update admin set num = num - 1 where num > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			closeConn(rs, pstmt, con);
		}
	} // updateSequence() 메서드 end
}