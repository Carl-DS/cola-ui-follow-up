package com.colaui.system.service;

import com.colaui.example.model.ColaRoleMember;
import com.colaui.provider.Page;

import java.util.ArrayList;
import java.util.List;

public interface ColaRoleMemberService {
    Page<ColaRoleMember> getPage(int pageSize, int pageNo, String contain);

    void save(ColaRoleMember rolemember);

    void delete(String id);

    void update(ColaRoleMember rolemember);

    ColaRoleMember find(String id);

    List<ColaRoleMember> find(int from, int limit);

    List<ColaRoleMember> checkSameUser(String roleId, String username);

    List<ColaRoleMember> checkSamePosition(String roleId, String positionId);

    List<ColaRoleMember> checkSameDept(String roleId, String deptId);

    List<ColaRoleMember> checkSameGroup(String roleId, String groupId);

    void saveRoleGroup(String roleId, ArrayList<String> roleGroupIds);

    void saveRoleDept(String roleId, ArrayList<String> roleDeptIds);

    void saveRolePosition(String roleId, ArrayList<String> rolePositionIds);

    void saveRoleUser(String roleId, ArrayList<String> roleUserIds);

    void deleteByUsername(String roleId, String username);

    void deleteByPositionId(String roleId, String positionId);

    void deleteByDeptId(String roleId, String deptId);

    void deleteByGroupId(String roleId, String groupId);
}