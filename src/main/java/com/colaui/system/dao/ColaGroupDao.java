package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.system.model.ColaGroup;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaGroupDao extends HibernateDao<ColaGroup, String> {
}