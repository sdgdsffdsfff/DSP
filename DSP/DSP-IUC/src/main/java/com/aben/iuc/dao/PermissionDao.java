 
 
package com.aben.iuc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aben.iuc.entity.main.Permission;

 

public interface PermissionDao extends JpaRepository<Permission, Long> {

}
