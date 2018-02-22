package com.inory.server;
/**
 * 封装请求方式
 * @author inory
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
	//请求方式
	private String method;
	//请求资源
	private String url;
	//请求参数
	private Map<String, List<String>> parameterMapValues;
	
	//内部
	public static final String CRLF = "\r\n";
	private InputStream iStream;
	private String requestInfo;
	
	private Request(){
		method = "";
		url = "";
		parameterMapValues = new HashMap<>();
		requestInfo = "";
	}
	public Request(InputStream iStream){
		this();
		this.iStream = iStream;
		
		//接收客户端的请求信息
		try {
			byte[] data = new byte[20480];
			int len;
			len = this.iStream.read(data);
			if (-1 == len) {
				return ;
			}
			requestInfo = new String(data,0,len).trim();
			System.out.println(requestInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//分析头信息
		parseHeadInfo();
	}
	
	public String getUrl(){
		return this.url;
	}
	
	private void parseHeadInfo(){
		if(null == requestInfo || (requestInfo.trim()).equals("")){
			return ;
		}
		
		/**
		 * 从信息的首行分解出：请求方式 请求路径 请求参数
		 *     如GET / HTTP/1.1
		 * 如果为post方式，请求参数可能在最后正文中
		 */
		
		String paramString = "";
		
		//1.获取请求方式
		String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
		int idx = requestInfo.indexOf("/");
		this.method = firstLine.substring(0,idx).trim();
		String urlstr = firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();
		if (this.method.equalsIgnoreCase("post")) {
			this.url = urlstr;
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF));
			
		}else if (this.method.equalsIgnoreCase("get")) {
			if (urlstr.contains("?")) {//是否存在参数
				String[] urlArray = urlstr.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];
			}else {
				this.url = urlstr;
			}
		}
		
		//2.将请求方式封装到Map中
		if (paramString.equals("")) {
			return;
		}
		parseParam(paramString);
	}
	
	private void parseParam(String paramString) {
		//分割 将字符串转成数组
		StringTokenizer tokenizer = new StringTokenizer(paramString, "&");
		while (tokenizer.hasMoreElements()) {
			String keyValue = (String) tokenizer.nextElement();
			String[] keyValues = keyValue.split("=");
			if(1 == keyValue.length()){
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues[1] = null;
			}
			
			String key = keyValues[0].trim();
			String value = null == keyValues[1] ? null : decode(keyValues[1].trim(), "utf-8");
			//转换成Map
			if (!parameterMapValues.containsKey(key)) {
				parameterMapValues.put(key, new ArrayList<>());
			}
			List<String> values = parameterMapValues.get(key);
			values.add(value);
		}
	}
	
	/**
	 * 解决中文
	 * @param value
	 * @param code
	 * @return
	 */
	private String decode(String value,String code){
		String result = null;
		try {
			result =java.net.URLDecoder.decode(value,code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据页面的name获取对应的值
	 */
	public String getParameter(String name){
		String[] values = getParameterValues(name);
		if (null == values) {
			return null;
		}else {
			return values[0];
		}
	}
	
	/**
	 * 根据页面的name获取对应的多个值
	 */
	public String[] getParameterValues(String name){
		List<String> values = null;
		if (null == (values = parameterMapValues.get(name))) {
			return null;
		}else {
			return values.toArray(new String[0]);
		}
	}
}




