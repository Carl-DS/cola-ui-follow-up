package com.colaui.system.dao;

import com.colaui.system.model.AuthorityType;
import com.colaui.system.model.ColaComponent;
import com.colaui.system.model.ColaUrl;
import com.colaui.system.model.ColaUrlComponent;
import com.colaui.helper.hibernate.HibernateDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ColaComponentDao extends HibernateDao<ColaComponent, Long> {
    public Map<String, Boolean> getComponentAuth(Map<String, String> params) {
        Map<String, Boolean> authMap = new HashMap<String, Boolean>();
        boolean editable = false, visible = false;
        String companyId = "bstek";
        String url = params.get("url");
        String componentId = params.get("componentId");
        if (StringUtils.hasText(url) && StringUtils.hasText(componentId)) {
            Criteria c1 = this.getSession().createCriteria(ColaUrlComponent.class);
            DetachedCriteria dc1 = DetachedCriteria.forClass(ColaUrl.class);
            dc1.add(Restrictions.eq("path", url)).add(Restrictions.eq("companyId", companyId)).setProjection(Projections.property("id"));
            DetachedCriteria dc2 = DetachedCriteria.forClass(ColaComponent.class);
            dc2.add(Restrictions.eq("componentId", componentId)).setProjection(Projections.property("id"));
            c1.add(Subqueries.propertyIn("urlId", dc1));
            c1.add(Subqueries.propertyIn("component.id", dc2));
            List<ColaUrlComponent> ucList = c1.list();
            if (ucList != null && ucList.size() > 0) {
                for (ColaUrlComponent uc : ucList) {
                    if (uc.getAuthorityType().equals(AuthorityType.read)) {
                        visible = true;
                    } else if (uc.getAuthorityType().equals(AuthorityType.write)) {
                        editable = true;
                    }
                }
            }
        }
        authMap.put("editable", editable);
        authMap.put("visible", visible);
        return authMap;
    }
}