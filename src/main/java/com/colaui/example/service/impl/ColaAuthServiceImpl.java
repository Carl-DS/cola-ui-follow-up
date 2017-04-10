package com.colaui.example.service.impl;

import com.colaui.example.dao.ColaAuthDao;
import com.colaui.example.model.ColaAuth;
import com.colaui.example.service.ColaAuthService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Carl on 2017/4/9.
 */
@Service
public class ColaAuthServiceImpl implements ColaAuthService{

    @Autowired
    private ColaAuthDao colaAuthDao;

    @Override
    public void save(ColaAuth auth) {
        colaAuthDao.save(auth);
    }

    @Override
    public List<ColaAuth> getAuths(String user) {
        Criteria criteria = colaAuthDao.createCriteria();
        criteria.add(Restrictions.eq("user", user));
        return criteria.list();
    }
}
