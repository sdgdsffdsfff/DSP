 
 
package com.aben.iuc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aben.iuc.entity.main.UserRole;


public interface UserRoleDao extends JpaRepository<UserRole, Long> {
	
	List<UserRole> findByUserId(Long userId);
	
//	根据role_id，返回所有是该角色的users
//	@Query("from User user where user.id in (select userrole.user.id from UserRole userrole where userrole.role.id = ?)")
//	List<User> findUsersByRoleId(Long roleId);
	
//	根据role_id,删除security_user_role表中的记录
//	@Modifying
//	@Query("delete from UserRole userrole where userrole.user.id = ?")
//	void deleteByRoleId(long roleId);
	
//	根据role_id,返回user_role
	String hql = "from UserRole userrole where userrole.role.id = ?";
	@Query(hql)
	List<UserRole> findUserRolesByRoleId(Long roleId);
	

}
