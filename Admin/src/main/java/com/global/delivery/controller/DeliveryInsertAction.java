package com.global.delivery.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.delivery.model.DeliveryDAO;
import com.global.delivery.model.DeliveryDTO;
import com.global.utils.ScriptUtil;

public class DeliveryInsertAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int orderNo = Integer.parseInt(request.getParameter("no").trim());
		
		DeliveryDAO dao = DeliveryDAO.getInstance();
		DeliveryDTO dto = new DeliveryDTO();
		
		int res = dao.InsertDelivery(dto,orderNo);
		
		 if (res > 0) {
			ScriptUtil.sendScript(response, "배송 성공","delivery_list.do");
				
	      } 
	      
	      else {
	      	ScriptUtil.sendScript(response, "배송 실패", null);
	      }
		return null;
	}

}
