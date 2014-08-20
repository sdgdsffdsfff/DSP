package com.aben.iuc.inter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aben.iuc.constant.Constant;
import com.aben.iuc.constant.RetCode;
import com.aben.iuc.entity.main.OutSysJoin;
import com.aben.iuc.service.SysJoinService;
import com.aben.iuc.util.MD5Util;
import com.alibaba.fastjson.JSONObject;

public class BaseInterFace {
	
	private static final Logger log = LoggerFactory
			.getLogger(BaseInterFace.class);

	@Autowired
	protected SysJoinService sysJoginService;
	 
	
	/**
	 * 验证请求头部信息
	 * 
	 * @param head
	 * @return
	 */
	public boolean validateReq(JSONObject req, OutSysJoin sys,
			JSONObject response, HttpServletRequest request) {
 
		
		JSONObject head = req.getJSONObject("head");
		JSONObject body = req.getJSONObject("body");
		
		String address = request.getHeader("x-forwarded-for");
		if (log.isDebugEnabled()) {
			log.debug("x-forwarded-for: " + address);
		}
		if (address != null) {
			address = address.split(",")[0];
		}
//		if (address == null) {
//			address = request.getHeader("http_client_ip");
//			if (log.isDebugEnabled()) {
//				log.debug("http_client_ip: " + address);
//			}
//		}
		if (address == null) {
			address = request.getRemoteAddr();
		}
		String reqIp = address;

		if (log.isDebugEnabled()) {
			log.debug("reqIp is :" + reqIp);
		}
		if (head == null) {
			response.put(Constant.RETCODE, RetCode.REQ_HEAD_ERRO);
			return false;
		}
		String appid = head.getString("appid");
		if (appid == null) {
			response.put(Constant.RETCODE, RetCode.APPID_ERROR);
			return false;
		}
		if (sys == null) {
			response.put(Constant.RETCODE, RetCode.APPID_ERROR);
			return false;
		}
		String validCode = head.getString("validcode");
		String ttl = head.getString("ttl");
		if (log.isDebugEnabled()) {
			log.debug("ttl is : " + ttl);
		}
		if (ttl == null) {
			response.put(Constant.RETCODE, RetCode.TTL_ERROR);
			return false;
		} else {
			try {
				long tt = Long.valueOf(ttl);
				if (System.currentTimeMillis() - tt > 3 * 60 * 1000) {
					throw new Exception("ttl invalid");
				}
			} catch (Exception ex) {
				log.error("ttl error!", ex);
				response.put(Constant.RETCODE, RetCode.TTL_ERROR);
				return false;
			}
		}
		String _validCode = MD5Util.Md5(body.toJSONString() + sys.getAppkey()
				+ ttl);
		if (log.isDebugEnabled()) {
			log.debug("req validcode: " + validCode + " _validcode:"
					+ _validCode);
		}
		if (!_validCode.equals(validCode)) {
			response.put(Constant.RETCODE, RetCode.VALIDCODE_ERROR);
			return false;
		}

		String ip = sys.getJoinip();
		if (ip == null)
			ip = "";
		if (log.isDebugEnabled()) {
			log.debug("blank ip :" + ip);
		}
		if (!(ip.indexOf(reqIp) >= 0)) {
			response.put(Constant.RETCODE, RetCode.IP_VALIDATE_ERROR);
			return false;
		}
		return true;
	}

	/**
	 * 获取请求json对象
	 * 
	 * @param reqType
	 *            请求参数类型
	 * @param request
	 *            http请求对象
	 * @return
	 * @throws IOException
	 */
	public JSONObject getReqJson(String reqType, HttpServletRequest request)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("reqType:" + reqType);
		}
		JSONObject obj = null;
		if ("paramter".equals(reqType)) {
			JSONObject body = JSONObject.parseObject(request
					.getParameter("body"));
			JSONObject head = JSONObject.parseObject(request
					.getParameter("head"));

			obj = new JSONObject();
			obj.put("body", body);
			obj.put("head", head);
			if (log.isDebugEnabled()) {
				log.debug("request str is:" + obj.toJSONString());
			}

		} else {
			int contentLength = request.getContentLength();
			InputStream stream = null;
			try {
				stream = request.getInputStream();
				byte[] by = new byte[contentLength];
				stream.read(by);
				String requestStr = new String(by);
				if (log.isDebugEnabled()) {
					log.debug("request str is :" + requestStr);
				}
				obj = JSONObject.parseObject(new String(by));
			} finally {
				if (stream != null)
					stream.close();
			}
		}

		JSONObject head = obj.getJSONObject("head");
		if (head == null) {
			throw new Exception("head is null");
		}
		return obj;
	}
}
