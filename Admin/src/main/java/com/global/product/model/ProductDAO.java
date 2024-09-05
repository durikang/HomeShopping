package com.global.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.global.admin.model.AdminDTO;

public class ProductDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 싱글톤
	public static ProductDAO instance=null;

	// 기본생성자
	public ProductDAO() {}


	public static ProductDAO getInstance() {

		if (instance == null) {
			instance = new ProductDAO();
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


	public int insertProduct(ProductDTO dto) {

		
		int result = 0;
		int paramIndex = 1;
		
		try {
		
		openConn();
		

		
		sql = "INSERT INTO PRODUCT VALUES(SEQ_PRODUCT_NO.NEXTVAL,?,?,?,?,?,DEFAULT,SYSDATE,null, 'N',DEFAULT,?)";
										
		
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(paramIndex++, dto.getCategory_no());
		pstmt.setString(paramIndex++, dto.getName());
		pstmt.setString(paramIndex++, dto.getDescription());
		pstmt.setInt(paramIndex++, dto.getPrice());
		pstmt.setInt(paramIndex++, dto.getStock_quantity());
		pstmt.setInt(paramIndex++, dto.getUser_no());
		
		result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
	}


	public ProductDTO getProduct(int product_no) {
		ProductDTO product = null;
		try {
			openConn();
			
			sql = "SELECT * FROM PRODUCT WHERE PRODUCT_NO = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, product_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = new ProductDTO();
				product.setProduct_no(rs.getInt(1));
				product.setCategory_no(rs.getString(2));
				product.setName(rs.getString(3));
				product.setDescription(rs.getString(4));
				product.setPrice(rs.getInt(5));
				product.setStock_quantity(rs.getInt(6));
				product.setViews(rs.getInt(7));
				product.setCreated_at(rs.getDate(8));
				product.setUpdated_at(rs.getDate(9));
				product.setIs_deleted(rs.getString(10));
				product.setTotal_sales(rs.getInt(11));
				product.setUser_no(rs.getInt(12));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}


	public List<ProductDTO> getProudctList() {
		
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		
 		
		try {

		openConn();
		
		sql = "SELECT * "
				+ "FROM PRODUCT "
				+ "JOIN PRODUCT_IMAGE using(PRODUCT_NO) ";
		
		pstmt = con.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			ProductDTO dto = new ProductDTO();
			
			
			dto.setProduct_no(rs.getInt("product_no"));
			dto.setCategory_no(rs.getString("category_no"));
			dto.setImage_url(rs.getString("image_url"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			dto.setPrice(rs.getInt("price"));
			dto.setStock_quantity(rs.getInt("stock_quantity"));
			dto.setViews(rs.getInt("views"));
			dto.setCreated_at(rs.getDate("created_at"));
			dto.setUpdated_at(rs.getDate("updated_at"));
			dto.setIs_deleted(rs.getString("is_deleted"));
			dto.setTotal_sales(rs.getInt("total_sales"));
			dto.setUser_no(rs.getInt("user_no"));
			
			list.add(dto);
			
		}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
			
		}
		
		
		return list;
		
	}


	public ProductDTO getProductContent(String product_no) {
		
		ProductDTO dto = null;
		
		try {

		openConn();

		
		
		sql = "SELECT * "
				+ "FROM PRODUCT "
				+ "JOIN PRODUCT_IMAGE using(PRODUCT_NO) "
				+ "WHERE PRODUCT_NO = ?";
//		sql = "SELECT P.PRODUCT_NO, P.CATEGORY_NO, I.IMAGE_URL, P.NAME, P.DESCRIPTION,"
//				+ "P.PRICE, P.STOCK_QUANTITY, P.VIEWS, P.CREATED_AT, P.UPDATED_AT, P.IS_DELETED, P.TOTAL_SALES"
//				+ "FROM PRODUCT P, PRODUCT_IMAGE I WHERE P.PRODUCT_NO = I.PRODUCT_NO AND P.PRODUCT_NO = ?";
		
		
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, product_no);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			
			dto = new ProductDTO();
			
			dto.setProduct_no(rs.getInt("product_no"));
			dto.setCategory_no(rs.getString("category_no"));
			dto.setName(rs.getString("name"));
			dto.setImage_url(rs.getString("image_url"));
			dto.setDescription(rs.getString("description"));
			dto.setPrice(rs.getInt("price"));
			dto.setStock_quantity(rs.getInt("stock_quantity"));
			dto.setViews(rs.getInt("views"));
			dto.setCreated_at(rs.getDate("created_at"));
			dto.setUpdated_at(rs.getDate("UPDATED_AT"));
			dto.setIs_deleted(rs.getString("is_deleted"));
			dto.setTotal_sales(rs.getInt("total_sales"));
			dto.setUser_no(rs.getInt("user_no"));
		}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return dto;
	}


	public int getProductListCount() {
		int count = 0;
		
		try {
		
			openConn();
			
			sql = "SELECT COUNT(*) FROM PRODUCT ORDER BY 1";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	
	}


	public List<ProductDTO> getProductPage(int currentPage, int boardLimit) {

			
			List<ProductDTO> list = new ArrayList<>();
			
			try {

		    int startRow = (currentPage - 1) * boardLimit + 1;
		    int endRow = startRow + boardLimit - 1;
				
			openConn();
			
			sql = "SELECT COUNT(*) FROM PRODUCT ORDER BY 1";

			pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, startRow);
	        pstmt.setInt(2, endRow);
			
	        rs = pstmt.executeQuery();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
		        closeConn(rs, pstmt, con);
		    }
			
			
			
			return list;
	}


}
