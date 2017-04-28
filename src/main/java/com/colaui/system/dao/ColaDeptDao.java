package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.system.model.ColaDept;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaDeptDao extends HibernateDao<ColaDept, String> {
}