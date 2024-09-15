package com.global.event.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.event.model.Event;
import com.global.event.model.EventDAO;
import com.global.utils.PageInfo;
import com.global.utils.PageUtil;
import com.global.utils.RequestParameterUtils;

public class EventListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		EventDAO dao = EventDAO.getInstance();
		
		String eventType = RequestParameterUtils.parseString(request.getParameter("eventType"), "");  // 이벤트 타입 (banner, coupon 등)
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"),1);
        String subtitle = request.getParameter("subtitle");
        // 이벤트 리스트 갯수 가져오기
        int eventListCount = dao.getEventCount(eventType);  // 이벤트 타입에 따라 다르게 처리
        int eventLimit = 20;
        int pageLimit = 10;
        
        PageInfo pi = PageUtil.getPageInfo(request, eventListCount, eventLimit, pageLimit);
		
        List<? extends Event> eventList = dao.getEventList(eventType);  // 이벤트 타입에 따라 다르게 처리
        
        // request에 필요한 속성 설정
        request.setAttribute("count", eventListCount);
        request.setAttribute("list", eventList);
        request.setAttribute("eventType", eventType);  // 이벤트 타입 전달
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pi", pi);  // PageInfo 객체 전달
        request.setAttribute("subtitle", subtitle);
        request.setAttribute("address", "eventList.do");

        return new View("main.go").setUrl("/views/event/eventList.jsp");
	}
}
