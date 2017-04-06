package com.colaui.system.service.impl;

import com.colaui.example.model.ColaPosition;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaPositionDao;
import com.colaui.system.service.ColaPositionService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaPositionServiceImpl implements ColaPositionService {
    @Autowired
    private ColaPositionDao positionDao;

    public Page<ColaPosition> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = positionDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return positionDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaPosition position) {
        positionDao.save(position);
    }

    public void delete(long id) {
        positionDao.delete(id);
    }

    public void update(ColaPosition position) {
        positionDao.update(position);
    }

    public ColaPosition find(long id) {
        return positionDao.get(id);
    }

    public List<ColaPosition> find(int from, int limit) {
        return positionDao.find(from, limit);
    }

}