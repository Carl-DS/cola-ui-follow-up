package com.colaui.example.service;

import java.util.List;

import com.colaui.example.model.Employee;
import com.colaui.provider.Page;
public interface EmployeeService {
	Employee find(long id);
	void save(Employee employee);
	void update(Employee employee);
	void delete(long id);
	List<Employee> find(int from,int limit);
	Page<Employee> getPage(int pageSize,int pageNo,String contain);
}
