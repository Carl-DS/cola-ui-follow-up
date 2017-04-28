package com.colaui.example.service;

import com.colaui.example.model.ColaCategory;
import com.colaui.helper.Page;

import java.util.List;
public interface ColaCategoryService {
	Page<ColaCategory> getPage(int pageSize, int pageNo, String contain);
	void save(ColaCategory category);
	void delete(long id);
	void update(ColaCategory category);
	ColaCategory find(long id);
	List<ColaCategory> find(int from, int limit);
}
