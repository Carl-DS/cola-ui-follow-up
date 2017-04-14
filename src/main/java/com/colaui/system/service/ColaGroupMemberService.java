package com.colaui.system.service;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.provider.Page;

import java.util.ArrayList;
import java.util.List;
public interface ColaGroupMemberService {
    Page<ColaGroupMember> getPage(int pageSize,int pageNo,String contain);
    void delete(long id);
    void update(ColaGroupMember groupmember);
    ColaGroupMember find(long id);
    List<ColaGroupMember> find(int from,int limit);

    void save(String groupId, ArrayList<String> groupUserIds);

    void deleteByUsername(String groupId, String username);
}