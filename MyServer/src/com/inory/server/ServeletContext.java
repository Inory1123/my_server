package com.inory.server;

import java.util.HashMap;
import java.util.Map;

public class ServeletContext {
	//为每一个Serverlet取一个别名
	private Map<String, String> servelet;
	private Map<String, String> mapping;
	
	public ServeletContext() {
		servelet = new HashMap<>();
		mapping = new HashMap<>();
	}
	
	public Map<String, String> getServelet() {
		return servelet;
	}
	public void setServelet(Map<String, String> servelet) {
		this.servelet = servelet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
	
}
