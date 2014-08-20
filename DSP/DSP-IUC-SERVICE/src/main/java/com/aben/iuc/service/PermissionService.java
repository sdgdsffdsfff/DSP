 
 
package com.aben.iuc.service;

import com.aben.iuc.entity.main.Permission;

 
public interface PermissionService {
	
	void save(Permission permission);
	
	Permission get(Long id);
	
	void update(Permission permission);
	
	void delete(Long id);
}
