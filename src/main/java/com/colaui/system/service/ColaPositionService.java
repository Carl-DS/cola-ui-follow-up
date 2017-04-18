package com.colaui.system.service;

import com.colaui.example.model.ColaPosition;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaPositionService {
    Page<ColaPosition> getPage(int pageSize,int pageNo,String contain);
    void save(ColaPosition position);
    void delete(long id);
    void update(ColaPosition position);
    ColaPosition find(long id);
    List<ColaPosition> find(int from,int limit);

    Page<ColaPosition> groupPositions(int pageSize, int pageNo, String groupId);

    Page<ColaPosition> rolePositions(int pageSize, int pageNo, String roleId);
}