package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaComponent;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaComponentDao extends HibernateDao<ColaComponent, Long> {
}