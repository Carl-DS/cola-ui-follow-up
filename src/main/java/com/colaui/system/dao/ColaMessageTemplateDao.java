package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.system.model.ColaMessageTemplate;
import com.colaui.helper.hibernate.HibernateDao;

@Repository
public class ColaMessageTemplateDao extends HibernateDao<ColaMessageTemplate, String> {
}