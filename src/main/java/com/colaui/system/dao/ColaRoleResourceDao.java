package com.colaui.system.dao;

import com.colaui.helper.hibernate.HibernateDao;
import com.colaui.system.model.ColaRoleResource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ColaRoleResourceDao extends HibernateDao<ColaRoleResource, String> {

    public List<ColaRoleResource> getRoleUrlsByRoleId(String roleId) {
            Criteria c=this.getSession().createCriteria(ColaRoleResource.class);
            c.add(Restrictions.eq("roleId", roleId));
            List<ColaRoleResource> list=c.list();
            return list;
    }
}