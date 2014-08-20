 
 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.UserRole;

 

public interface UserRoleService {
	
	/**
	 * 根据userId，找到已分配的角色。
	 * 描述
	 * @param userId
	 * @return
	 */
	List<UserRole> find(Long userId);

	void save(UserRole userRole);

	void delete(Long userRoleId);
	
	List<UserRole> findUserRolesByRoleId(Long roleId);
	
}
