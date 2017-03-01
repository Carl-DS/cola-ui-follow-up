package com.colaui.example.service;

import com.colaui.example.model.Product;
import com.colaui.provider.Page;

import java.util.List;
public interface ProductService {
	Product find(long id);
	void save(Product product);
	void update(Product product);
	void delete(long id);
	List<Product> find(int from,int limit);
	Page<Product> queryOfCategoryId(int pageSize, int pageNo, long categoryId, String sort);
}
