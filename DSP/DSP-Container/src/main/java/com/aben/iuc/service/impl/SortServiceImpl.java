package com.aben.iuc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aben.iuc.dao.SortDao;
import com.aben.iuc.entity.main.Sort;
import com.aben.iuc.service.SortService;

@Service
public class SortServiceImpl implements SortService{
	@Autowired
	SortDao sortDao;
	@Override
	public List<Sort> findAll() {
		// TODO Auto-generated method stub
		return sortDao.findAll();
	}

}
