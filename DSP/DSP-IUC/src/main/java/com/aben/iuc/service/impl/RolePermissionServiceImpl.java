 
package com.aben.iuc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.RolePermissionDao;
import com.aben.iuc.entity.main.RolePermission;
import com.aben.iuc.service.RolePermissionService;

 
@Service
@Transactional(readOnly=true)
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	private RolePermissionDao rolePermissionDao;

	/**   
	 * @param rolePermission  
	 * @see com.aben.iuc.service.RolePermissionService#save(com.aben.iuc.entity.main.RolePermission)  
	 */
	@Transactional
	@Override
	public void save(RolePermission rolePermission) {
		rolePermissionDao.save(rolePermission);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.aben.iuc.service.RolePermissionService#get(java.lang.Long)  
	 */
	@Override
	public RolePermission get(Long id) {
		return rolePermissionDao.findOne(id);
	}

	/**   
	 * @param rolePermission  
	 * @see com.aben.iuc.service.RolePermissionService#update(com.aben.iuc.entity.main.RolePermission)  
	 */
	@Transactional
	@Override
	public void update(RolePermission rolePermission) {
		rolePermissionDao.save(rolePermission);
	}

	/**   
	 * @param id  
	 * @see com.aben.iuc.service.RolePermissionService#delete(java.lang.Long)  
	 */
	@Transactional
	@Override
	public void delete(Long id) {
		rolePermissionDao.delete(id);
	}

	/**   
	 * @param roleId
	 * @return  
	 * @see com.aben.iuc.service.RolePermissionService#findByRoleId(java.lang.Long)  
	 */
	@Override
	public List<RolePermission> findByRoleId(Long roleId) {
		return rolePermissionDao.findByRoleId(roleId);
	}

	@Override
	public List<RolePermission> findAll() {
		// TODO Auto-generated method stub
		return rolePermissionDao.findAll();
	}

}
