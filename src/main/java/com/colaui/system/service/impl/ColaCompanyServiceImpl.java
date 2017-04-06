package com.colaui.system.service.impl;

import com.colaui.example.model.ColaCompany;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaCompanyDao;
import com.colaui.system.service.ColaCompanyService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaCompanyServiceImpl implements ColaCompanyService {
    @Autowired
    private ColaCompanyDao companyDao;

    public Page<ColaCompany> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = companyDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return companyDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaCompany company) {
        companyDao.save(company);
    }

    public void delete(long id) {
        companyDao.delete(id);
    }

    public void update(ColaCompany company) {
        companyDao.update(company);
    }

    public ColaCompany find(long id) {
        return companyDao.get(id);
    }

    public List<ColaCompany> find(int from, int limit) {
        return companyDao.find(from, limit);
    }

}