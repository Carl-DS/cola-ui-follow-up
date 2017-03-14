package com.colaui.example.service.impl;

import com.colaui.example.dao.ColaEmployeeDao;
import com.colaui.example.model.ColaEmployee;
import com.colaui.example.service.ColaEmployeeService;
import com.colaui.provider.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("employeeService")
@Transactional
public class ColaEmployeeServiceImpl implements ColaEmployeeService {
	@Autowired
	private ColaEmployeeDao employeeDao;

	public Page<ColaEmployee> getPage(int pageSize, int pageNo, String contain) {
		Criteria criteria = employeeDao.createCriteria();
		if (StringUtils.isNotEmpty(contain)) {
			Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
			Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
			criteria.add(Restrictions.or(lastRest, firstRest));
		}
		return employeeDao.getPage(pageSize, pageNo, criteria);
	}

	public void save(ColaEmployee employee) {
		employeeDao.save(employee);
	}

	public void delete(long id) {
		employeeDao.delete(id);
	}

	public void update(ColaEmployee employee) {
		employeeDao.update(employee);
	}

	public ColaEmployee find(long id) {
		return employeeDao.get(id);
	}

	public List<ColaEmployee> find(int from, int limit) {
		return employeeDao.find(from, limit);
	}

}
