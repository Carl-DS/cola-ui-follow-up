package com.colaui.example.service.impl;

import com.colaui.example.dao.CategoryDao;
import com.colaui.example.model.Category;
import com.colaui.example.service.CategoryService;
import com.colaui.provider.Page;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public Category find(long id) {
		return this.categoryDao.get(id);
	}

	public void save(Category category) {
		categoryDao.save(category);
	}

	public void update(Category category) {
		categoryDao.update(category);

	}

	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	public List<Category> find(int from, int limit) {
		return categoryDao.find(from, limit);
	}

	public Page<Category> getPage(int pageSize, int pageNo, String contain) {
		Criteria criteria = categoryDao.createCriteria();
		//if (StringUtils.isNotEmpty(contain)) {
		//	Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
		//	Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
		//	criteria.add(Restrictions.or(lastRest, firstRest));
		//}
		return categoryDao.getPage(pageSize, pageNo, criteria);
	}

}
