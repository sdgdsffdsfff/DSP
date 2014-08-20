 
 
package com.aben.iuc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.RoleDao;
import com.aben.iuc.entity.main.Role;
import com.aben.iuc.entity.main.User;
import com.aben.iuc.service.RoleService;
import com.aben.iuc.shiro.ShiroDbRealm;
import com.aben.iuc.util.dwz.Page;
import com.aben.iuc.util.dwz.PageUtils;

 
@Service
@Transactional(readOnly=true,propagation=Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	@Transactional
	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public Role get(Long id) {
		return roleDao.findOne(id);
	}
	
	@Override
	public List<Role> findAll(Page page) {
		org.springframework.data.domain.Page<Role> springDataPage = roleDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**   
	 * @param role  
	 * @see com.aben.iuc.service.RoleService#update(com.aben.iuc.entity.main.Role)  
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void update(Role role) {
		roleDao.save(role);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param id  
	 * @see com.aben.iuc.service.RoleService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) {
		roleDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see com.aben.iuc.service.RoleService#find(com.aben.iuc.util.dwz.Page, java.lang.String)  
	 */
	public List<Role> find(Page page, String name) {
		org.springframework.data.domain.Page<Role> springDataPage = 
				(org.springframework.data.domain.Page<Role>)roleDao.findByNameContaining(name, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	//write by me

	@Override
	public List<Role> findByFilter(Page page,String roleName, Long moduleId) {
		// TODO Auto-generated method stub
		EntityManager em = entityManagerFactory.createEntityManager();
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
//		sb.append("select r from Role r where 1=1");
		if(moduleId == null){
			sb.append("select r from Role r where 1=1");
		}else{
//			sb.append("select distinct r from Role r,RolePermission rp,Permission p,Module m "
//                    +"where r.id = rp.role.id and rp.permission.id = p.id and p.module.id = m.id");
			sb.append("select distinct r from Role r,RolePermission rp,Permission p,Module m "
                    +"where 1=1");
		}
		
		
		if(moduleId != null){
//			sb.append(" and r.module.id = :moduleId");
			sb.append(" and (m.id = :moduleId or m.parent.id = :moduleId2 or m.parent.parent.id = :moduleId3)");
			map.put("moduleId", moduleId);
			map.put("moduleId2", moduleId);
			map.put("moduleId3", moduleId);
			sb.append(" and r.id = rp.role.id and rp.permission.id = p.id and p.module.id = m.id");
			
		}
		if(!StringUtils.isBlank(roleName)){
//			sb.append(" and r.name like :roleName");
			sb.append(" and r.name like :roleName");
			map.put("roleName", "%"+roleName+"%");
		}
		
	
//				String jpql = "select sr.* from security_role sr,security_role_permission srp,security_permission sp,security_module sm "
//                           +"where sr.id = srp.role_id and srp.permission_id = sp.id "
//                           +"and sp.module_id = sm.id and sm.id = '745' and sr.name like '%ri%'";

				
				
				
		Query query = em.createQuery(sb.toString());
		
		for(String key : map.keySet()){
			query.setParameter(key, map.get(key));
		}
		List<Role> list = query.getResultList();
		page.setTotalCount(list.size());
		//分页
		query.setMaxResults(page.getPageNum()*page.getNumPerPage());
		query.setFirstResult((page.getPageNum()-1)*page.getNumPerPage());
		List<Role> roles = new ArrayList<Role>();
		roles = (List<Role>) query.getResultList();
		return roles;
	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;
}
