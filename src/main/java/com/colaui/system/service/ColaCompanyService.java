package com.colaui.system.service;

import com.colaui.example.model.ColaCompany;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaCompanyService {
    Page<ColaCompany> getPage(int pageSize,int pageNo,String contain);
    void save(ColaCompany company);
    void delete(long id);
    void update(ColaCompany company);
    ColaCompany find(long id);
    List<ColaCompany> find(int from,int limit);
}