package com.colaui.example.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaProduct;
import com.colaui.helper.hibernate.HibernateDao;
@Repository
public class ColaProductDao extends HibernateDao<ColaProduct, Long> {

}
