package com.colaui.system.service.impl;

import com.colaui.system.dao.ColaRoleMemberDao;
import com.colaui.system.dao.ColaRoleResourceDao;
import com.colaui.system.dao.ColaUrlDao;
import com.colaui.system.model.ColaRoleResource;
import com.colaui.system.model.ColaUrl;
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
    @Autowired
    private ColaRoleMemberDao roleMemberDao;

    public List<ColaUrl> getUrls(String companyId, String parentId) {
        Criteria criteria = colaUrlDao.createCriteria();
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
        //Object obj = params.get("forNavigation");
        //if (obj != null) {
        //    criteria.add(Restrictions.eq("forNavigation", (Boolean) obj));
        //}

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
        // 获取当前公司所拥有的菜单
        List<ColaUrl> urls = getUrls("bstek", parentId);
        List<ColaRoleResource> roleResources = roleResourceDao.getRoleUrlsByRoleId(roleId);
        List<String> roleUrls = new ArrayList<>();
        // 取出角色对应的资源id
        for (ColaRoleResource resource : roleResources) {
            roleUrls.add(resource.getUrlId());
        }
        checkForNavigation(urls,roleUrls);
        return urls;
    }

    @Override
    public List<ColaUrl> getRoleUrls(String companyId, String roleId) {

        //
        List<ColaUrl> urls = getUrls("bstek", null);
        // 根据roleId 获取角色对应的菜单
        List<ColaRoleResource> roleResources = roleResourceDao.getRoleUrlsByRoleId(roleId);
        List<String> roleUrlIds = new ArrayList<>();
        // 取出角色对应的菜单id
        for (ColaRoleResource resource : roleResources) {
            roleUrlIds.add(resource.getUrlId());
        }
        checkForNavigation(urls,roleUrlIds);
        recursiveMenus(urls);
        return urls;
    }

    private void recursiveMenus(List<ColaUrl> urls) {
        Iterator it = urls.iterator();
        while(it.hasNext()) {
            ColaUrl url = (ColaUrl) it.next();
            if (!url.isForNavigation()) {
                it.remove();
            }
            if (url.getMenus() != null && url.getMenus().size() > 0) {
                recursiveMenus((List<ColaUrl>) url.getMenus());
            }
        }
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
