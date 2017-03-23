package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaPosition;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaPositionDao extends HibernateDao<ColaPosition, Long> {
}