package com.colaui.example.service.impl;

import java.util.List;

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
	public Page<Employee> getPage(int pageSize, int pageNo) {
		return employeeDao.getPage(pageSize, pageNo);
	}

}
