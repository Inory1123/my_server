package com.inory.servelet;

import com.inory.server.Request;
import com.inory.server.Response;

public class RegisterServelet extends Servelet{

	@Override
	public void doGet(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		// response.println(Util.readStringFromFile("/home/inory/桌面/a.html"));
		response.println("<html><head><title>返回注册</title>");
		response.println("</head><body>");
		response.println("你的用户名为："+ request.getParameter("uname"));
		response.println("</body><html>");
		
	}

}
