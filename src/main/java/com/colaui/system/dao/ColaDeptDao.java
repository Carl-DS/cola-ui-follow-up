package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaDept;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaDeptDao extends HibernateDao<ColaDept, String> {
}