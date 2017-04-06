package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaMessageTemplate;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaMessageTemplateDao extends HibernateDao<ColaMessageTemplate, String> {
}