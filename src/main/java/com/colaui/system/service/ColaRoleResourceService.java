package com.colaui.system.service;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.provider.Page;

import java.util.ArrayList;
import java.util.List;
public interface ColaRoleResourceService {
    Page<ColaRoleResource> getPage(int pageSize,int pageNo,String contain);
    void save(ColaRoleResource roleresource);
    void delete(String id);
    void update(ColaRoleResource roleresource);
    ColaRoleResource find(String id);
    List<ColaRoleResource> find(int from,int limit);

    void saveOrDelete(String roleId, ArrayList<String> urlIds, ArrayList<String> excludeUrlIds);
}