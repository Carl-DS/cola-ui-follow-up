package com.colaui.system.dao;

import com.colaui.example.model.AuthorityType;
import com.colaui.example.model.ColaComponent;
import com.colaui.example.model.ColaUrlComponent;
import com.colaui.hibernate.HibernateDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ColaComponentDao extends HibernateDao<ColaComponent, Long> {
    public Map<String, Boolean> getComponentAuth(Map<String, String> params) {
        Map<String, Boolean> authMap = new HashMap<String, Boolean>();
        boolean editable = false, visible = true;
        //String companyId = ContextHolder.getLoginUser().getCompanyId();
        //String companyId = "bstek";
        String url = params.get("url");
        String componentId = params.get("componentId");
        if (StringUtils.hasText(url) && StringUtils.hasText(componentId)) {
            Criteria criteria = this.getSession().createCriteria(ColaUrlComponent.class);
            criteria.add(Restrictions.eq("componentId", componentId));

            List<ColaUrlComponent> ucList = criteria.list();
            if (ucList != null && ucList.size() > 0) {
                for (ColaUrlComponent uc : ucList) {
                    if (uc.getAuthorityType().equals(AuthorityType.read.toString())) {
                        visible = true;
                    } else if (uc.getAuthorityType().equals(AuthorityType.write.toString())) {
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