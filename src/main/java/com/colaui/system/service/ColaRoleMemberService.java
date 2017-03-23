package com.colaui.system.service;

import com.colaui.example.model.ColaRoleMember;
import com.colaui.provider.Page;

import java.util.List;
public interface ColaRoleMemberService {
    Page<ColaRoleMember> getPage(int pageSize,int pageNo,String contain);
    void save(ColaRoleMember rolemember);
    void delete(long id);
    void update(ColaRoleMember rolemember);
    ColaRoleMember find(long id);
    List<ColaRoleMember> find(int from,int limit);
}