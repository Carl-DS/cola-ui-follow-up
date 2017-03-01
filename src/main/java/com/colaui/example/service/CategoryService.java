package com.colaui.example.service;

import com.colaui.example.model.Category;
import com.colaui.provider.Page;

import java.util.List;
public interface CategoryService {
	Category find(long id);
	void save(Category category);
	void update(Category category);
	void delete(long id);
	List<Category> find(int from,int limit);
	Page<Category> getPage(int pageSize,int pageNo,String contain);
}
