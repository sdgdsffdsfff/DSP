 
 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.Organization;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.util.dwz.Page;

 

public interface OrganizationService {
	
	List<Organization> find(Long parentId, Page page);
	
	List<Organization> find(Long parentId, String name, Page page);
	
	Organization getTree();

	void save(Organization organization);

	Organization get(Long id);

	void update(Organization organization);

	void delete(Long id) throws ServiceException;
	
	//查询全部的Organization;
	List<Organization> findAll();
}
