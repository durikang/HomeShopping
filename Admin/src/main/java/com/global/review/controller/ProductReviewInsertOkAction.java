package com.global.review.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.UsersDTO;
import com.global.review.model.ProductReviewDAO;
import com.global.review.model.ProductReviewDTO;
import com.global.utils.ScriptUtil;

public class ProductReviewInsertOkAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ProductReviewDTO review = new ProductReviewDTO();
		
		// 세션에서 UserDTO 객체를 가져옵니다.
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            // 유저 정보가 없을 경우, 에러 메시지를 출력하고 리스트 페이지로 리다이렉트
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "productReviewList.do");
            return null;
        }
        
        // 유저 번호를 가져와서 BoardDTO에 설정합니다.
        review.setUser_No(user.getUserNo());
		String categoryNo = request.getParameter("categoryNo");
		String title = request.getParameter("title").trim();
		String content = request.getParameter("content").trim();
		int reviewStar = Integer.parseInt(request.getParameter("reviewStar"));

		review.setCategory_no(categoryNo);
		review.setTitle(title);
		review.setContent(content);
		review.setRating(reviewStar);
		
		int check = ProductReviewDAO.getInstance().insertReview(review, user.getUserNo());
		
		if(check > 0) {
			ScriptUtil.sendScript(response, "리뷰 작성 성공!", "productReviewList.do");
		} else {
			ScriptUtil.sendScript(response, "리뷰 작성 실패..", null);
		}
		
		return null;
	}

}
