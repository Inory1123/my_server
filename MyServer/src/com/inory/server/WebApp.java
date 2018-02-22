package com.inory.server;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.inory.servelet.Servelet;

public class WebApp {
	private static ServeletContext context;
	
	static{
		//获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 获取解析器
		SAXParser sax = null;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		// 开始解析
		WebHandler webHandler = new WebHandler();
		try {
			sax.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("WEB_INFO/web.xml"), webHandler);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		//将List转为Map
		context = new ServeletContext();
		
		Map<String, String> mapping = context.getMapping();
		//url-pattern --> servelet-name
		for(Mapping temp :webHandler.getMappingList()){
			for(String url : temp.getUrlPatterns()){
				mapping.put(url, temp.getServeletName());
			}
		}
		Map<String,String> servelet = context.getServelet();
		//servelet-name --> servelet-class
		for(Entity entity : webHandler.getEntityList()){
			servelet.put(entity.getServeletName(), entity.getClassName());
		}
	}
	
	public static Servelet getServerlet(String url){
		Servelet servelet = null;
		url = url.trim();
		if (null == url || url.equals("") ) {
			return null;
		}
		
		//根据字符串(完整路径)创建对象
		String name = context.getServelet().get(context.getMapping().get(url));
		if (null == name) {
			return null;
		}
		try {
			servelet = (Servelet)Class.forName(name).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return servelet;
		//return context.getServelet().get(context.getMapping().get(url));
	}
}
