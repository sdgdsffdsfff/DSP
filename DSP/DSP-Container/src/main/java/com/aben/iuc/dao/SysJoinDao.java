package com.aben.iuc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aben.iuc.entity.main.OutSysJoin;

public interface SysJoinDao extends JpaRepository<OutSysJoin, Long> {
	/**
	 * 根据模块ID获取接入信息
	 * 
	 * @param moudleId
	 *            模块ID
	 * @return
	 */
	@Query("from OutSysJoin o where o.moudle.id=?")
	public OutSysJoin getByMoudleId(long moudleId);

	/**
	 * 更新指定接入的接入IP信息
	 * 
	 * @param id
	 *            接入ID
	 * @param ipStr
	 *            ip字符串
	 */
	@Modifying
	@Query("update OutSysJoin o set o.joinip=:ip where o.id=:id")
	public void updateIp(@Param("id") long id, @Param("ip") String ipStr);

	/**
	 * 根据appid获取应用接入信息
	 * 
	 * @param appid
	 *            应用id
	 * @return
	 */
	@Query("from OutSysJoin o where o.appid=?")
	public OutSysJoin getByAppId(String appid);
}
