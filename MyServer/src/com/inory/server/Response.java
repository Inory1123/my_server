package com.inory.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.inory.util.Util;


/**
 * 封装响应信息
 * @author inory
 *
 */
public class Response {
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	//存储头信息
	private StringBuilder headInfo;
	//存储正文
	private StringBuilder content;
	//存储正文长度
	private int len = 0;
	
	//构建流
	BufferedWriter bWriter;
	
	private Response() {
		len = 0;
		headInfo = new StringBuilder();
		content = new StringBuilder();
	}
	public Response(OutputStream os) {
		this();
		 bWriter = new BufferedWriter(new OutputStreamWriter(os));
	}
	public Response(Socket socket) {
		this();
		 try {
			bWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			headInfo = null;
		}
	}
	
	/**
	 * 构建正文
	 */
	public Response print(String info){
		content.append(info);
		len = len + info.getBytes().length;
		return this;
	}
	/**
	 * 构建正文+回车
	 */
	public Response println(String info){
		content.append(info).append(CRLF);
		len = len + (info+CRLF).getBytes().length;
		return this;
	}
	
	
	/**
	 * 构建头信息
	 * @param code
	 */
	private void createHeadeInfo(int code){
		//1) http协议版本、状态代码、描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 500:
			headInfo.append("SERVER ERROR");
			break;
		}
		headInfo.append(CRLF);
		//2) 响应头(Response Head)
		headInfo.append("Server:inory Server/0.0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
		//正文长度：字节长度
		headInfo.append("Content-Length:").append(len).append(CRLF);
		//3)正文之前
		headInfo.append(CRLF);
	}
	
	void pushToClient(int code) {
		if (null == headInfo) {
			code = 500;
		}
		createHeadeInfo(code);
		System.out.println(headInfo.toString()+content.toString());
		try {
			bWriter.append(headInfo.toString());
			bWriter.append(content.toString());
			bWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void close(){
		Util.close(bWriter);
	}
	
	
}
