package com.colaui.system.service.impl;

import com.colaui.example.model.ColaUrl;
import com.colaui.system.dao.ColaUrlDao;
import com.colaui.system.service.ColaUrlService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUrlServiceImpl implements ColaUrlService{

    @Autowired
    private ColaUrlDao colaUrlDao;

    public List<ColaUrl> getUrl(String id) {
        Criteria criteria = colaUrlDao.createCriteria();
        criteria.add(Restrictions.eq("parentId", id));
        return colaUrlDao.find(criteria);
    }
}
