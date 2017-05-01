package com.colaui.system.service;

import com.colaui.system.model.ColaDept;
import com.colaui.helper.Page;

import java.util.List;
import java.util.Map;

public interface ColaDeptService {
    Page<ColaDept> getPage(int pageSize, int pageNo, String contain);

    void save(ColaDept dept);

    void delete(String id);

    void update(ColaDept dept);

    ColaDept find(String id);

    List<ColaDept> find(int from, int limit);

    List<ColaDept> getDepts(Map<String, Object> params);

    Page<ColaDept> groupDepts(int pageSize, int pageNo, String groupId);

    Page<ColaDept> roleDepts(int pageSize, int pageNo, String roleId);
}