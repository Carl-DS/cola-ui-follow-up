package com.colaui.example.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaEmployee;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaEmployeeDao extends HibernateDao<ColaEmployee, Long> {
}
