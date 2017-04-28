package com.colaui.system.service;

import com.colaui.system.model.ColaUrlComponent;
import com.colaui.helper.Page;

import java.util.List;
import java.util.Map;

public interface ColaUrlComponentService {
    Page<ColaUrlComponent> getPage(int pageSize, int pageNo, String contain);

    void save(ColaUrlComponent urlcomponent);

    void delete(long id);

    void update(ColaUrlComponent urlcomponent);

    ColaUrlComponent find(long id);

    List<ColaUrlComponent> find(int from, int limit);

    void saveRoleUrlComponents(List<Map<String, Object>> urlComponents);
}