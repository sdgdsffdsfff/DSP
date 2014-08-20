 
package com.aben.iuc.service.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.stereotype.Service;

import com.aben.iuc.service.CacheService;

 
@Service
public class CacheServiceImpl implements CacheService {
	
	@PersistenceContext
	private EntityManager em;
	@Resource(name = "shiroEhcacheManager")
	private CacheManager shiroEhcacheManager;

	public CacheManager getShiroEhcacheManager() {
		return shiroEhcacheManager;
	}

	public void setShiroEhcacheManager(CacheManager shiroEhcacheManager) {
		this.shiroEhcacheManager = shiroEhcacheManager;
	}
	/**
	 * @see com.aben.iuc.service.CacheService#clearAllCache()
	 */
	public void clearAllCache() {
		em.getEntityManagerFactory().getCache().evictAll();
	    EhCacheManager manager=(EhCacheManager)shiroEhcacheManager;
	    manager.getCacheManager().clearAll();
	}

}
