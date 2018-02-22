package com.inory.server;

import java.io.IOException;
import java.net.Socket;

import com.inory.servelet.Servelet;
import com.inory.util.Util;


/**
 * 一个请求或响应，就一个此对象
 * @author inory
 *
 */
public class Diapatcher implements Runnable{
	private Socket socket;
	private Request request;
	private Response response;

	private int code = 200;
	
	public Diapatcher(Socket socket) {
		this.socket = socket;
		try {
			//请求
			this.request = new Request(socket.getInputStream());
			//响应
			this.response = new Response(socket.getOutputStream());
		} catch (IOException e) {
//			e.printStackTrace();
			code = 500;
			return ;
		}
	}
	
	@Override
	public void run() {
		if(request.getUrl().trim().equals("/favicon.ico")){
			return ;
		}
		Servelet servelet = WebApp.getServerlet(request.getUrl());
		if (null == servelet) {
			code = 404;
		}else {
			servelet.Service(request, response);
		}
		response.pushToClient(code);
		Util.close(socket);
	}

}
