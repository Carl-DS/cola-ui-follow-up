package com.colaui.system.service.impl;

import com.colaui.system.model.ColaRoleMember;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaRoleMemberDao;
import com.colaui.system.service.ColaRoleMemberService;
import com.colaui.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColaRoleMemberServiceImpl implements ColaRoleMemberService {
    @Autowired
    private ColaRoleMemberDao rolememberDao;

    public Page<ColaRoleMember> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return rolememberDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaRoleMember rolemember) {
        rolememberDao.save(rolemember);
    }

    public void delete(String id) {
        rolememberDao.delete(id);
    }

    public void update(ColaRoleMember rolemember) {
        rolememberDao.update(rolemember);
    }

    public ColaRoleMember find(String id) {
        return rolememberDao.get(id);
    }

    public List<ColaRoleMember> find(int from, int limit) {
        return rolememberDao.find(from, limit);
    }

    @Override
    public List<ColaRoleMember> checkSameUser(String roleId, String username) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(username)) {
            Criterion lastRest= Restrictions.eq("roleId", roleId);
            Criterion firstRest= Restrictions.eq("username", username);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return rolememberDao.find(criteria);
    }

    @Override
    public List<ColaRoleMember> checkSamePosition(String roleId, String positionId) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(positionId)) {
            Criterion lastRest= Restrictions.eq("roleId", roleId);
            Criterion firstRest= Restrictions.eq("positionId", positionId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return rolememberDao.find(criteria);
    }

    @Override
    public List<ColaRoleMember> checkSameDept(String roleId, String deptId) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(deptId)) {
            Criterion lastRest= Restrictions.eq("roleId", roleId);
            Criterion firstRest= Restrictions.eq("deptId", deptId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return rolememberDao.find(criteria);
    }

    @Override
    public List<ColaRoleMember> checkSameGroup(String roleId, String groupId) {
        Criteria criteria = rolememberDao.createCriteria();
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(groupId)) {
            Criterion lastRest= Restrictions.eq("roleId", roleId);
            Criterion firstRest= Restrictions.eq("groupId", groupId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return rolememberDao.find(criteria);
    }

    @Override
    public void saveRoleUser(String roleId, ArrayList<String> roleUserIds) {
        ColaRoleMember rolemember = null;
        for (String roleUserId : roleUserIds) {
            rolemember = new ColaRoleMember();
            rolemember.setId(CommonUtils.uuid());
            rolemember.setRoleId(roleId);
            rolemember.setUsername(roleUserId);
            rolememberDao.save(rolemember);
        }
    }

    @Override
    public void deleteByUsername(String roleId, String username) {
        rolememberDao.deleteByUsername(roleId, username);
    }

    @Override
    public void deleteByPositionId(String roleId, String positionId) {
        rolememberDao.deleteByPositionId(roleId, positionId);
    }

    @Override
    public void deleteByDeptId(String roleId, String deptId) {
        rolememberDao.deleteByDeptId(roleId, deptId);
    }

    @Override
    public void deleteByGroupId(String roleId, String groupId) {
        rolememberDao.deleteByGroupId(roleId, groupId);
    }

    @Override
    public void saveRolePosition(String roleId, ArrayList<String> rolePositionIds) {
        ColaRoleMember rolemember = null;
        for (String rolePositionId : rolePositionIds) {
            rolemember = new ColaRoleMember();
            rolemember.setId(CommonUtils.uuid());
            rolemember.setRoleId(roleId);
            rolemember.setPositionId(rolePositionId);
            rolememberDao.save(rolemember);
        }
    }

    @Override
    public void saveRoleDept(String roleId, ArrayList<String> roleDeptIds) {
        ColaRoleMember rolemember = null;
        for (String roleDeptId : roleDeptIds) {
            rolemember = new ColaRoleMember();
            rolemember.setId(CommonUtils.uuid());
            rolemember.setRoleId(roleId);
            rolemember.setDeptId(roleDeptId);
            rolememberDao.save(rolemember);
        }
    }

    @Override
    public void saveRoleGroup(String roleId, ArrayList<String> roleGroupIds) {
        ColaRoleMember rolemember = null;
        for (String roleGroupId : roleGroupIds) {
            rolemember = new ColaRoleMember();
            rolemember.setId(CommonUtils.uuid());
            rolemember.setRoleId(roleId);
            rolemember.setGroupId(roleGroupId);
            rolememberDao.save(rolemember);
        }
    }

}