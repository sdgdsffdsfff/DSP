package com.aben.iuc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
import com.aben.iuc.dao.SysJoinDao;
import com.aben.iuc.entity.main.OutSysJoin;
import com.aben.iuc.service.SysJoinService;
import com.aben.iuc.util.dwz.Page;
import com.aben.iuc.util.dwz.PageUtils;

@Service
public class SysJoinServiceImpl implements SysJoinService {
	
	private static final Logger log = LoggerFactory
	.getLogger(SysJoinServiceImpl.class);
	
	@Autowired
	private SysJoinDao sysJoinDao;

	@Override
	@Transactional
	public void delJoin(long id) {
		if (log.isDebugEnabled()){
			log.debug("start del join info id is: "+id);
		}
		sysJoinDao.delete(id);
	}

	@Override
	public OutSysJoin getBySysMoudleId(long moudleid) {
		// TODO Auto-generated method stub
		return sysJoinDao.getByMoudleId(moudleid);
	}

	@Override
	@Transactional
	public void sysJoin(OutSysJoin join) {
		// TODO Auto-generated method stub
		sysJoinDao.save(join);
	}

	@Override
	@Transactional
	public void updateIp(long id,String ip) {
		sysJoinDao.updateIp(id, ip);
	}

	@Override
	public List<OutSysJoin> findAll(Page page) {
		org.springframework.data.domain.Page<OutSysJoin> springDataPage = sysJoinDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public OutSysJoin getByAppId(String appid) {
		// TODO Auto-generated method stub
		return sysJoinDao.getByAppId(appid);
	}

	@Override
	public OutSysJoin getSysJoinById(Long id) {
		// TODO Auto-generated method stub
		return sysJoinDao.findOne(id);
	}

}
