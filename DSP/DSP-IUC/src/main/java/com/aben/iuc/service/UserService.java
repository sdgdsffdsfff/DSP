 
 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.User;
import com.aben.iuc.exception.ExistedException;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.util.dwz.Page;

 

public interface UserService {
	
	User get(String username);
	
	List<User> find(Page page, String name);

	void update(User user);

	void save(User user) throws ExistedException;

	User get(Long id);

	void delete(Long id) throws ServiceException;

	List<User> findAll(Page page);
	
	//用过滤器查询
	List<User> findByFilter(String username,String realname,Long organizationId);
	
	//使用EntityManager执行jpa的sql动态查询
	List<User> findByEntityManager(Page page,String username,String realname,Long organizationId);
}
