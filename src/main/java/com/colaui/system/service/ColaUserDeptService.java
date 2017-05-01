package com.colaui.system.service;

import com.colaui.system.model.ColaUserDept;
import com.colaui.helper.Page;

import java.util.List;
public interface ColaUserDeptService {
    Page<ColaUserDept> getPage(int pageSize,int pageNo,String contain);
    void save(ColaUserDept userdept);
    void delete(long id);
    void update(ColaUserDept userdept);
    ColaUserDept find(long id);
    List<ColaUserDept> find(int from,int limit);
}