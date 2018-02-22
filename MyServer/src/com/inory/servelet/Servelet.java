package com.inory.servelet;

import com.inory.server.Request;
import com.inory.server.Response;

public abstract class Servelet {
	
	public void Service(Request request,Response response){
		try {
			this.doGet(request,response);
			this.doPost(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void doGet(Request request,Response response) throws Exception;
	public abstract void doPost(Request request,Response response) throws Exception;
}
