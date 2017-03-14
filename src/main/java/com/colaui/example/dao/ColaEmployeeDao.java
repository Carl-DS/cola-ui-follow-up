package com.colaui.example.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaEmployee;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaEmployeeDao extends HibernateDao<ColaEmployee, Long> {
}
