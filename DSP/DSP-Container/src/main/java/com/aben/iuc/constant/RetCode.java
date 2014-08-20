package com.aben.iuc.constant;

public class RetCode {
	/**
	 * 系统内部错误
	 */
	public static final String SYS_ERROR="-1";
	/**
	 * 应用ID无效
	 */
	public static final  String APPID_ERROR="1";
	
	/**
	 * 验证码验证失败
	 */
	public static final String VALIDCODE_ERROR="2";
	
	/**
	 * 操作成功
	 */
	public static final String SUCESS="0";
	
	/**
	 * ip白名单验证失败
	 */
	public static final String IP_VALIDATE_ERROR="6";
	
	
	/**
	 * TICKET验证失败
	 */
	public static final String TICKET_VALIDATE_ERROR="3";
	
	/**
	 * 无操作权限
	 */
	public static final String NOT_PRO="4";
	
	/**
	 * 请求头不正确
	 */
	public static final String REQ_HEAD_ERRO="5";
	
	/**
	 * 无效的ttl
	 */
	public static final String TTL_ERROR="7";
}
