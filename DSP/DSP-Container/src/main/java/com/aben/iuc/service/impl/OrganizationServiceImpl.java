 
package com.aben.iuc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.dao.OrganizationDao;
import com.aben.iuc.dao.UserDao;
import com.aben.iuc.entity.main.Organization;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.service.OrganizationService;
import com.aben.iuc.util.dwz.Page;
import com.aben.iuc.util.dwz.PageUtils;

 
@Service
@Transactional(readOnly=true)
public class OrganizationServiceImpl implements OrganizationService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Transactional
	@Override
	public void save(Organization organization) {
		organizationDao.save(organization);
	}

	@Override
	public Organization get(Long id) {
		return organizationDao.findOne(id);
	}

	@Transactional
	@Override
	public void update(Organization organization) {
		organizationDao.save(organization);
	}

	/**   
	 * @param id
	 * @throws ServiceException  
	 * @see com.aben.iuc.service.OrganizationService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}
		
		Organization organization = this.get(id);
		
		long count=organizationDao.countSubOraByParentId(id);
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(count > 0){
			throw new ServiceException(organization.getName() + "组织下存在子组织，不允许删除。");
		}
		
		if (userDao.findByOrganizationId(id).size() > 0) {
			throw new ServiceException(organization.getName() + "组织下存在用户，不允许删除。");
		}
		
		organizationDao.delete(id);
	}

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see com.aben.iuc.service.OrganizationService#find(java.lang.Long, com.aben.iuc.util.dwz.Page)  
	 */
	public List<Organization> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = 
				organizationDao.findByParentId(parentId, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see com.aben.iuc.service.OrganizationService#find(java.lang.Long, java.lang.String, com.aben.iuc.util.dwz.Page)  
	 */
	public List<Organization> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = 
				organizationDao.findByParentIdAndNameContaining(parentId, name, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

	/**
	 * 
	 * @return  
	 * @see com.aben.iuc.service.OrganizationService#getTree()
	 */
	public Organization getTree() {
		List<Organization> list = organizationDao.findAllWithCache();
		
		List<Organization> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<Organization>();
		// get parentId = null;
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Organization> parent, List<Organization> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Organization> tmp = new ArrayList<Organization>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				c2.setChildren(new ArrayList<Organization>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}

	@Override
	public List<Organization> findAll() {
		List<Organization> organizations = organizationDao.findAll();
		for(Organization o : organizationDao.findAll()){
			if(o.getId() == 2){
				organizations.remove(o);
			}
		}
		return organizations;
	}
}
