package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.system.model.ColaPosition;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaPositionDao extends HibernateDao<ColaPosition, Long> {
}