package com.colaui.system.service.impl;

import com.colaui.example.model.ColaUrl;
import com.colaui.system.dao.ColaUrlDao;
import com.colaui.system.service.ColaUrlService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUrlServiceImpl implements ColaUrlService{

    @Autowired
    private ColaUrlDao colaUrlDao;

    public List<ColaUrl> geturls() {
        Criteria criteria = colaUrlDao.createCriteria();
        Criterion lastRest = Restrictions.isNull("parent");
        criteria.add(lastRest);
        return colaUrlDao.find(criteria);
    }

}
