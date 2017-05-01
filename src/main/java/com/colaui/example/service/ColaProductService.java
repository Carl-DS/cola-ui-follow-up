package com.colaui.example.service;

import com.colaui.example.model.ColaProduct;
import com.colaui.helper.Page;

import java.util.List;
public interface ColaProductService {
	Page<ColaProduct> queryOfCategoryId(int pageSize, int pageNo, long categoryId, String sort);
	void save(ColaProduct product);
	void delete(long id);
	void update(ColaProduct product);
	ColaProduct find(long id);
	List<ColaProduct> find(int from, int limit);
}
