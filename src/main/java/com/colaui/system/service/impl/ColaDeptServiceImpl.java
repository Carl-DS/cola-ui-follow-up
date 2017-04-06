package com.colaui.system.service.impl;

import com.colaui.example.model.ColaDept;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaDeptDao;
import com.colaui.system.service.ColaDeptService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ColaDeptServiceImpl implements ColaDeptService {
    @Autowired
    private ColaDeptDao deptDao;

    public Page<ColaDept> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = deptDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return deptDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaDept dept) {
        deptDao.save(dept);
    }

    public void delete(String id) {
        deptDao.delete(id);
    }

    public void update(ColaDept dept) {
        deptDao.update(dept);
    }

    public ColaDept find(String id) {
        return deptDao.get(id);
    }

    public List<ColaDept> find(int from, int limit) {
        return deptDao.find(from, limit);
    }

    @Override
    public List<ColaDept> getDepts(Map<String, Object> params) {
            Criteria criteria=deptDao.createCriteria();
            if(params!=null&&params.size()>0){
                String companyId=(String) params.get("companyId");
                String parentId=(String) params.get("parentId");
                if(org.springframework.util.StringUtils.hasText(companyId)){
                    criteria.add(Restrictions.eq("companyId", companyId));
                }
                if(org.springframework.util.StringUtils.hasText(parentId)){
                    criteria.add(Restrictions.eq("parentId", parentId));
                }else{
                    criteria.add(Restrictions.isNull("parentId"));
                    if(!org.springframework.util.StringUtils.hasText(companyId)){
                        criteria.add(Restrictions.eq("companyId", null));
                    }
                }
            }
            criteria.add(Restrictions.isNull("parent"));
            //criteria.addOrder(Order.asc("order"));
            return deptDao.find(criteria);
    }

}