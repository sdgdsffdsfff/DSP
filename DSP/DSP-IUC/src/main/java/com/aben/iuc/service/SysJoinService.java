package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.OutSysJoin;
import com.aben.iuc.util.dwz.Page;

public interface SysJoinService {
	/**
	 * 系统接入
	 * 
	 * @param jogin
	 *            系统接入对象
	 */
	public void sysJoin(OutSysJoin join);

	/**
	 * 删除一个接入信息
	 * 
	 * @param id
	 *            接入ID
	 */
	public void delJoin(long id);

	/**
	 * 修改IP
	 * 
	 * @param ip
	 *            接入IP
	 */
	public void updateIp(long id,String ip);

	/**
	 * 根据系统模块ID获取接入信息
	 * 
	 * @param moudleid
	 *            模块ID
	 * @return
	 */
	public OutSysJoin getBySysMoudleId(long moudleid);
	
	/**
	 * 查询全量的列表信息
	 * @param page 分页对象
	 * @return
	 */
	public List<OutSysJoin> findAll(Page page);
	
	/**
	 * 根据appid获取应用接入信息
	 * @param appid 应用ID
	 * @return
	 */
	public OutSysJoin getByAppId(String appid);
	
	//by me 
	//根据id获取OutSysJoin对象
	public OutSysJoin getSysJoinById(Long id);
}
