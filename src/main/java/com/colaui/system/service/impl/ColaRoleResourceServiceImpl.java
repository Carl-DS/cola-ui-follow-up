package com.colaui.system.service.impl;

import com.colaui.system.model.ColaRoleResource;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaRoleResourceDao;
import com.colaui.system.service.ColaRoleResourceService;
import com.colaui.utils.CommonUtils;
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

    public List<ColaRoleResource> findByRoleUrlId(String roleId, String urlId) {
        Criteria criteria = roleresourceDao.createCriteria();
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(urlId)) {
            Criterion lastRest= Restrictions.eq("roleId", roleId);
            Criterion firstRest= Restrictions.eq("urlId", urlId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return roleresourceDao.find(criteria);
    }

    public void save(ColaRoleResource roleresource) {
        roleresourceDao.save(roleresource);
    }

    public void delete(String id) {
        roleresourceDao.delete(id);
    }

    public void update(ColaRoleResource roleresource) {
        roleresourceDao.update(roleresource);
    }

    public ColaRoleResource find(String id) {
        return roleresourceDao.get(id);
    }

    public List<ColaRoleResource> find(int from, int limit) {
        return roleresourceDao.find(from, limit);
    }

    @Override
    public void saveOrDelete(String roleId, ArrayList<String> urlIds, ArrayList<String> excludeUrlIds) {
        ColaRoleResource rr = null;
        List<ColaRoleResource> list = null;
        for (String urlId : urlIds) { // 选中的节点(新选中或末改变)
            list = findByRoleUrlId(roleId, urlId);
            if (list.size() > 0) continue;
            rr = new ColaRoleResource();
            rr.setId(CommonUtils.uuid());
            rr.setRoleId(roleId);
            rr.setUrlId(urlId);
            roleresourceDao.save(rr);
        }
        for (String excludeUrlId : excludeUrlIds) { // 末选中的节点(原选中后取消,或从未选中)
            list = findByRoleUrlId(roleId, excludeUrlId);
            if (list.size() == 0) continue;
            roleresourceDao.delete(list.get(0).getId());
        }
    }

}