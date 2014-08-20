 
package com.aben.iuc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aben.iuc.entity.main.RolePermission;
 
public interface RolePermissionDao extends JpaRepository<RolePermission, Long> {
	List<RolePermission> findByRoleId(Long roleId);
}
