package com.colaui.system.service.impl;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaRoleResourceDao;
import com.colaui.system.service.ColaRoleResourceService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaRoleResourceServiceImpl implements ColaRoleResourceService {
    @Autowired
    private ColaRoleResourceDao roleresourceDao;

    public Page<ColaRoleResource> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = roleresourceDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return roleresourceDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaRoleResource roleresource) {
        roleresourceDao.save(roleresource);
    }

    public void delete(long id) {
        roleresourceDao.delete(id);
    }

    public void update(ColaRoleResource roleresource) {
        roleresourceDao.update(roleresource);
    }

    public ColaRoleResource find(long id) {
        return roleresourceDao.get(id);
    }

    public List<ColaRoleResource> find(int from, int limit) {
        return roleresourceDao.find(from, limit);
    }

}