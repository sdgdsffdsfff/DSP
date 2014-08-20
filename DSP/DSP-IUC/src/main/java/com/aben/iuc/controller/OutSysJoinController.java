package com.aben.iuc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.shiro.authz.annotation.Logical;
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
import com.aben.iuc.entity.main.OutSysJoin;
import com.aben.iuc.service.ModuleService;
import com.aben.iuc.service.SysJoinService;
import com.aben.iuc.util.UUIDUtil;
import com.aben.iuc.util.dwz.AjaxObject;
import com.aben.iuc.util.dwz.Page;
 

@Controller
@RequestMapping("/management/security/join")
public class OutSysJoinController {
	private static final Logger log = LoggerFactory
	.getLogger(OutSysJoinController.class);
	private static final String list = "management/security/sysJoin/list";
	private static final String update = "management/security/sysJoin/update";
	private static final String create = "management/security/sysJoin/create";
	private static final String lookupmo = "management/security/sysJoin/lookup_mod";
	@Autowired
	private SysJoinService joinService;
	@Autowired
	private Validator validator;
	@Autowired
	private ModuleService moduleService;
	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public SysJoinService getJoinService() {
		return joinService;
	}

	public void setJoinService(SysJoinService joinService) {
		this.joinService = joinService;
	}

	@RequiresPermissions("sysJoin:save")
	@RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
	public String preCreate(@PathVariable Long id,Map<String,Object> map) {
		
		OutSysJoin sysjoin = joinService.getSysJoinById(id);
		map.put("sysjoin", sysjoin);
		return create;
	}

	@RequiresPermissions("sysJoin:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(OutSysJoin join) {
		join.setAppkey(UUIDUtil.getUUid());
		join.setAppid(UUIDUtil.getUUid());
		join.setCreateTime(new Date());
		BeanValidators.validateWithException(validator, join);
		OutSysJoin jogin = joinService.getBySysMoudleId(join.getMoudle()
				.getId());
		if (jogin != null) {
			return AjaxObject.newError(
					"系统" + jogin.getMoudle().getName() + "已接入").toString();
		}
		try {
			joinService.sysJoin(join);
		} catch (Exception ex) {
			return AjaxObject.newError("系统异常").toString();
		}
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@RequiresPermissions("sysJoin:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id,String dtype,Map<String, Object> map) {
		map.put("id",id);
		return update;
	} 
	
	@RequiresPermissions("sysJoin:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String updateIp(OutSysJoin join) {
		if (log.isDebugEnabled()){
			log.debug("start update ip");
			log.debug("id: "+join.getId());
			log.debug("ipStr: "+join.getJoinip());
		}
		joinService.updateIp(join.getId(), join.getJoinip());
		if (log.isDebugEnabled()){
			log.debug("update ip end");
		}
		return AjaxObject.newOk("修改IP信息成功！").toString();
	}

	@RequiresPermissions("sysJoin:view")
	@RequestMapping(value = "/list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String list(Page page,Map<String, Object> map) {
		if (log.isDebugEnabled()){
			log.debug("start get list");
		}
		List<OutSysJoin> joingList=joinService.findAll(page);
		map.put("page", page);
		map.put("join", joingList);
		return list;
	}

	@RequiresPermissions("sysJoin:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		AjaxObject ajaxObject = new AjaxObject();
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始删除接入信息");
			}
			joinService.delJoin(id);
			if (log.isDebugEnabled()) {
				log.debug("删除接入信息完成");
			}
			ajaxObject.setMessage("删除接入成功！");
		} catch (Exception e) {
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("接入删除失败：" + e.getMessage());
		}

		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions(value={"sysJoin:edit", "sysJoin:save"}, logical=Logical.OR)
	@RequestMapping(value="/lookupmodule", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		List<Module> mouldes=moduleService.findByDtype(Constant.SYS_MOUDLE);
		Module moudle=new Module();
		moudle.setChildren(mouldes);
		moudle.setId(1l);
		moudle.setName("root");
		map.put("module", moudle);
		return lookupmo;
	}
}
