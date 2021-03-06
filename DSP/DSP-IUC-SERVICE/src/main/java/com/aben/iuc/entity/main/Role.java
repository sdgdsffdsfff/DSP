 
package com.aben.iuc.entity.main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.aben.iuc.entity.IdEntity;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
 
@Entity
@Table(name="security_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gionee.iuc.entity.main")
public class Role extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -5537665695891354775L;
	
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String name;
	
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);
	
	@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<OrganizationRole> organizationRoles = Lists.newArrayList();
	
	@OneToMany(mappedBy="role", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private List<RolePermission> rolePermissions = Lists.newArrayList();
	
	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**  
	 * 返回 userRoles 的值   
	 * @return userRoles  
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**  
	 * 设置 userRoles 的值  
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**  
	 * 返回 organizationRoles 的值   
	 * @return organizationRoles  
	 */
	public List<OrganizationRole> getOrganizationRoles() {
		return organizationRoles;
	}

	/**  
	 * 设置 organizationRoles 的值  
	 * @param organizationRoles
	 */
	public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
		this.organizationRoles = organizationRoles;
	}

	/**  
	 * 返回 rolePermissions 的值   
	 * @return rolePermissions  
	 */
	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	/**  
	 * 设置 rolePermissions 的值  
	 * @param rolePermissions
	 */
	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
	
	/**   
	 * @param arg0
	 * @return  
	 * @see java.lang.Object#equals(java.lang.Object)  
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof Role) { 
			Role that = (Role) obj; 
            return Objects.equal(id, that.getId()) 
                    && Objects.equal(name, that.getName());
        } 

        return false; 
	}

	/**   
	 * @return  
	 * @see java.lang.Object#hashCode()  
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id, name);
	}
	
	//write by me 
	//TODO
	
	@ManyToOne
	@JoinColumn(name="moduleId")
	private Module module;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

//	@Transient
//	private List<Module> modules = Lists.newArrayList();
//
//	public List<Module> getModules() {
//		return modules;
//	}
//
//	public void setModules(List<Module> modules) {
//		this.modules = modules;
//	}
	@Transient
	private String sysnames ;

	public String getSysnames() {
		return sysnames;
	}

	public void setSysnames(String sysnames) {
		this.sysnames = sysnames;
	}

	
	
	
	
	
}
