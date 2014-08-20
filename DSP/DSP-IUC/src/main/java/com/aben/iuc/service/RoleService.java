 
 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.Role;
import com.aben.iuc.util.dwz.Page;
 

public interface RoleService {
	
	List<Role> find(Page page, String name);

	void save(Role role);

	Role get(Long id);

	void update(Role role);

	void delete(Long id);

	List<Role> findAll(Page page);
	
	//write by me
	//根据角色名和moduleId得到所有角色
	List<Role> findByFilter(Page page,String roleName,Long moduleId);
}
