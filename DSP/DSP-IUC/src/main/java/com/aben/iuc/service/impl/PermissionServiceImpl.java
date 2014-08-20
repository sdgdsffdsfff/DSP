 
package com.aben.iuc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.PermissionDao;
import com.aben.iuc.entity.main.Permission;
import com.aben.iuc.service.PermissionService;

 
@Service
@Transactional(readOnly=true)
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDao permissionDao;

	/**   
	 * @param permission  
	 * @see com.aben.iuc.service.PermissionService#save(com.aben.iuc.entity.main.Permission)  
	 */
	@Transactional
	@Override
	public void save(Permission permission) {
		permissionDao.save(permission);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.aben.iuc.service.PermissionService#get(java.lang.Long)  
	 */
	@Override
	public Permission get(Long id) {
		return permissionDao.findOne(id);
	}

	/**   
	 * @param permission  
	 * @see com.aben.iuc.service.PermissionService#update(com.aben.iuc.entity.main.Permission)  
	 */
	@Transactional
	@Override
	public void update(Permission permission) {
		permissionDao.save(permission);
	}

	/**   
	 * @param id  
	 * @see com.aben.iuc.service.PermissionService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		permissionDao.delete(id);
	}
}
