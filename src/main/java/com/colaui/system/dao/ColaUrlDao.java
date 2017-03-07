package com.colaui.system.dao;

import com.colaui.example.model.ColaUrl;
import com.colaui.hibernate.HibernateDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@Repository
public class ColaUrlDao extends HibernateDao<ColaUrl, Long>{

    public List<ColaUrl> getUrls(Map<String, Object> params) {
        List<ColaUrl> uList=null;
        if(params!=null){
            String companyId=(String) params.get("companyId");
            String parentId=(String) params.get("parentId");
            Criteria c=this.getSession().createCriteria(ColaUrl.class);
            if(StringUtils.hasText(companyId)){
                c.add(Restrictions.eq("companyId", companyId));
            }
            if(StringUtils.hasText(parentId)&&!"null".equals(parentId)){
                c.add(Restrictions.eq("parentId", parentId));
            }else{
                c.add(Restrictions.isNull("parentId"));
                if(!StringUtils.hasText(companyId)){
                    c.add(Restrictions.eq("companyId", null));
                }
            }
            Object obj= params.get("forNavigation");
            if(obj!=null){
                c.add(Restrictions.eq("forNavigation", (Boolean) obj));
            }
            c.addOrder(Order.asc("order"));
            uList=c.list();
        }
        return uList;
    }
}
