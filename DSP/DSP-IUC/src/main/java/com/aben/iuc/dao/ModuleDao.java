 
package com.aben.iuc.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.aben.iuc.entity.main.Module;

 
public interface ModuleDao extends JpaRepository<Module, Long> {
	Page<Module> findByParentId(Long parentId, Pageable pageable);
	
	Page<Module> findByParentIdAndNameContaining(Long parentId, String name, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="com.gionee.iuc.entity.main")
		}
	)
	@Query("from Module m order by m.priority ASC")
	List<Module> findAllWithCache();
	
	@Query("from Module m where m.sn=:sn and m.sysid=:sysid")
	Module findBySysSn(@Param("sn") String sn,@Param("sysid") long sysid);
	
	/**
	 * 根据sn，查找Module
	 * 描述
	 * @param sn
	 * @return
	 */
	List<Module> findBySn(String sn);
	
	/**
	 * 统计
	 * @param parentId 父级ID
	 * @return
	 */
	@Query("select count(*) from Module m where m.parent.id=?")
	long countByParentId(Long parentId);
	
	/**
	 * 根据datype，查找Module
	 * 描述
	 * @param sn
	 * @return
	 */
	@Query("from Module m where m.dtype=?")
	List<Module> findByDtype(String dtype);
	
	/**
	 * 获取指定系统的ID
	 * @param sysid 系统ID
	 * @return
	 */
	@Query("from Module m where m.sysid=:id or m.id=:id")
	List<Module> findBySysid(@Param("id") long sysid);
	
	
	
	
	
}
