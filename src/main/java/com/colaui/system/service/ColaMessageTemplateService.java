package com.colaui.system.service;

import com.colaui.example.model.ColaMessageTemplate;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaMessageTemplateService {
    Page<ColaMessageTemplate> getPage(int pageSize,int pageNo,String contain);
    void save(ColaMessageTemplate messagetemplate);
    void delete(String id);
    void update(ColaMessageTemplate messagetemplate);
    ColaMessageTemplate find(String id);
    List<ColaMessageTemplate> find(int from,int limit);
}