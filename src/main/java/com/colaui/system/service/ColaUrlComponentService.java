package com.colaui.system.service;

import com.colaui.example.model.ColaUrlComponent;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaUrlComponentService {
    Page<ColaUrlComponent> getPage(int pageSize,int pageNo,String contain);
    void save(ColaUrlComponent urlcomponent);
    void delete(long id);
    void update(ColaUrlComponent urlcomponent);
    ColaUrlComponent find(long id);
    List<ColaUrlComponent> find(int from,int limit);
}