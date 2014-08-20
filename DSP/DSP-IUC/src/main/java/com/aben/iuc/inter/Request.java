package com.aben.iuc.inter;

import com.aben.iuc.util.MD5Util;
import com.alibaba.fastjson.JSONObject;

public class Request {
	private JSONObject head;
	private JSONObject body;

	public JSONObject getHead() {
		return head;
	}

	public JSONObject getBody() {
		return body;
	}

	public String getHead(String name) {
		return head.getString(name);
	}

	public String getBody(String name) {
		return body.getString(name);
	}
	
	public static void main(String[] st){
//		JSONObject obj=new JSONObject();
//		obj.put("appid","123456");
//		obj.put("validCode","mmmkkk");
//		Request request=new Request();
//		request.head=obj;
//		obj=new JSONObject();
//		obj.put("ticket","mm");
//		request.body=obj;
////		obj=new JSONObject();
//	    System.out.println(JSONObject.toJSONString(request));
		long ttl=System.currentTimeMillis();
		System.out.println(ttl);
		//body={"ticket":"TGT-1-lhPTmNnBzZNdzNi6bJ07MPe62slcx47MjLdodTHdqgPFY3t4EU-sso"}&head={"appid":"C4705923DEDC464186EBA08D1A066AB3","ttl":"1377757846805","validcode":"68bbfb896d374a9dbbf1d55f53018f1f"}
//		body={"tgt":"TGT-1-BqCH7aAikiyUMvSzwSZNhKEFuRwDWNGgKGeMHcBIWk6sqsQc7x-sso"}&head={"appid":"C4705923DEDC464186EBA08D1A066AB3","ttl":"1377762859639","validcode":"9cf0c989d784bde4d12c4b0e738d776b"}
		
//		System.out.println(MD5Util.Md5("{\"ticket\":\"ST-2-xlIfyoTl3jepTVAerpkl-sso\"}0EF71133212D4C739F4241DA139305D6"+ttl));
		System.out.println(MD5Util.Md5("{\"tgt\":\"TGT-1-BqCH7aAikiyUMvSzwSZNhKEFuRwDWNGgKGeMHcBIWk6sqsQc7x-sso\"}501C8DE47C614D5AA24E3DBB00C5C88A"+ttl));
		
		String ud="{\"body\":{\"ticket\":\"mm\"},\"head\":{\"appid\":\"123456\",\"validcode\":\"mmmkkk\"}}";
		Request requ=JSONObject.parseObject(ud, Request.class);
		System.out.println(requ.body);
	}
}
