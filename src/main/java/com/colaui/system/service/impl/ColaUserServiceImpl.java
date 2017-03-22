package com.colaui.system.service.impl;

import com.colaui.example.model.ColaUser;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaUserDao;
import com.colaui.system.service.ColaUserService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUserServiceImpl implements ColaUserService{
    @Autowired
    private ColaUserDao colaUserDao;

    public Page<ColaUser> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = colaUserDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return colaUserDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUser colaUser) {
        colaUserDao.save(colaUser);
    }

    public void delete(String id) {
        colaUserDao.delete(id);
    }

    public void update(ColaUser colaUser) {
        colaUserDao.update(colaUser);
    }

    public ColaUser find(String id) {
        return colaUserDao.get(id);
    }

    public List<ColaUser> find(int from, int limit) {
        return colaUserDao.find(from, limit);
    }
}
