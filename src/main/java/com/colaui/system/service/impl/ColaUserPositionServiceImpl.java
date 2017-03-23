package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaUserPositionDao;
import com.colaui.example.model.ColaUserPosition;
import com.colaui.system.service.ColaUserPositionService;
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

@Service("userpositionService")
@Transactional
public class ColaUserPositionServiceImpl implements ColaUserPositionService {
    @Autowired
    private ColaUserPositionDao userpositionDao;

    public Page<ColaUserPosition> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = userpositionDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return userpositionDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUserPosition userposition) {
        userpositionDao.save(userposition);
    }

    public void delete(long id) {
        userpositionDao.delete(id);
    }

    public void update(ColaUserPosition userposition) {
        userpositionDao.update(userposition);
    }

    public ColaUserPosition find(long id) {
        return userpositionDao.get(id);
    }

    public List<ColaUserPosition> find(int from, int limit) {
        return userpositionDao.find(from, limit);
    }

}