package com.global.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductImageDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 싱글톤
	public static ProductImageDAO instance=null;

	// 기본생성자
	public ProductImageDAO() {}


	public static ProductImageDAO getInstance() {

		if (instance == null) {
			instance = new ProductImageDAO();
		}
		return instance;
	}

	
	public void openConn() {

		try {

			// 1단계 : JNDI 서버 객체 생성
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext 객체는
			// 네이밍 서비스를 이용하기 위한 시작점이 됨.
			Context initCtx = new InitialContext();

			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현재 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() end

	// jsp_bbs 테이블의 전체 게시물을 조회하는 메서드.

	public void closeConn(PreparedStatement pstmt, Connection con) {

		try {

			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() end


	public int insertImgProduct(ProductImageDTO image) {

		int result = 0;
		int paramIndex=1;
		try {
		
		openConn();
		
		sql = "INSERT INTO PRODUCT_IMAGE VALUES(SEQ_IMAGE_NO.NEXTVAL,SEQ_PRODUCT_NO.currval,?,null)";
		
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(paramIndex++, image.getImage_url());
		
		result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}


	public int insertNullProduct(ProductDTO product) {
		int result = 0;

		try {
		
		openConn();
		
		sql = "INSERT INTO PRODUCT_IMAGE VALUES(SEQ_PRODUCT_NO.NEXTVAL,?,'',?)";
		
		pstmt = con.prepareStatement(sql);
		
		
		pstmt.setInt(1, product.getProduct_no());
		pstmt.setString(2, product.getDescription());
		
		result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
	}


	public int modifyNullProduct(ProductDTO product) {
		
		int img = 0;
		
		try {

		openConn();
		
		sql = "UPDATE PRODUCT_IMAGE SET IMAGE_URL = ? WHERE PRODUCT_NO = ?";
		
		pstmt = con.prepareStatement(sql);
			
		pstmt.setString(1, "-");
		pstmt.setInt(2, product.getProduct_no());
		
		img = pstmt.executeUpdate();
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
			
		} 
	
		return img;
	}


	public int modifyImgProduct(ProductImageDTO image) {
		
		int img = 0;
		
		try {

		openConn();
		
		sql = "UPDATE PRODUCT_IMAGE SET IMAGE_URL = ? WHERE PRODUCT_NO = ?";
		
		pstmt = con.prepareStatement(sql);
			
		pstmt.setString(1, image.getImage_url());
		pstmt.setInt(2, image.getProudct_no());
		
		img = pstmt.executeUpdate();
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
			
		} 
	
		return img;
	}


	public int DeleteProductImage(int product_no) {
		
			
			int check = 0;
			
			try {

			openConn();
			
			sql = "SELECT * FROM PRODUCT_IMAGE WHERE PRODUCT_NO = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, product_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sql = "DELETE FROM PRODUCT_IMAGE WHERE PRODUCT_NO = ?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, product_no);
				
				check = pstmt.executeUpdate();
			}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
				
			}
			
			
			
			
			
			return check;
		}


}
