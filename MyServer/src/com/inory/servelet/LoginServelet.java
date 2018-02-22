package com.inory.servelet;

import com.inory.server.Request;
import com.inory.server.Response;

public class LoginServelet extends Servelet{

	@Override
	public void doGet(Request request, Response response) throws Exception {
		String name = request.getParameter("uname");
		String pwd = request.getParameter("upwd");
		if (null == name || null == pwd) {
			response.println("用户名/密码不能为空");
		}else if(login(name, pwd)){
			response.println("登录成功");
		}else {
			response.println("登录失败");
		}
	}
	
	private boolean login(String name,String pwd) {
		return name.equals("inory") && pwd.equals("1123");
	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
