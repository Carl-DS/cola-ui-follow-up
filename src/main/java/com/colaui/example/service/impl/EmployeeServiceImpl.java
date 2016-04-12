package com.colaui.example.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colaui.example.dao.EmployeeDao;
import com.colaui.example.model.Employee;
import com.colaui.example.service.EmployeeService;
import com.colaui.provider.Page;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public Employee find(long id) {
		return employeeDao.get(id);
	}

	@Override
	public void save(Employee employee) {
		employeeDao.save(employee);
	}

	@Override
	public void update(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public void delete(long id) {
		employeeDao.delete(id);
	}

	@Override
	public List<Employee> find(int from, int limit) {
		return employeeDao.find(from, limit);
	}

	@Override
	public Page<Employee> getPage(int pageSize, int pageNo, String contain) {
		Criteria criteria = employeeDao.createCriteria();
		if (StringUtils.isNotEmpty(contain)) {
			Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
			Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
			criteria.add(Restrictions.or(lastRest, firstRest));
		}
		return employeeDao.getPage(pageSize, pageNo, criteria);
	}

}
