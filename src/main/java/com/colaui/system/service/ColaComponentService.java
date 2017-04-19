package com.colaui.system.service;

import com.colaui.example.model.ColaComponent;
import com.colaui.provider.Page;

import java.util.List;
import java.util.Map;

public interface ColaComponentService {
    Page<ColaComponent> getPage(int pageSize, int pageNo, String contain);

    void save(ColaComponent component);

    void delete(long id);

    void update(ColaComponent component);

    ColaComponent find(long id);

    List<ColaComponent> find(int from, int limit);

    Map<String, Boolean> getComponentAuth(String url, String componentId);
}