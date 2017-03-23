package com.colaui.system.service;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaRoleResourceService {
    Page<ColaRoleResource> getPage(int pageSize,int pageNo,String contain);
    void save(ColaRoleResource roleresource);
    void delete(long id);
    void update(ColaRoleResource roleresource);
    ColaRoleResource find(long id);
    List<ColaRoleResource> find(int from,int limit);
}