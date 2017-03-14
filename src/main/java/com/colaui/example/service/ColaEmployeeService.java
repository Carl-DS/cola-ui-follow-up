package com.colaui.example.service;

import java.util.List;

import com.colaui.example.model.ColaEmployee;
import com.colaui.provider.Page;
public interface ColaEmployeeService {
	Page<ColaEmployee> getPage(int pageSize,int pageNo,String contain);
	void save(ColaEmployee employee);
	void delete(long id);
	void update(ColaEmployee employee);
	ColaEmployee find(long id);
	List<ColaEmployee> find(int from,int limit);
}
