package com.colaui.system.service;

import com.colaui.example.model.ColaDept;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaDeptService {
    Page<ColaDept> getPage(int pageSize,int pageNo,String contain);
    void save(ColaDept dept);
    void delete(long id);
    void update(ColaDept dept);
    ColaDept find(long id);
    List<ColaDept> find(int from,int limit);
}