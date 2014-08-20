package com.aben.iuc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aben.iuc.service.SortService;

@Controller
@RequestMapping("/management/sysSort")
public class SortController {
	private static final String FIND_SORT = "management/security/module/lookup_sort";
	@Autowired
	private SortService sortService;

	/**
	 * 获取所有分类信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getAllSort(Map<String, Object> map) {
		map.put("sort", sortService.findAll());
		return FIND_SORT;
	}
}
