 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.OrganizationRole;
 
public interface OrganizationRoleService {
	/**
	 * 根据organizationId，找到已分配的角色。
	 * 描述
	 * @param organizationId
	 * @return
	 */
	List<OrganizationRole> find(Long organizationId);

	void save(OrganizationRole organizationRole);

	void delete(Long organizationRoleId);
}
