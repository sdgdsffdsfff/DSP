//我写的
package com.aben.iuc.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aben.iuc.entity.main.UserRole;
import com.aben.iuc.service.UserRoleService;
import com.aben.iuc.util.dwz.AjaxObject;

@Controller
@RequestMapping("/management/security/roleUser")
public class RoleUserController {
	

	@Autowired
	private UserRoleService userRoleService;
	

	
	
	public static final String LIST = "management/security/role/delete_role_user";
	
	


    @RequiresPermissions("Role:View")
    @RequestMapping(value = "/userlist/{id}")
    public String userlist(@PathVariable Long id,Map<String, Object> map){
    	List<UserRole> userroles = userRoleService.findUserRolesByRoleId(id);
    	map.put("userRoles", userroles);
    	return LIST;
    }
    
    @RequiresPermissions("Role:Delete")
   	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
       public @ResponseBody String delete(@PathVariable Long id){
   		//删除security_user_role中的一条记录
       	userRoleService.delete(id);
       	//返回ajax对象
       	AjaxObject ao = new AjaxObject("删除成功");
       	ao.setCallbackType("");
       	return ao.toString();
       }
    
}
