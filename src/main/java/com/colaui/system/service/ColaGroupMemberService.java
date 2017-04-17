package com.colaui.system.service;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.provider.Page;

import java.util.ArrayList;
import java.util.List;

public interface ColaGroupMemberService {
    Page<ColaGroupMember> getPage(int pageSize, int pageNo, String contain);

    void delete(long id);

    void update(ColaGroupMember groupmember);

    ColaGroupMember find(long id);

    List<ColaGroupMember> find(int from, int limit);

    void saveGroupUser(String groupId, ArrayList<String> groupUserIds);

    void saveGroupPosition(String groupId, ArrayList<String> groupPositionIds);

    void saveGroupDept(String groupId, ArrayList<String> groupDeptIds);

    void deleteByUsername(String groupId, String username);

    void deleteByPositionId(String groupId, String positionId);

    void deleteByDeptId(String groupId, String deptId);

    List<ColaGroupMember> checkSameUser(String groupId, String username);

    List<ColaGroupMember> checkSamePosition(String groupId, String positionId);

    List<ColaGroupMember> checkSameDept(String groupId, String deptId);
}