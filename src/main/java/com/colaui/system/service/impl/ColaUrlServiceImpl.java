package com.colaui.system.service.impl;

import com.colaui.example.model.ColaUrl;
import com.colaui.system.dao.ColaUrlDao;
import com.colaui.system.service.ColaUrlService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUrlServiceImpl implements ColaUrlService{

    @Autowired
    private ColaUrlDao colaUrlDao;

    public List<ColaUrl> getUrls(Map<String, Object> params) {
        Criteria criteria=colaUrlDao.createCriteria();
        if(params!=null&&params.size()>0){
            String companyId=(String) params.get("companyId");
            String parentId=(String) params.get("parentId");
            if(StringUtils.hasText(companyId)){
                criteria.add(Restrictions.eq("companyId", companyId));
            }
            if(StringUtils.hasText(parentId)){
                criteria.add(Restrictions.eq("parentId", parentId));
            }else{
                criteria.add(Restrictions.isNull("parentId"));
                if(!StringUtils.hasText(companyId)){
                    criteria.add(Restrictions.eq("companyId", null));
                }
            }
            Object obj= params.get("forNavigation");
            if(obj!=null){
                criteria.add(Restrictions.eq("forNavigation", (Boolean) obj));
            }
        }
        criteria.add(Restrictions.isNull("parent"));
        criteria.addOrder(Order.asc("order"));
        return colaUrlDao.find(criteria);
    }

    public void saveUrl(ColaUrl url) {
        colaUrlDao.save(url);
    }

    public void deleteUrl(String id) {
        colaUrlDao.delete(id);
    }

}
