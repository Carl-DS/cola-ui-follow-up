package com.colaui.system.dao;

import com.colaui.system.model.ColaRoleResource;
import com.colaui.helper.hibernate.HibernateDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ColaRoleResourceDao extends HibernateDao<ColaRoleResource, String> {

    public List<ColaRoleResource> getRoleUrls(Map<String, Object> params) {
            String roleId=(String) params.get("roleId");
            Criteria c=this.getSession().createCriteria(ColaRoleResource.class);
            c.add(Restrictions.eq("roleId", roleId));
            List<ColaRoleResource> list=c.list();
            return list;
    }
}