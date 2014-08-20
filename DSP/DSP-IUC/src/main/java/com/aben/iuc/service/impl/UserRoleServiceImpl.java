  
package com.aben.iuc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.UserRoleDao;
import com.aben.iuc.entity.main.UserRole;
import com.aben.iuc.service.UserRoleService;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-7 下午5:09:50 
 */
@Service
@Transactional(readOnly=true)
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
//	@Autowired
//	public UserRoleServiceImpl(UserRoleDao userRoleDao) {
//		this.userRoleDao = userRoleDao;
//	}

	@Transactional
	@Override
	public void save(UserRole userRole) {
		userRoleDao.save(userRole);
	}

	@Transactional
	@Override
	public void delete(Long userRoleId) {
		userRoleDao.delete(userRoleId);
	}

	/**   
	 * @param userId
	 * @return  
	 * @see com.aben.iuc.service.UserRoleService#find(Long)  
	 */
	public List<UserRole> find(Long userId) {
		return userRoleDao.findByUserId(userId);
	}



	@Override
	public List<UserRole> findUserRolesByRoleId(Long roleId) {
		return userRoleDao.findUserRolesByRoleId(roleId);
	}


	

}
