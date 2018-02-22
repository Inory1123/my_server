package com.inory.server;
/**
 * 请求并响应
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.inory.util.Util;


public class Server {

	private ServerSocket serverSocket ;
	private Socket socket ;
	private boolean isShutdown = false;
	
	/**
	 * 启动方法
	 */
	public void start(){
		start(8899);
	}
	
	/**
	 * 启动方法 带端口
	 */
	public void start(int port){
		try {
			serverSocket = new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			//e.printStackTrace();
			stop();
		}
	}
	
	/**
	 * 接收与处理请求
	 */
	private void receive(){
		try {
			while (!isShutdown) {
				new Thread(new Diapatcher(serverSocket.accept())).start();
			}
		} catch (IOException e) {
//			e.printStackTrace();
			stop();
		}
	}
	
	public void stop(){
		isShutdown = true;
		Util.close(serverSocket);
	}
	
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

}
