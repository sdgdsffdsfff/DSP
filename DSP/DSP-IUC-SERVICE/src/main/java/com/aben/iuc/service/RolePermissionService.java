 
 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.RolePermission;
 

public interface RolePermissionService {
	void save(RolePermission rolePermission);
	
	RolePermission get(Long id);
	
	void update(RolePermission rolePermission);
	
	void delete(Long id);

	List<RolePermission> findByRoleId(Long roleId);
	
	//by me 
	//得到所有的RolePermission
	List<RolePermission> findAll();
}
