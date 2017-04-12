package com.colaui.system.service.impl;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.example.model.ColaUrl;
import com.colaui.system.dao.ColaRoleResourceDao;
import com.colaui.system.dao.ColaUrlDao;
import com.colaui.system.service.ColaUrlService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUrlServiceImpl implements ColaUrlService {

    @Autowired
    private ColaUrlDao colaUrlDao;
    @Autowired
    private ColaRoleResourceDao roleResourceDao;

    public List<ColaUrl> getUrls(Map<String, Object> params) {
        Criteria criteria = colaUrlDao.createCriteria();
        if (params != null && params.size() > 0) {
            String companyId = (String) params.get("companyId");
            String parentId = (String) params.get("parentId");
            if (StringUtils.hasText(companyId)) {
                criteria.add(Restrictions.eq("companyId", companyId));
            }
            if (StringUtils.hasText(parentId)) {
                criteria.add(Restrictions.eq("parentId", parentId));
            } else {
                criteria.add(Restrictions.isNull("parentId"));
                if (!StringUtils.hasText(companyId)) {
                    criteria.add(Restrictions.eq("companyId", null));
                }
            }
            Object obj = params.get("forNavigation");
            if (obj != null) {
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

    public List<ColaUrl> findUrlByRoleId(String roleId, String companyId, String parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", "bstek");
        params.put("roleId", roleId);
        params.put("parentId", parentId);
        List<ColaUrl> urls = getUrls(params);
        List<ColaRoleResource> roleResources = roleResourceDao.getRoleUrls(params);
        List<String> roleUrls = new ArrayList<>();
        // 取出角色对应的资源id
        for (ColaRoleResource resource : roleResources) {
            roleUrls.add(resource.getUrlId());
        }
        checkForNavigation(urls,roleUrls);
        return urls;
    }

    private void checkForNavigation(Collection<ColaUrl> urls, List<String> roleUrls) {
        if (urls.size() > 0) {
            for (ColaUrl url:urls) {
                // 与全部资源匹配
                if (roleUrls.contains(url.getId())) {
                    url.setForNavigation(true);
                } else {
                    url.setForNavigation(false);
                }
                checkForNavigation(url.getMenus(), roleUrls);
            }
        }
    }

}
