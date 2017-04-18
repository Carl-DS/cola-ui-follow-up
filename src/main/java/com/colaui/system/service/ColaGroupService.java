package com.colaui.system.service;

import com.colaui.example.model.ColaGroup;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaGroupService {
    Page<ColaGroup> getPage(int pageSize,int pageNo,String contain);
    void save(ColaGroup group);
    void delete(String id);
    void update(ColaGroup group);
    ColaGroup find(String id);
    List<ColaGroup> find(int from,int limit);

    Page<ColaGroup> roleGroups(int pageSize, int pageNo, String roleId);
}