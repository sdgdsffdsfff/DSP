 
 
package com.aben.iuc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.UserDao;
import com.aben.iuc.entity.main.User;
import com.aben.iuc.exception.ExistedException;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.service.UserService;
import com.aben.iuc.shiro.ShiroDbRealm;
import com.aben.iuc.shiro.ShiroDbRealm.HashPassword;
import com.aben.iuc.util.dwz.Page;
import com.aben.iuc.util.dwz.PageUtils;

 
@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDao userDao;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User get(Long id) {
		return userDao.findOne(id);
	}

	@Override
	public List<User> findAll(Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 
	 * @param user
	 * @throws ExistedException  
	 * @see com.aben.iuc.service.UserService#save(com.aben.iuc.entity.main.User)
	 */
	@Transactional
	public void save(User user) throws ExistedException {		
		if (userDao.findByUsername(user.getUsername()) != null) {
			throw new ExistedException("用户添加失败，登录名：" + user.getUsername() + "已存在。");
		}
		
		if (userDao.findByRealname(user.getRealname()) != null) {
			throw new ExistedException("用户添加失败，真实名：" + user.getRealname() + "已存在。");
		}
		
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param user  
	 * @see com.aben.iuc.service.UserService#update(com.aben.iuc.entity.main.User)  
	 */
	@Transactional
	public void update(User user) {
		//if (isSupervisor(user.getId())) {
		//	logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
		//	throw new ServiceException("不能修改超级管理员用户");
		//}
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param id  
	 * @see com.aben.iuc.service.UserService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new ServiceException("不能删除超级管理员用户。");
		}
		userDao.delete(id);
	}

	/**   
	 * @param username
	 * @return  
	 * @see com.aben.iuc.service.UserService#get(java.lang.String)  
	 */
	public User get(String username) {
		return userDao.findByUsername(username);
	}

	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see com.aben.iuc.service.UserService#find(com.aben.iuc.util.dwz.Page, java.lang.String)  
	 */
	public List<User> find(Page page, String name) {
		org.springframework.data.domain.Page<User> springDataPage = 
				userDao.findByUsernameContaining(name, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	@Override
	public List<User> findByFilter(String username, String realname,
			Long organizationId) {
		User u1 = null ;
		User u2 = null;
		List<User> u3 = null;
		List<User> u4 = new ArrayList<User>();
//		List<User> users = new ArrayList<User>();
		if(StringUtils.isNotBlank(username)){
			u1 =  userDao.findByUsername(username);
//			System.out.println(u1.getUsername());
		}
		
		if(StringUtils.isNotBlank(realname)){
			u2 = userDao.findByRealname(realname);
//			System.out.println(u2.getRealname());
		}
		
		if(null != (organizationId)){
			
			u3 = userDao.findByOrganizationId(organizationId); 
		}
		
		if(u3 != null){
			for(User u : u3){
				if(u1 != null){
					if(u1 != u){
						u4.add(u);
					}
				}
				
				if(u2 != null){
					if(u2 != u && u2 != u1){
						u4.add(u);
					}
				}
			}
			
			if(u4.size() > 0){
				u3.removeAll(u4);
				return u3;
			}else{
				if(StringUtils.isNotBlank(realname)||StringUtils.isNotBlank(username)){
					if(u1 != null){
						u4.add(u1);
						return u4;
					}else if(u2 != null){
						u4.add(u2);
						return u4;
					}else{
						return new ArrayList<User>();
					}
				}else{
					return u3;
				}
			}
			
			
		}else{
			if(u1!=null && u2 != null){
				if(u1 == u2){
					u4.add(u1);
					return u4;
				}else{
					return new ArrayList<User>();
				}
			}
			else if(u1 != null){
				u4.add(u1);
				return u4;
			}else if(u2 != null){
				u4.add(u2);
				return u4;
			}else{
				return u4;
			}
		}
		
		
	}

	

	
	//使用EntityManager执行jpa的sql动态查询
	public List<User> findByEntityManager(Page page,String username,String realname,Long organizationId){
//		EntityManagerFactory emf = 
//				javax.persistence.Persistence.createEntityManagerFactory("entityManagerFactory"); 
		EntityManager em = entityManagerFactory.createEntityManager();
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		StringBuffer sb = new StringBuffer("SELECT u FROM User u WHERE 1=1");
		if(!StringUtils.isEmpty(username)){
			sb.append(" AND u.username LIKE :username");
			map.put("username", "%"+username+"%");
		}
		if(!StringUtils.isEmpty(realname)){
			sb.append(" AND u.realname LIKE :realname");
			map.put("realname", "%"+realname+"%");
		}	
		if(organizationId != null){
			sb.append(" AND u.organization.id = :organizationId");
			map.put("organizationId", organizationId);
		}
		Query query = em.createQuery(sb.toString());
		for(String key : map.keySet()){
			query.setParameter(key, map.get(key));
		}
		//设置总页数
		page.setTotalCount(query.getResultList().size());
		//分页
		query.setMaxResults(page.getPageNum()*page.getNumPerPage());
		query.setFirstResult((page.getPageNum()-1)*page.getNumPerPage());
		List<User> users = new ArrayList<User>();
		users = query.getResultList();
		
		return users;
		
	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	
}
