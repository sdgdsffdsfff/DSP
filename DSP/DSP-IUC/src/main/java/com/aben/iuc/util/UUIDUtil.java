package com.aben.iuc.util;

import java.util.UUID;

/**
 * UUID生成工具类
 * 
 * @author nijiangli
 * 
 */
public class UUIDUtil {
	/**
	 * 生成新的UUID
	 * 
	 * @return
	 */
	public static String getUUid() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		return uuidStr.replace("-", "").toUpperCase();
	}

}
