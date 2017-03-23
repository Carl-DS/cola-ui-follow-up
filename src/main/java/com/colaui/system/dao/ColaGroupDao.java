package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaGroup;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaGroupDao extends HibernateDao<ColaGroup, Long> {
}