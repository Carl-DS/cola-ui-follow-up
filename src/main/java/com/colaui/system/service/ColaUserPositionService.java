package com.colaui.system.service;

import com.colaui.example.model.ColaUserPosition;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaUserPositionService {
    Page<ColaUserPosition> getPage(int pageSize,int pageNo,String contain);
    void save(ColaUserPosition userposition);
    void delete(long id);
    void update(ColaUserPosition userposition);
    ColaUserPosition find(long id);
    List<ColaUserPosition> find(int from,int limit);
}