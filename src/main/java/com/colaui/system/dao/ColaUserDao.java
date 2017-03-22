package com.colaui.system.dao;

import com.colaui.example.model.ColaUser;
import com.colaui.hibernate.HibernateDao;
import org.springframework.stereotype.Repository;

/**
 * Created by carl.li on 2017/3/3.
 */
@Repository
public class ColaUserDao extends HibernateDao<ColaUser, String>{
}
