package com.aben.iuc.util;

import java.io.File;

/**
 * 字符串处理
 * 
 * @author nijiangli
 * 
 */
public class StringUtils {
	public static final String chinese = "[\u0391-\uFFE5]";

	/**
	 * @todo: {对象转为字符串，为空返回null}
	 * 
	 * @throws
	 */
	public static String ObjectToStr(Object str) {
		String string = null;
		if (str != null) {
			string = str.toString();
		}
		return string;
	}

	/**
	 * @Title: ObjectToStrNotEmpty
	 * @Description: TODO(对象转为字符串，为空转换成空字符串)
	 * @param str
	 * @return
	 */
	public static String ObjectToStrNotEmpty(Object str) {
		return str == null ? "" : str.toString();
	}

	/**
	 * @todo: {判断字符串是否非空/非空字符串}
	 * 
	 * @throws
	 */
	public static boolean isNotEmpty(String str) {
		boolean result = false;
		if (str != null && str.length() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 判定字符串是否为空
	 * 
	 * @param str
	 *            字符串信息
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件是否为空
	 * 
	 * @param str
	 *            字符串信息
	 * @return
	 */
	public static boolean isEmpty(File... files) {
		if (files == null) {
			return true;
		} else {
			File file = null;
			for (int i = 0, length = files.length; i < length; i++) {
				file = files[i];
				if (file == null || !(file.isDirectory() || file.isFile())) {
					file = null;
					return true;
				}
			}
			file = null;
		}
		return false;
	}

	public static int strLength(String str) {
		int valueLength = 0;
		if (str == null)
			return 0;
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static void main(String[] st) {
		System.out.println(strLength("a1"));
	}
	
	//拼接字符串
	
}
