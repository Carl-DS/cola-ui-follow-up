package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaUserDeptDao;
import com.colaui.example.model.ColaUserDept;
import com.colaui.system.service.ColaUserDeptService;
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

@Service("userdeptService")
@Transactional
public class ColaUserDeptServiceImpl implements ColaUserDeptService {
    @Autowired
    private ColaUserDeptDao userdeptDao;

    public Page<ColaUserDept> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = userdeptDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return userdeptDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUserDept userdept) {
        userdeptDao.save(userdept);
    }

    public void delete(long id) {
        userdeptDao.delete(id);
    }

    public void update(ColaUserDept userdept) {
        userdeptDao.update(userdept);
    }

    public ColaUserDept find(long id) {
        return userdeptDao.get(id);
    }

    public List<ColaUserDept> find(int from, int limit) {
        return userdeptDao.find(from, limit);
    }

}