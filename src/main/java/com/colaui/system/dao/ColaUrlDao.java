package com.colaui.system.dao;

import com.colaui.example.model.ColaUrl;
import com.colaui.hibernate.HibernateDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@Repository
public class ColaUrlDao extends HibernateDao<ColaUrl, String>{

}
