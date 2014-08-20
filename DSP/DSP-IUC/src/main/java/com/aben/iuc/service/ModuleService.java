 
package com.aben.iuc.service;

import java.util.List;

import com.aben.iuc.entity.main.Module;
import com.aben.iuc.entity.main.Role;
import com.aben.iuc.exception.ExistedException;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.util.dwz.Page;

 
public interface ModuleService {
	void save(Module module) throws ExistedException;

	Module get(Long id);
	
	void update(Module module);
	
	void delete(Long id) throws ServiceException;
	
	Module getTree(String type);
	
	List<Module> findAll();
	
	List<Module> find(Long parentId, Page page);
	
	List<Module> find(Long parentId, String name, Page page);
	
	List<Module> findByDtype(String dtype);
	
	/**
	 * 获取指定系统的菜单
	 * @param sysid 系统ID
	 * @return
	 */
    Module getTreeWitchSysId(long sysid);
    
    List<Module> getModuleBySysId(long sysid);
    
    //在security_module中返回所有的是系统的Module
    List<Module> getSystemModule();
    
    //根据Module_Id返回相应的tree
    
    Module getTreeByFilter2(long id);
    
    
    //根据角色拥有的权限找到角色所能进入的系统
    void getModulesByPermission(List<Role> roles);
    
}
