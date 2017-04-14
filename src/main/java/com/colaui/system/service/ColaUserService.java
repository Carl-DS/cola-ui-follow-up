package com.colaui.system.service;

import com.colaui.example.model.ColaUser;
import com.colaui.provider.Page;

import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
public interface ColaUserService {
    Page<ColaUser> getPage(int pageSize, int pageNo, String contain);
    void save(ColaUser colaUser);
    void delete(String id);
    void update(ColaUser colaUser);
    ColaUser find(String id);
    List<ColaUser> find(int from, int limit);

    boolean check(String username);

    Page<ColaUser> groupUsers(int pageSize, int pageNo, String groupId);
}
