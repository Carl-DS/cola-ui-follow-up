package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaRoleMemberDao;
import com.colaui.example.model.ColaRoleMember;
import com.colaui.system.service.ColaRoleMemberService;
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

@Service("rolememberService")
@Transactional
public class ColaRoleMemberServiceImpl implements ColaRoleMemberService {
    @Autowired
    private ColaRoleMemberDao rolememberDao;

    public Page<ColaRoleMember> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return rolememberDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaRoleMember rolemember) {
        rolememberDao.save(rolemember);
    }

    public void delete(long id) {
        rolememberDao.delete(id);
    }

    public void update(ColaRoleMember rolemember) {
        rolememberDao.update(rolemember);
    }

    public ColaRoleMember find(long id) {
        return rolememberDao.get(id);
    }

    public List<ColaRoleMember> find(int from, int limit) {
        return rolememberDao.find(from, limit);
    }

}