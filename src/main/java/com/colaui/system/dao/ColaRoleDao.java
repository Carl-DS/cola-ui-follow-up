package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaRole;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaRoleDao extends HibernateDao<ColaRole, Long> {
}