package com.global.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.ActionFactory;
import com.global.action.View;
import com.global.utils.RequestUtils;

public class FrontController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1934459167346141964L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글 처리 작업 진행
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Properties prop = new Properties();
		
		String fileName = FrontController.class.getResource("/com/common/mapping/mapping.properties").getPath();
		
		prop.load(new FileReader(fileName));
		// URI와 커맨드를 추출 
		String uri = RequestUtils.extractUri(request);
		String command = RequestUtils.extractCommand(uri, request.getContextPath());

		// 커맨드에 해당하는 클래스 이름을 가져온다.
		String namePath = prop.getProperty(command);

		System.out.println("Command: " + command);
		System.out.println("NamePath: " + namePath);
		View view = null;
		if (namePath.endsWith(".jsp")) {
			// 비즈니스 로직 메서드 호출 및 결과 처리
			view = new View(namePath).setUrl(namePath);
		} else {
			// 동적으로 클래스 로딩 및 인스턴스 생성
			Action action = ActionFactory.createActionInstance(namePath);

			if (action == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			// 비즈니스 로직 메서드 호출 및 결과 처리
			view = action.execute(request, response);
		}

		if (view != null) {
			view.render(request, response);
		}

	}
}
