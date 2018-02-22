package com.inory.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler{

	private List<Entity> entityList;
	private List<Mapping> mappingList;
	
	private Entity entity;
	private Mapping mapping;
	
	private String beginTag;
	private boolean isMap;
	
	@Override
	public void startDocument() throws SAXException {
		//文档解析开始
		entityList = new ArrayList<>();
		mappingList = new ArrayList<>();
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//开始元素
		if (null !=qName) {
			beginTag = qName;
			if (qName.equals("servelet")) {
				isMap = false;
				entity = new Entity();
			}else if (qName.equals("servelet-mapping")) {
				isMap = true;
				mapping = new Mapping();
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//处理内容
		if (null != beginTag) {
			String str = new String(ch,start,length);
			if (isMap) {
				if (beginTag.equals("servelet-name")) {
					mapping.setServeletName(str);
				}else if (beginTag.equals("url-pattern")) {
					mapping.getUrlPatterns().add(str);
				}
			}else if (!isMap) {
				if (beginTag.equals("servelet-name")) {
					entity.setServeletName(str);
				}else if (beginTag.equals("servelet-class")) {
					entity.setClassName(str);
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//结束元素
		if (null != qName) {
			if (qName.equals("servelet")) {
				entityList.add(entity);
			}else if (qName.equals("servelet-mapping")) {
				mappingList.add(mapping);
			}
		}
		beginTag = null;
	}

	@Override
	public void endDocument() throws SAXException {
		//文档解析结束
	}


	public List<Entity> getEntityList() {
		return entityList;
	}


	public List<Mapping> getMappingList() {
		return mappingList;
	}
	
	
	
//	public static void main(String[] args) {
//		//获取解析工厂
//		SAXParserFactory factory = SAXParserFactory.newInstance();
//		//获取解析器
//		SAXParser sax = null;
//		try {
//			sax = factory.newSAXParser();
//		} catch (ParserConfigurationException | SAXException e) {
//			e.printStackTrace();
//		}
//		if (null == sax) {
//			return ;
//		}
//		//开始解析
//		WebHandler webHandler = new WebHandler();
//		try {
//			sax.parse(Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream("com/inory/server/demo04/web.xml"), webHandler);
//		} catch (SAXException | IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(webHandler.mappingList.get(0).getServeletName());
//		System.out.println(webHandler.mappingList.get(0).getUrlPatterns());
//		System.out.println(webHandler.entityList);
//	}
}
