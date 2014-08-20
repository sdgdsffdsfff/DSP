 
package com.aben.iuc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aben.iuc.entity.main.Role;

 

public interface RoleDao extends JpaRepository<Role, Long> {
	Page<Role> findByNameContaining(String name, Pageable pageable);
	

}
