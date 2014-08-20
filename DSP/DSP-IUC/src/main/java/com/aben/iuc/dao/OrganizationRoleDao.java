 
 
package com.aben.iuc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aben.iuc.entity.main.OrganizationRole;

 
public interface OrganizationRoleDao extends JpaRepository<OrganizationRole, Long> {
	List<OrganizationRole> findByOrganizationId(Long organizationId);
}
