package com.colaui.system.service.impl;

import com.colaui.system.model.ColaGroup;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaGroupDao;
import com.colaui.system.dao.ColaRoleMemberDao;
import com.colaui.system.service.ColaGroupService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColaGroupServiceImpl implements ColaGroupService {
    @Autowired
    private ColaGroupDao groupDao;
    @Autowired
    private ColaRoleMemberDao roleMemberDao;

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

    public Page<ColaGroup> roleGroups(int pageSize, int pageNo, String roleId) {
        Criteria criteria = groupDao.createCriteria();
        ArrayList groupIds = null;
        if (StringUtils.isNotEmpty(roleId)) {
            groupIds = roleMemberDao.getGroupIds(roleId);
            if (groupIds.size() > 0) {
                criteria.add(Restrictions.in("id", groupIds));
                return groupDao.getPage(pageSize, pageNo, criteria);
            }
        }
        return null;
    }

}