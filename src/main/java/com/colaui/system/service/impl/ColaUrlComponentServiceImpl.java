package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaUrlComponentDao;
import com.colaui.example.model.ColaUrlComponent;
import com.colaui.system.service.ColaUrlComponentService;
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

@Service("urlcomponentService")
@Transactional
public class ColaUrlComponentServiceImpl implements ColaUrlComponentService {
    @Autowired
    private ColaUrlComponentDao urlcomponentDao;

    public Page<ColaUrlComponent> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = urlcomponentDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return urlcomponentDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUrlComponent urlcomponent) {
        urlcomponentDao.save(urlcomponent);
    }

    public void delete(long id) {
        urlcomponentDao.delete(id);
    }

    public void update(ColaUrlComponent urlcomponent) {
        urlcomponentDao.update(urlcomponent);
    }

    public ColaUrlComponent find(long id) {
        return urlcomponentDao.get(id);
    }

    public List<ColaUrlComponent> find(int from, int limit) {
        return urlcomponentDao.find(from, limit);
    }

}