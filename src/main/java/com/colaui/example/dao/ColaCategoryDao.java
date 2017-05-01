package com.colaui.example.dao;

import com.colaui.example.model.ColaCategory;
import org.springframework.stereotype.Repository;

import com.colaui.helper.hibernate.HibernateDao;
@Repository
public class ColaCategoryDao extends HibernateDao<ColaCategory, Long> {
}
