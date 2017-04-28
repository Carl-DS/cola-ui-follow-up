package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.system.model.ColaRole;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaRoleDao extends HibernateDao<ColaRole, String> {
}