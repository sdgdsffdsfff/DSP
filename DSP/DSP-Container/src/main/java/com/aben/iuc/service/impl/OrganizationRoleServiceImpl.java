 
package com.aben.iuc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.OrganizationRoleDao;
import com.aben.iuc.entity.main.OrganizationRole;
import com.aben.iuc.service.OrganizationRoleService;

 
@Service
@Transactional(readOnly=true)
public class OrganizationRoleServiceImpl implements OrganizationRoleService {
	
	private OrganizationRoleDao organizationRoleDao;
	
	/**
	 * 
	 * 构造函数
	 * @param organizationRoleDao
	 */
	@Autowired
	public OrganizationRoleServiceImpl(OrganizationRoleDao organizationRoleDao) {
		this.organizationRoleDao = organizationRoleDao;
	}

	/**   
	 * @param organizationId
	 * @return  
	 * @see com.aben.iuc.service.OrganizationRoleService#find(java.lang.Long)  
	 */
	@Override
	public List<OrganizationRole> find(Long organizationId) {
		return organizationRoleDao.findByOrganizationId(organizationId);
	}

	/**   
	 * @param organizationRole  
	 * @see com.aben.iuc.service.OrganizationRoleService#save(com.aben.iuc.entity.main.OrganizationRole)  
	 */
	@Transactional
	@Override
	public void save(OrganizationRole organizationRole) {
		organizationRoleDao.save(organizationRole);
	}

	/**   
	 * @param organizationRoleId  
	 * @see com.aben.iuc.service.OrganizationRoleService#delete(java.lang.Long)  
	 */
	@Transactional
	@Override
	public void delete(Long organizationRoleId) {
		organizationRoleDao.delete(organizationRoleId);
	}

}
