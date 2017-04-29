package com.colaui.system.service;

import com.colaui.system.model.ColaUrl;

import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
public interface ColaUrlService {

    List<ColaUrl> getUrls(String companyId, String parentId);

    void saveUrl(ColaUrl url);

    void deleteUrl(String id);

    List<ColaUrl> findUrlByRoleId(String roleId, String companyId, String parentId);

    List<ColaUrl> getRoleUrls(String companyId, String roleId);
}
