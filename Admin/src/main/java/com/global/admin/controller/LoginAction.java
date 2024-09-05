package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class LoginAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		return new View("main").setUrl("/views/member/login.jsp");
	}

}
