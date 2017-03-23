package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaDeptDao;
import com.colaui.example.model.ColaDept;
import com.colaui.system.service.ColaDeptService;
import com.colaui.provider.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("deptService")
@Transactional
public class ColaDeptServiceImpl implements ColaDeptService {
    @Autowired
    private ColaDeptDao deptDao;

    public Page<ColaDept> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = deptDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return deptDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaDept dept) {
        deptDao.save(dept);
    }

    public void delete(long id) {
        deptDao.delete(id);
    }

    public void update(ColaDept dept) {
        deptDao.update(dept);
    }

    public ColaDept find(long id) {
        return deptDao.get(id);
    }

    public List<ColaDept> find(int from, int limit) {
        return deptDao.find(from, limit);
    }

}