package com.aben.iuc.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;

import com.aben.iuc.constant.Constant;
import com.aben.iuc.entity.main.Module;
import com.aben.iuc.entity.main.Permission;
import com.aben.iuc.exception.ExistedException;
import com.aben.iuc.exception.ServiceException;
import com.aben.iuc.service.ModuleService;
import com.aben.iuc.util.dwz.AjaxObject;
import com.aben.iuc.util.dwz.Page;
import com.google.common.collect.Lists;
 


@Controller
@RequestMapping("/management/security/module")
public class ModuleController {
	private static final Logger log = LoggerFactory
			.getLogger(ModuleController.class);
	@Autowired
	private ModuleService moduleService;

	@Autowired
	private Validator validator;

	private static final String CREATE = "management/security/module/create";
	private static final String UPDATE = "management/security/module/update";
	private static final String LIST = "management/security/module/list";
	private static final String OLIST = "management/security/module/olist";
	private static final String TREE = "management/security/module/tree";
	private static final String VIEW = "management/security/module/view";
	private static final String OCREATE = "management/security/module/ocreate";
	private static final String OUPDATE = "management/security/module/oupdate";
	
	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create/{parentModuleId}", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map,
			@PathVariable Long parentModuleId, String dtype) {
		map.put("parentModuleId", parentModuleId);
		if (log.isDebugEnabled()){
			log.debug("dtype: "+dtype);
		}
		if (Constant.SYS_MOUDLE.equals(dtype))
			return OCREATE;
		return CREATE;
	}

	@RequiresPermissions("Module:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(Module module) {
		if (log.isDebugEnabled()) {
			log.debug("start create module :"+module);
		}
		// 获取父模
		Module parentModule = moduleService.get(module.getParent().getId());
		if (parentModule == null) {
			return AjaxObject.newError(
					"模块添加失败：id=" + module.getParent().getId() + "的父模块不存在！")
					.toString();
		}
		//如果是系统模块
		if (Constant.SYS_MOUDLE.equals(parentModule.getDtype())){
			module.setSysid(parentModule.getId());
		}else{
			//设置系统ID
			module.setSysid(parentModule.getSysid());
//			module.getSort().setModule(module);
		}
		BeanValidators.validateWithException(validator, module);
		List<Permission> permissions = Lists.newArrayList();
		// 获取模块对应的权限信息
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {
				permissions.add(permission);
			}
		}

		// 设置权限的模块
		for (Permission permission : permissions) {
			permission.setModule(module);
		}

		module.setPermissions(permissions);
		module.setType(parentModule.getType());
		
		if (log.isDebugEnabled()) {
			log.debug("parentModule type is :" + parentModule.getType());
		}
		try {
			moduleService.save(module);
		} catch (ExistedException e) {
			return AjaxObject.newError("模块添加失败：" + e.getMessage()).toString();
		}
		if (log.isDebugEnabled()) {
			log.debug("create module end");
		}
		return AjaxObject.newOk("模块添加成功！").toString();
	}

	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id,String dtype,Map<String, Object> map) {
		if (log.isDebugEnabled()){
			log.debug("start update moudle");
		}
		Module module = moduleService.get(id);
		map.put("module", module);
		if (Constant.SYS_MOUDLE.equals(dtype)){
			if (log.isDebugEnabled()){
				log.debug("OUPDATE moudle end");
			}
			return OUPDATE;
		}
		if (log.isDebugEnabled()){
			log.debug("update moudle end");
		}
		return UPDATE;
	}

	@RequiresPermissions("Module:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(Module module) {
		BeanValidators.validateWithException(validator, module);
		Module oldModule = moduleService.get(module.getId());
		oldModule.setName(module.getName());
		oldModule.setPriority(module.getPriority());
		oldModule.setDescription(module.getDescription());
		oldModule.setSn(module.getSn());
		oldModule.setUrl(module.getUrl());
		for (Permission permission : module.getPermissions()) {
			if (StringUtils.isNotBlank(permission.getShortName())) {
				if (permission.getId() == null) {
					permission.setModule(oldModule);
					oldModule.getPermissions().add(permission);
				}
			} else {
				if (permission.getId() != null) {
					for (Permission oldPermission : oldModule.getPermissions()) {
						if (oldPermission.getId().equals(permission.getId())) {
							oldPermission.setModule(null);
							permission = oldPermission;
							break;
						}
					}
					oldModule.getPermissions().remove(permission);
				}
			}
		}

		moduleService.update(oldModule);

		return AjaxObject.newOk("模块修改成功！").toString();
	}

	@RequiresPermissions("Module:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始删除模块信息");
			}
			moduleService.delete(id);
			if (log.isDebugEnabled()) {
				log.debug("删除模块完成");
			}
			ajaxObject.setMessage("模块删除成功！");
		} catch (ServiceException e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("模块删除失败：" + e.getMessage());
	
		}

		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(String type, Map<String, Object> map) {
		if (log.isDebugEnabled()) {
			log.debug("get moudle tree type :" + type);
		}
		if (type == null || "".equals(type)) {
			type = Constant.INNER_MODULE;
		}
		Module module = moduleService.getTree(type);
		map.put("type", type);
		map.put("module", module);
		if (log.isDebugEnabled()) {
			log.debug("get moudle tree end");
		}
		return TREE;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/olist", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String olist(Page page,String keywords, Map<String, Object> map) {
		if (log.isDebugEnabled()) {
			log.debug("start get moudle olist");
		}
		long parentModuleId=3;
		List<Module> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentModuleId, keywords, page);
		} else {
			modules = moduleService.find(parentModuleId, page);
		}
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);
		map.put("parentModuleId", parentModuleId);
		if (log.isDebugEnabled()) {
			log.debug("get moudle olist end");
		}
		return OLIST;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/list/{parentModuleId}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String list(Page page, @PathVariable Long parentModuleId,
			String keywords, Map<String, Object> map) {
		if (log.isDebugEnabled()) {
			log.debug("start get moudle list");
		}
		List<Module> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentModuleId, keywords, page);
		} else {
			modules = moduleService.find(parentModuleId, page);
		}
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);
		map.put("parentModuleId", parentModuleId);
		if (log.isDebugEnabled()) {
			log.debug("get moudle list end");
		}
		return LIST;
	}

	@RequiresPermissions("Module:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Module module = moduleService.get(id);

		map.put("module", module);
		return VIEW;
	}
}
