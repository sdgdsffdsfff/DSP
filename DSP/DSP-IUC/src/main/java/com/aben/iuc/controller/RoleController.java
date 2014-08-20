package com.aben.iuc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.aben.iuc.entity.main.Module;
import com.aben.iuc.entity.main.Role;
import com.aben.iuc.entity.main.RolePermission;
import com.aben.iuc.service.ModuleService;
import com.aben.iuc.service.RolePermissionService;
import com.aben.iuc.service.RoleService;
import com.aben.iuc.util.dwz.AjaxObject;
import com.aben.iuc.util.dwz.Page;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/management/security/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private Validator validator;
	
	//by me begin
	@Autowired
	private RolePermissionService rolePermissionService;
	//end

	private static final String CREATE = "management/security/role/create";
	private static final String UPDATE = "management/security/role/update";
	private static final String LIST = "management/security/role/list";
	private static final String VIEW = "management/security/role/view";

	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		map.put("module", moduleService.getTree(null));
		map.put("sysModule", moduleService.getSystemModule());
		return CREATE;
	}

	/**
	 * @param role
	 * @param moduleId
	 * @return
	 */
	@RequiresPermissions("Role:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Role role,Long moduleId) {
		Module module = new Module();
		module.setId(moduleId);
		role.setModule(module);
		
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, role);
	
		List<RolePermission> rolePermissions = Lists.newArrayList();
		for (RolePermission rolePermission : role.getRolePermissions()) {
			if (rolePermission.getPermission() != null
					&& rolePermission.getPermission().getId() != null) {
				rolePermissions.add(rolePermission);
			}
		}

		for (RolePermission rolePermission : rolePermissions) {
			rolePermission.setRole(role);
		}
		role.setRolePermissions(rolePermissions);
		roleService.save(role);
		return AjaxObject.newOk("角色添加成功！").toString();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(2000);
	}

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id ,Map<String, Object> map) {
		Role role = roleService.get(id);
		//by me begin
		if(role.getModule() == null){
			map.put("module", moduleService.getTree(null));
		}else{
			map.put("module", moduleService.getTreeByFilter2(role.getModule().getId()));
			map.put("moduleId", role.getModule().getId());
		}
		
		//end
//		map.put("module", moduleService.getTree(null));
		
		//下拉列表的提示选项
		map.put("sysModule", moduleService.getSystemModule());
		map.put("role", role);
		return UPDATE;
	}
	
	

	@RequiresPermissions("Role:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(Role role,Long moduleId) {
		BeanValidators.validateWithException(validator, role);

		Role oldRole = roleService.get(role.getId());
		oldRole.setName(role.getName());
		oldRole.setDescription(role.getDescription());

		for (RolePermission rolePermission : role.getRolePermissions()) {
			if (rolePermission.getPermission() != null//查看是否有Permission并且permission的Id不为空
					&& rolePermission.getPermission().getId() != null) {
				if (rolePermission.getId() == null) {//在数据库中是否有这条记录
					rolePermission.setRole(oldRole);//在对象rolepermission中添加级联关系
					//by me begin 在security_role_permission表中添加记录，否则会出现update异常
					rolePermissionService.save(rolePermission);
					//end
					oldRole.getRolePermissions().add(rolePermission);//在对象role中添加级联关系

				}
			} else {
				if (rolePermission.getId() != null) {
					for (RolePermission oldRolePermission : oldRole
							.getRolePermissions()) {
						if (oldRolePermission.getId().equals(
								rolePermission.getId())) {
							oldRolePermission.setRole(null);
							rolePermission = oldRolePermission;
                            //by me begin 在security_role_permission表中删除记录，否则会出现update异常
							rolePermissionService.delete(rolePermission.getId());
							//end
							break;
						}
					}
					oldRole.getRolePermissions().remove(rolePermission);
				}
			}
		}

		
		//by me begin 如果改变的所属系统,相应的删除之前的所属系统的所有权限，如果该角色无任何权限，则不属于任何系统
		if(oldRole.getRolePermissions().size() != 0){
			Module m = new Module();
			m.setId(moduleId);
			oldRole.setModule(m);
		}else{
			oldRole.setModule(null);
		}
		roleService.update(oldRole);

		Role roleupdate = roleService.get(oldRole.getId());
		List<RolePermission> list = new ArrayList<RolePermission>();
		for(RolePermission rp : roleupdate.getRolePermissions()){
			if (rp.getPermission() != null) {
				Module m2 = rp.getPermission().getModule();
				if (m2 != null) {
					Module m3 = getSysModule(m2);
					if (m3 != null) {
						if (!m3.getId().equals(moduleId)) {
							list.add(rp);
						}
					}
				}
			}
		}
		roleupdate.getRolePermissions().removeAll(list);
		roleService.update(roleupdate);
		//end  hibernate update 问题

		return AjaxObject.newOk("角色修改成功！").toString();
	}

	@RequiresPermissions("Role:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {

		roleService.delete(id);

		AjaxObject ajaxObject = new AjaxObject("角色删除成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(Page page, String keywords, Map<String, Object> map,Long moduleId) {
		List<Role> roles = null;
//		if (StringUtils.isNotBlank(keywords)) {
//			roles = roleService.find(page, keywords);
//		} else {
//			roles = roleService.findAll(page);
//		}

		roles = roleService.findByFilter(page, keywords, moduleId);
		moduleService.getModulesByPermission(roles);
		map.put("sysModule", moduleService.getSystemModule());
		map.put("moduleId", moduleId);
		
		map.put("page", page);
		map.put("roles", roles);
		map.put("keywords", keywords);
		return LIST;
	}

	

	@RequiresPermissions("Role:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Role role = roleService.get(id);
//		map.put("module", moduleService.getTree(null));
		
		//by me begin
				if(role.getModule() == null){
					map.put("module", moduleService.getTree(null));
				}else{
					map.put("module", moduleService.getTreeByFilter2(role.getModule().getId()));
					map.put("moduleId", role.getModule().getId());
				}
				
		//end
		
		
		map.put("sysModule", moduleService.getSystemModule());
		map.put("role", role);
		return VIEW;
	}
	

	//write by me 
	@RequestMapping(value = "/filter")
	public String modulefilter(Long id,Long moduleId,Map<String,Object> map){
		map.put("module", "");
		
		Role role = roleService.get(id);
		
		map.put("module", moduleService.getTreeByFilter2(moduleId));
		
		List<String> sysnames = new ArrayList<String>();
		for(RolePermission rp : role.getRolePermissions()){
			if ("SYS".equals(rp.getPermission().getModule().getParent().getDtype())) {
				String sysname = rp.getPermission().getModule().getParent().getName();
				sysnames.add(sysname);
			}
		}
		map.put("sysnames", sysnames);
		//下拉列表的提示选项
		map.put("sysModule", moduleService.getSystemModule());
		map.put("role", role);
		map.put("moduleId", moduleId);
		return UPDATE;
	}
	
	@RequestMapping(value = "/createfilter")
	public String modulecreatefilter(String name,String description,Long moduleId,Map<String,Object> map){
		map.put("name", name);
		map.put("description", description);
		map.put("module", moduleService.getTreeByFilter2(moduleId));
		map.put("sysModule", moduleService.getSystemModule());
		map.put("moduleId", moduleId);
		return CREATE;
	}


	//根据Module找到他的SysModule
	private Module getSysModule(Module module){
		if("SYS".equals(module.getDtype())){
			return module;
		}else{
			if(module.getParent() != null){
				return getSysModule(module.getParent());
			}else{
				return null;
			}
			
		}
	}
}
