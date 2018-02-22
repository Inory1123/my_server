package com.inory.server;
/**
 * 存储mapping
 * @author inory
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Mapping {
	private String serveletName;
	private List<String> urlPatterns;
	
	public Mapping() {
		urlPatterns = new ArrayList<>();
	}
	
	public String getServeletName() {
		return serveletName;
	}
	public void setServeletName(String serveletName) {
		this.serveletName = serveletName;
	}
	public List<String> getUrlPatterns() {
		return urlPatterns;
	}
	public void setUrlPatterns(List<String> urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	
	
}
