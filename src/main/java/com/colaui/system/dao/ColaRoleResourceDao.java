package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaRoleResourceDao extends HibernateDao<ColaRoleResource, Long> {
}