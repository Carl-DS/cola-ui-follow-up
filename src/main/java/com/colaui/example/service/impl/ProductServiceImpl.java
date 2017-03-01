package com.colaui.example.service.impl;

import com.colaui.example.dao.ProductDao;
import com.colaui.example.model.Product;
import com.colaui.example.service.ProductService;
import com.colaui.provider.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	public Product find(long id) {
		return productDao.get(id);
	}

	public void save(Product product) {
		productDao.save(product);
	}

	public void update(Product product) {
		productDao.update(product);
	}

	public void delete(long id) {

	}

	public List<Product> find(int from, int limit) {
		return productDao.find(from, limit);
	}

	public Page<Product> queryOfCategoryId(int pageSize, int pageNo, long categoryId, String sort) {
		Criteria criteria = productDao.createCriteria();
		if (categoryId>0) {
			Criterion lastRest = Restrictions.eq("categoryId", categoryId);
			criteria.add(lastRest);
		}
		// 处理排序参数
		if (StringUtils.hasText(sort)) {
			String field = sort.substring(1);
			if (sort.substring(0, 1).equals("+")) {
				criteria.addOrder(Order.asc(field));
			} else {
				criteria.addOrder(Order.desc(field));
			}
		}

		return productDao.getPage(pageSize, pageNo, criteria);
	}

}
