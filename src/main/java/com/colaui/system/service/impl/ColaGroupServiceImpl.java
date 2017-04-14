package com.colaui.system.service.impl;

import com.colaui.example.model.ColaGroup;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaGroupDao;
import com.colaui.system.service.ColaGroupService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaGroupServiceImpl implements ColaGroupService {
    @Autowired
    private ColaGroupDao groupDao;

    public Page<ColaGroup> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = groupDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return groupDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaGroup group) {
        groupDao.save(group);
    }

    public void delete(String id) {
        groupDao.delete(id);
    }

    public void update(ColaGroup group) {
        groupDao.update(group);
    }

    public ColaGroup find(String id) {
        return groupDao.get(id);
    }

    public List<ColaGroup> find(int from, int limit) {
        return groupDao.find(from, limit);
    }

}