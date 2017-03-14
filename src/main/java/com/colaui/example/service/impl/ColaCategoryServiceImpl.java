package com.colaui.example.service.impl;

import com.colaui.example.dao.ColaCategoryDao;
import com.colaui.example.model.ColaCategory;
import com.colaui.example.service.ColaCategoryService;
import com.colaui.provider.Page;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("categoryService")
@Transactional
public class ColaCategoryServiceImpl implements ColaCategoryService {
	@Autowired
	private ColaCategoryDao categoryDao;

	public Page<ColaCategory> getPage(int pageSize, int pageNo, String contain) {
		Criteria criteria = categoryDao.createCriteria();
		//if (StringUtils.isNotEmpty(contain)) {
		//	Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
		//	Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
		//	criteria.add(Restrictions.or(lastRest, firstRest));
		//}
		return categoryDao.getPage(pageSize, pageNo, criteria);
	}

	public void save(ColaCategory category) {
		categoryDao.save(category);
	}

	public void delete(long id) {
		categoryDao.delete(id);
	}

	public void update(ColaCategory category) {
		categoryDao.update(category);
	}

	public ColaCategory find(long id) {
		return this.categoryDao.get(id);
	}

	public List<ColaCategory> find(int from, int limit) {
		return categoryDao.find(from, limit);
	}

}
