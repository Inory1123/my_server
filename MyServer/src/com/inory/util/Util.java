package com.inory.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Util {
	
	public static String readStringFromFile(String src) {
		String result = "";
		File file = new File(src);
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new FileReader(file));
			String mString = null ;
			while (null != (mString = bReader.readLine())) {
				result = result + mString + "\r\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			Util.close(bReader);
		}
		return result;
	}
	
	/**
	 * 工具类关闭流
	 * 可变参数： ... 只能放在最后一个位置
	 */
	public static void close (Closeable ... io){
		for(Closeable temp : io){
			if(null != temp){
				try {
					temp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 工具类关闭流
	 * 泛型方法
	 */
	public static <T extends Closeable> void closeAll (T ... io){
		for(Closeable temp : io){
			if(null != temp){
				try {
					temp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
