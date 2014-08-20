package com.aben.iuc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aben.iuc.constant.Constant;
import com.aben.iuc.controller.ModuleController;
import com.aben.iuc.dao.ModuleDao;
import com.aben.iuc.dao.SysJoinDao;
import com.aben.iuc.entity.main.Module;
import com.aben.iuc.entity.main.Permission;
import com.aben.iuc.entity.main.Role;
import com.aben.iuc.entity.main.RolePermission;
import com.aben.iuc.exception.ExistedException;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.service.ModuleService;
import com.aben.iuc.util.dwz.Page;
import com.aben.iuc.util.dwz.PageUtils;
import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {
	private static final Logger log = LoggerFactory
			.getLogger(ModuleController.class);

	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private SysJoinDao sysJoinDao;
 

	@Transactional
	@Override
	public void save(Module module) throws ExistedException {
		if (moduleDao.findBySysSn(module.getSn(), module.getSysid()) != null) {
			throw new ExistedException("已存在sn=" + module.getSn() + "的模块。");
		}
		moduleDao.save(module);
	}

	@Override
	public Module get(Long id) {
		return moduleDao.findOne(id);
	}

	@Transactional
	@Override
	public void update(Module module) {
		moduleDao.save(module);
	}

	/**
	 * @param id
	 * @throws ServiceException
	 * @see com.aben.iuc.service.ModuleService#delete(int)
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {

		if (log.isDebugEnabled()) {
			log.debug("需要删除的模块ID为: " + id);
		}
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根模块。");
		}
		if (log.isDebugEnabled()) {
			log.debug("start delete moudule id is :" + id);
		}
		Module module = this.get(id);
		long count = moduleDao.countByParentId(id);
		// // 先判断是否存在子模块，如果存在子模块，则不允许删除
		if (count > 0) {
			throw new ServiceException(module.getName() + "模块下存在子模块，不允许删除。");
		}

		if (Constant.SYS_MOUDLE.equals(module.getDtype())) {
			if (sysJoinDao.getByMoudleId(id) != null) {
				throw new ServiceException(module.getName() + "系统存在接入关系，不允许删除。");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("module id is  :" + module.getId());
		}
		module.setChildren(new ArrayList<Module>());
		moduleDao.delete(id);

	}

	public List<Module> findByDtype(String dtype) {
		return moduleDao.findByDtype(dtype);
	}

	/**
	 * @param parentId
	 * @param page
	 * @return
	 * @see com.aben.iuc.service.ModuleService#find(java.lang.Long,
	 *      com.aben.iuc.util.dwz.Page)
	 */
	public List<Module> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<Module> springDataPage = moduleDao
				.findByParentId(parentId, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * @param parentId
	 * @param name
	 * @param page
	 * @return
	 * @see com.aben.iuc.service.ModuleService#find(java.lang.Long,
	 *      java.lang.String, com.aben.iuc.util.dwz.Page)
	 */
	public List<Module> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<Module> springDataPage = moduleDao
				.findByParentIdAndNameContaining(parentId, name, PageUtils
						.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * @return
	 * @see com.aben.iuc.service.ModuleService#findAll()
	 */
	@Override
	public List<Module> findAll() {
		return moduleDao.findAll();
	}

	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == 1 || id == 2 || id == 3;
	}

	/**
	 * 
	 * @return
	 * @see com.aben.iuc.service.ModuleService#getTree()
	 */
	//获取一个树形结构的根节点
	public Module getTree(String type) {
		//返回所有security_module里的数据，并按优先级排列
		List<Module> list = moduleDao.findAllWithCache();
		//根据参数list,返回一个树形结构的List
		List<Module> rootList = makeTree(list);
		//取出树形结构中的根元素（根节点）
		Module rootModule = rootList.get(0);
		// 如果不需要做类型过滤
		if (type != null) {
			boolean hashI = false;
			//通过根元素，获取子元素（子节点）
			List<Module> childMo = rootModule.getChildren();
			//对子节点进行遍历
			for (Module mo : childMo) {
				//子节点的type是否与参数type一致
				if (type.equals(mo.getType())) {
					//替换根元素
					rootModule = mo;
					hashI = true;
					break;
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("hashI: " + hashI + " type: " + type);
			}
			
			if (!hashI) {
				List<Module> list1 = Lists.newArrayList();
				rootModule.setChildren(list1);
			}
		}
		return rootModule;
	}
	
	public Module getTreeWitchSysId(long sysid) {
		List<Module> list =getModuleBySysId(sysid);
		List<Module> rootList = makeSysTree(list);
		Module rootModule = rootList.get(0);
		return rootModule;
	}
	
	public List<Module> getModuleBySysId(long sysid){
		return moduleDao.findBySysid(sysid);
	}
	
	private List<Module> makeSysTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getDtype().equals(Constant.SYS_MOUDLE)) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		makeChildren(parent, list);
		return parent;
	}
	

	//根据参数list,返回一个树形结构的List
	private List<Module> makeTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		makeChildren(parent, list);
		return parent;
	}

	//通过回调，不断对security_module中取出的结果进行树形结构的构造
	private void makeChildren(List<Module> parent, List<Module> children) {
		if (children.isEmpty()) {
			return;
		}
		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				c2.setChildren(new ArrayList<Module>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		children.removeAll(tmp);
		makeChildren(tmp, children);
	}

	//writed by me
	private static List<Module> list;
	 
	
	@Override
	public List<Module> getSystemModule() {
		
		list = moduleDao.findAll();
		List<Module> newList = new ArrayList<Module>();
		for(Module m : list){
			if(m.getParent() != null){
				if(3 == (m.getParent().getId())){
					newList.add(m);
				}
				if(2 == (m.getParent().getId())){
					newList.add(m);
				}
			}
		}
		return newList;
	}

	
	
	
	
	public Module getTreeByFilter2(long id){
		List<Module> newList = new ArrayList<Module>();
		
		Set<Module> set = new LinkedHashSet<Module>();
		
		newList = getNewList(id,set);
		
		List<Module> rootList = makeTree(newList);
		//取出树形结构中的根元素（根节点）
		Module rootModule = rootList.get(0);
		
		return rootModule;
	}
	
	private List<Module> getNewList(Long id,Set<Module> set){
		Module module = moduleDao.findOne(id);
		//得到子类
		getParents2List(module,set);
		set.add(module);
		//得到父类
		getChild2List(module,set);
		//将结果放到newList中
		List<Module> newList = new ArrayList<Module>();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			newList.add((Module) iterator.next());
		}
		
		return newList;
	}
	
	//不通过代码迭代
	private Set<Module> getChild2List(Module module,Set<Module> set){
		
		if(module.getChildren().size() > 0){
			for(Module m : module.getChildren()){
				set.add(m);
				if(m.getChildren().size()>0)
					getChild2List(m,set);
			}
		}
		return set;
		
	}
	private Set<Module> getParents2List(Module module,Set<Module> set){
		Module m = module.getParent();
		if(m != null){
			set.add(m);
			if(m.getParent() != null){
				getParents2List(m,set);
			}
		}
		return set;
	}

	@Override
	public void getModulesByPermission(List<Role> roles) {
		// TODO Auto-generated method stub
		List<Role> list = new ArrayList<Role>();
		if(roles != null){
			for(Role role : roles){
				if(role.getModule() == null){
					list.add(role);
				}
			}
		}
		getSysModules(list);
		
	}
	
	private void getSysModules(List<Role> roles){
		Set<Module> set = null;
		List<Module> list = null;
		List<Module> lists = new ArrayList<Module>();
		for(Role r : roles){
			if(r.getRolePermissions() != null){
				set = new LinkedHashSet<Module>();
				for(RolePermission mp : r.getRolePermissions()){
					Module module = getSysModule(mp.getPermission().getModule());
					if (module != null) {
						set.add(module);
					}
				}
				
				list = new ArrayList<Module>();
				Iterator iterator = set.iterator();
				StringBuffer sb = new StringBuffer();
				while(iterator.hasNext()){
					
					Module m = (Module) iterator.next();
					sb.append(""+m.getName()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//					list.add((Module) iterator.next());
					
				}
				
				r.setSysnames(sb.toString());

			}
		}
		
		
		
	}
	
	private Module getSysModule(Module module){
		if(module != null){
			if("SYS".equals(module.getDtype())){
				return module;
			}else{
				getSysModule(module.getParent());
				
			}
		}
		return null;
	}
}
