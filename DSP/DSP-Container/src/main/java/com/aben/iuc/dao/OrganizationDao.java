 
package com.aben.iuc.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.aben.iuc.entity.main.Organization;

 
public interface OrganizationDao extends JpaRepository<Organization, Long>{
	
	Page<Organization> findByParentId(Long parentId, Pageable pageable);
	
	Page<Organization> findByParentIdAndNameContaining(Long parentId, String name, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gionee.iuc.entity.main")
		}
	)
	@Query("from Organization")
	List<Organization> findAllWithCache();
	
	/**
	 * 统计指定组织下的子组织个数
	 * @param parentId 父组织ID
	 * @return
	 */
	
	@Query("select count(*) from Organization o where o.parent.id=?")
	long countSubOraByParentId(long parentId);
}
