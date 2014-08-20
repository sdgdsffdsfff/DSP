package com.aben.iuc.service;

import java.util.List;
 
import com.aben.iuc.entity.main.Sort;


public interface SortService {
	/**
	 * 查询所有的分类信息
	 * @return
	 */
	public List<Sort> findAll();
}
